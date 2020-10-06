package suporte;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import suporte.LeitorProperties;

public class BaseTest{
	
	@BeforeClass
	public static void carregarConfigInicial() {
		RestAssured.baseURI = new LeitorProperties("config.properties").getProperties().getProperty("url.api");

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();

		reqBuilder.setContentType(ContentType.JSON);
		respBuilder.expectResponseTime(Matchers.lessThan(50000L));

		RestAssured.requestSpecification = reqBuilder.build();
		RestAssured.responseSpecification = respBuilder.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
	}

}
