package de.allmaennitta.mindware.conceptmap;

import de.allmaennitta.mindware.conceptmap.utils.DBInitializer;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class JPATests {
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
    List<Node> nodes = this.repository.findAllNodes();
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