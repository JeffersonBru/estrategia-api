package auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Usuario;
import suporte.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterTest extends BaseTest{
	
	static HelperLogin hlp;
	static String application;
	String basePath = "auth";
	
	@BeforeClass
	public static void init() {
		hlp = new HelperLogin();
	}
	
	@Test
	public void ct01_CadastroVazio() {
	given()
		.body(hlp.gerarBody(new Usuario(null, null, null)).toString())
		.basePath(basePath)
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("Registration failed")));
	}
	
	@Test
	public void ct02_CadastroComApenasNome() {
		Usuario usuario = hlp.gerarUsuarioRandom();
		usuario.email = null;
		usuario.senha = null;
	given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("Registration failed")));
	}
	
	@Test
	public void ct03_CadastroSemSenha() {
		Usuario usuario = hlp.gerarUsuarioRandom();
		usuario.senha = null;
	given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("Registration failed")));
	}
	
	@Test
	public void ct04_CadastroSemNome() {
		Usuario usuario = hlp.gerarUsuarioRandom();
		usuario.name = null;
		given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("user._id", not(emptyString()))
		.body("user.email", is(equalTo(usuario.email)))
		.body("user.createdAt", not(emptyString()))
		.body("token", not(emptyString()));
	}
	
	@Test
	public void ct05_CadastroComSucesso() {
		Usuario usuario = hlp.gerarUsuarioRandom();
		given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("register")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("user._id", not(emptyString()))
		.body("user.name", is(equalTo(usuario.name)))
		.body("user.email", is(equalTo(usuario.email)))
		.body("user.createdAt", not(emptyString()))
		.body("token", not(emptyString()));
	}

}
