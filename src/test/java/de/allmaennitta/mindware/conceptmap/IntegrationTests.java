package de.allmaennitta.mindware.conceptmap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.jayway.jsonpath.JsonPath;
import de.allmaennitta.mindware.conceptmap.utils.DBInitializer;
import io.restassured.RestAssured;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
public class IntegrationTests {

  @LocalServerPort
  int port;

  @Autowired
  NodeRepository rep;

  private static boolean dbInitialized = false;

  @Before
  public void initialize() {

    if (!dbInitialized) {
      new DBInitializer(rep).init();
      dbInitialized = true;
    }
  }

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

    List<String> result = JsonPath.parse(json).read("$.nodes[*].name");
    assertThat(result).contains("Filozoa", "Holozoa");
  }

  @Test
  public void byNameTest() throws IOException {
    RestAssured.port = port;
    Node jsonNode =
        when().
            get("/node/Chordata").
          then().
            contentType(JSON).
            statusCode(200).
          extract().
            response().body().as(Node.class);

    assertThat(jsonNode.getName()).isEqualTo("Chordata");
  }

  @Test
  public void createTest() {

    RestAssured.port = port;
    Node nodeCreated =
        given().
            contentType("application/json").
            body(new Node("Testknoten")).
        when().
            post("/node/create").
          then().
            contentType(JSON).
            statusCode(200).
            extract().
            response().body().as(Node.class);

    Long id = nodeCreated.getId();
    assertThat(id > 0L);
    assertThat(nodeCreated.getName()).isEqualTo("Testknoten");

    assertThat(rep.findOne(nodeCreated.getId())).isEqualTo(nodeCreated);
    rep.delete(nodeCreated.getId());
  }
}