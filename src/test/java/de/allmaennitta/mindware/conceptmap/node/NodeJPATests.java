package de.allmaennitta.mindware.conceptmap.node;

import de.allmaennitta.mindware.conceptmap.utils.DBInitializer;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class NodeJPATests {
  @Autowired
  private NodeRepository repository;

  private static boolean dbInitialized = false;

  @Before
  public void initialize() {

    if (!dbInitialized) {
      new DBInitializer(repository).init();
      dbInitialized = true;
    }
  }

  @Test
  public void testAllNodes() {
    List<Node> nodes = this.repository.getAllNodes();
    assertThat(nodes.size()).isGreaterThan(5);
  }

  @Test
  public void testNodeByName() {
    Node node = this.repository.findByName("Animalia");
    assertThat(node.getId()).isGreaterThan(0L);
    assertThat(node.getName()).isEqualTo("Animalia");
  }

  @Test
  public void testCRUD(){
    Node createdNode = this.repository.save(new Node("Magpie"));
    assertThat(this.repository.exists(createdNode.getId())).isTrue();
    Node foundNode = this.repository.findByName("Magpie");
    assertThat(createdNode).isEqualTo(foundNode);
    foundNode.setName("Lapwing");
    Node changedNode = foundNode;
    this.repository.save(changedNode);
    Node refoundChangedNode = this.repository.findByName("Lapwing");
    assertThat(refoundChangedNode.getId()).isEqualTo(createdNode.getId());
    this.repository.delete(refoundChangedNode);
    assertThat(this.repository.exists(createdNode.getId())).isFalse();
  }
}