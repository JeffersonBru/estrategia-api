package auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import model.Usuario;
import suporte.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest extends BaseTest{
	
	static HelperLogin hlp;
	static String application;
	static String basePath = "auth";
	static Usuario usuarioValido;
	
	@BeforeClass
	public static void init() {
		hlp = new HelperLogin();
		usuarioValido = hlp.gerarUsuarioRandom();
		
	given()
		.body(hlp.gerarBody(usuarioValido).toString())
		.basePath(basePath)
	.when()
		.post("register")
	.then()
		.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void ct01_LoginSucesso() {
		
	given()
		.body(hlp.gerarBody(usuarioValido).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_OK)
		.body("user._id", not(emptyString()))
		.body("user.email", is(equalTo(usuarioValido.email)))
		.body("user.createdAt", not(emptyString()))
		.body("token", not(emptyString()));
		
	}
	
	@Test
	public void ct02_LoginVazio() {
	given()
		.body(hlp.gerarBody(new Usuario(null, null)).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("User not found")));
	}
	
	@Test @Ignore
	public void ct03_LoginSomenteUsuario() {
		Usuario usuario = new Usuario(usuarioValido.email, null);
	given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("User not found")));
	}
	
	@Test
	public void ct04_LoginSomenteSenha() {
		Usuario usuario = new Usuario(null, usuarioValido.senha) ;
	given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("User not found")));
	}
	
	@Test
	public void ct05_LoginInvalido() {
		Usuario usuario = new Usuario("usuario XPTO", usuarioValido.senha) ;
	given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("User not found")));
	}
	
	@Test
	public void ct06_SenhaInvalido() {
		Usuario usuario = new Usuario(usuarioValido.email, "123") ;
		given()
		.body(hlp.gerarBody(usuario).toString())
		.basePath(basePath)
	.when()
		.post("authenticate")
		.prettyPeek()
	.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST)
		.body("error", is(equalTo("Invalid password")));
	}
	
}
