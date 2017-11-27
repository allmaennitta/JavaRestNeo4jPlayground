package de.allmaennitta.mindware.conceptmap;

import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

  @LocalServerPort
  int port;

  @Test
  public void rootTest() {
    RestAssured.port = port;
    String json =
        when().
            get("/").
            then().
            contentType(JSON).
            statusCode(200).
    extract().
            response().body().print();

    assertThat(JsonPath.from(json).getString("nodes[0].name")).isEqualTo("Amsel");
  }

  @Test
  public void byNameTest() {
    RestAssured.port = port;
    String json =
        when().
            get("/node/Amsel").
            then().
            contentType(JSON).
            statusCode(200).
            extract().
            response().body().print();

    assertThat(JsonPath.from(json).getLong("id")).isEqualTo(1L);
  }
}