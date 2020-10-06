package projects;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import auth.HelperLogin;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import model.Projeto;
import model.Usuario;
import suporte.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectsTest extends BaseTest{
	
	static HelperLogin hlpLogin;
	static HelperProjects hlpProject;
	static String application;
	static Usuario usuarioValido;
	static Projeto projectValid;
	
	@BeforeClass
	public static void init() {
		hlpLogin = new HelperLogin();
		hlpProject = new HelperProjects();
		usuarioValido = hlpLogin.gerarUsuarioRandom();
		
		ExtractableResponse<Response> response = 
	given()
		.body(hlpLogin.gerarBody(usuarioValido).toString())
		.basePath("auth")
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.extract();
		
		usuarioValido.token = response.path("token");
		usuarioValido.id = response.path("user._id");
		projectValid = hlpProject.gerarProject(usuarioValido.id);
	}
	
	@Test
	public void ct01_criarProjetoSemAutenticacao() {
	given()
		.headers("Authorization", "")
	.when()
		.post("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("No token provided"));
	}
	
	@Test
	public void ct02_criarProjetoSucesso() {
		ExtractableResponse<Response> response = 
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token))
		.body(hlpProject.gerarBody(projectValid).toString())
	.when()
		.post("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("project._id", not(emptyString()))
		.body("project.title", is(projectValid.title))
		.body("project.description", is(projectValid.description))
		.body("project.user", is(usuarioValido.id))
		.body("project.createdAt", not(emptyString()))
		.body("project.tasks[0].name", is(projectValid.tasks.get(0).name))
		.body("project.tasks[0].assignedTo", is(projectValid.tasks.get(0).assignedTo))
		.body("project.tasks[0].project", not(emptyString()))
		.body("project.tasks[0].createdAt", not(emptyString()))
		.extract();
		
		projectValid.id = response.path("project._id");
		projectValid.tasks.get(0).id = response.path("project.tasks[0].id");
	}
	
	@Test
	public void ct03_criarProjetoTokenInvalido() {
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token).concat("1"))
		.body(hlpProject.gerarBody(projectValid).toString())
	.when()
		.post("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("Token invalid"));
	}
	
	
	@Test
	public void ct04_consultarProjeto() {
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token))
	.when()
		.get("projects/".concat(projectValid.id))
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("project._id", not(emptyString()))
		.body("project.title", is(projectValid.title))
		.body("project.description", is(projectValid.description))
		.body("project.user._id", is(usuarioValido.id))
		.body("project.user.name", is(usuarioValido.name))
		.body("project.user.email", is(usuarioValido.email))
		.body("project.user.createdAt", not(emptyString()))
		.body("project.createdAt", not(emptyString()))
		.body("project.tasks[0].name", is(projectValid.tasks.get(0).name))
		.body("project.tasks[0].assignedTo", is(projectValid.tasks.get(0).assignedTo))
		.body("project.tasks[0].project", not(emptyString()))
		.body("project.tasks[0].createdAt", not(emptyString()));
	}
	
	@Test
	public void ct05_consultarProjetoSemAutenticacao() {
	given()
		.headers("Authorization", "")
	.when()
		.get("projects/".concat(projectValid.id))
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("No token provided"));
	}
	
	@Test
	public void ct06_consultarProjetoTokenInvalido() {
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token).concat("1"))
	.when()
		.get("projects/".concat(projectValid.id))
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("Token invalid"));
	}
	
	
	@Test
	public void ct07_consultarTodosOsProjetos() {
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token))
	.when()
		.get("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.rootPath("projects.find { it._id == '%s'}", withArgs(projectValid.id))
			.body("title", is(projectValid.title))
			.body("description", is(projectValid.description))
			.body("user._id", is(usuarioValido.id))
			.body("user.name", is(usuarioValido.name))
			.body("user.email", is(usuarioValido.email))
			.body("user.createdAt", not(emptyString()))
			.body("createdAt", not(emptyString()))
		.rootPath("projects.tasks.findAll { it.assignedTo[0] == '%s'}", withArgs(projectValid.tasks.get(0).assignedTo))
			.body("_id[0][0]", not(emptyString()))
			.body("completed[0][0]", is(false))
			.body("name[0][0]", is(projectValid.tasks.get(0).name))
			.body("assignedTo[0][0]", is(projectValid.tasks.get(0).assignedTo))
			.body("project[0][0]", is(projectValid.id))	
			.body("createdAt[0][0]", not(emptyString()));
	}
	
	@Test
	public void ct08_consultarTodosProjetosSemAutenticacao() {
	given()
		.headers("Authorization", "")
	.when()
		.get("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("No token provided"));
	}
	
	@Test
	public void ct09_consultarTodosProjetosTokenInvalido() {
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token).concat("1"))
	.when()
		.get("projects")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED)
		.body("error", is("Token invalid"));
	}
	
	@Test
	public void ct10_atualizarProjeto() {
		Projeto newProject = hlpProject.gerarProject(usuarioValido.id);
		newProject.id = projectValid.id;
		newProject.tasks.get(0).id = projectValid.tasks.get(0).id;
		
	given()
		.headers("Authorization", "Bearer ".concat(usuarioValido.token))
		.body(hlpProject.gerarBody(newProject).toString())
	.when()
		.put("projects/".concat(newProject.id))
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("project._id", not(emptyString()))
		.body("project.title", is(newProject.title))
		.body("project.description", is(newProject.description))
		.body("project.user", is(usuarioValido.id))
		.body("project.createdAt", not(emptyString()))
		.body("project.tasks[0].name", is(newProject.tasks.get(0).name))
		.body("project.tasks[0].assignedTo", is(newProject.tasks.get(0).assignedTo))
		.body("project.tasks[0].project", not(emptyString()))
		.body("project.tasks[0].createdAt", not(emptyString()));
	}

}
