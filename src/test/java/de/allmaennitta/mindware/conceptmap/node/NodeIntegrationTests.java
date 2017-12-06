package de.allmaennitta.mindware.conceptmap.node;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.jayway.jsonpath.JsonPath;
import de.allmaennitta.mindware.conceptmap.node.Node;
import de.allmaennitta.mindware.conceptmap.node.NodeRepository;
import de.allmaennitta.mindware.conceptmap.utils.DBInitializer;
import io.restassured.RestAssured;
import io.restassured.function.RestAssuredFunction;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.parsing.Parser;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponseLogSpec;
import io.restassured.response.ValidatableResponseOptions;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matcher;
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
public class NodeIntegrationTests {

  @LocalServerPort
  int port;

  @Autowired
  NodeRepository rep;

  private static boolean dbInitialized = false;

  @Before
  public void initialize() {
    RestAssured.port = port;

    if (!dbInitialized) {
      new DBInitializer(rep).init();
      dbInitialized = true;
    }
  }

  @Test
  public void allNodesTest() {
    String json =
        when().
            get("/node/all").
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