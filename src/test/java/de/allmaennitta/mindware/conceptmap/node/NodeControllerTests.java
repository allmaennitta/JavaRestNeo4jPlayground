package de.allmaennitta.mindware.conceptmap.node;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import de.allmaennitta.mindware.conceptmap.node.Node;
import de.allmaennitta.mindware.conceptmap.node.NodeRepository;
import de.allmaennitta.mindware.conceptmap.utils.DBInitializer;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class NodeControllerTests {

  @Autowired
  private MockMvc mockMvc;

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
  public void findRoot() throws Exception {
    this.mockMvc.perform(get("/")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "/node/all"));
  }

  @Test
  public void allNodes() throws Exception {
    String json = this.mockMvc.perform(get("/node/all")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn().getResponse().getContentAsString();

    List<String> result = JsonPath.parse(json).read("$.nodes[*].name");
    assertThat(result).contains("Cuckoo", "Eukaryota");
  }

  @Test
  public void nodeByName() throws Exception {
    this.mockMvc.perform(get("/node/Opisthokonta")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("@.name").value("Opisthokonta"));
  }

  @Test
  public void nodeCreate() throws Exception {
    this.mockMvc.
      perform(
        post("/node/create").
        content("{\"name\":\"ControllerTestKnoten\"}").
        contentType(MediaType.APPLICATION_JSON)
      )
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));

    Node node = rep.findByName("ControllerTestKnoten");
    rep.delete(node.getId());
  }
}