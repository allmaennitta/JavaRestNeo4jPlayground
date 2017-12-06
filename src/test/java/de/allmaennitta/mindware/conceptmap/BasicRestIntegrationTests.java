package de.allmaennitta.mindware.conceptmap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

import de.allmaennitta.mindware.conceptmap.node.Node;
import de.allmaennitta.mindware.conceptmap.node.NodeRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@TestConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BasicRestIntegrationTests {

  @LocalServerPort
  int port;

  @Autowired
  NodeRepository rep;

//  private static boolean dbInitialized = false;
//
  @Before
  public void initialize() {
    RestAssured.port = port;
//
//    if (!dbInitialized) {
//      new DBInitializer(rep).init();
//      dbInitialized = true;
//    }
  }

  @Test
  public void rootTest() {
    given().
        redirects().follow(false).
    when().
        get("/").
    then().
        statusCode(302).
        header("location",String.format("http://localhost:%s/node/all",port));

  }

  @Test
  public void specialCharTest() {
    Node nodeSpecialChars =
        given().
            contentType("application/json").
            body(new Node("Buße in Höhe von 100$ in €")).
          when().
            post("/node/create").
          then().
            contentType(JSON).
            statusCode(200).
          extract().
            response().body().as(Node.class);

    assertThat(nodeSpecialChars.getName()).isEqualTo("Buße in Höhe von 100$ in €");

    Node resultNode =
        when().
            get(String.format("/node/%s","Buße in Höhe von 100$ in €")).
            then().
            contentType(JSON).
            statusCode(200).
            extract().
            response().body().as(Node.class);

    assertThat(resultNode.getName()).isEqualTo("Buße in Höhe von 100$ in €");
    rep.deleteAll();
  }

  @Test
  public void errorNullpointerTest() {
    Response response =
      when().
        post("/error/nullpointer").
      then().
      extract().response();
    assertThat(response.body().asString()).contains("Server Fault");
    assertThat(response.statusCode()).isEqualTo(500);
  }

  @Test
  public void errorIllegalArgumentTest() {
    Response response =
        when().
            post("/error/illegal_argument").
            then().
            extract().response();
    assertThat(response.body().asString()).contains("API Fault");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  public void errorUnsupportedOperationTest() {
    Response response =
        when().
            post("/error/unsupported_operation").
            then().
            extract().response();
    assertThat(response.body().asString()).contains("Unknown Fault");
    assertThat(response.statusCode()).isEqualTo(501);
  }
}