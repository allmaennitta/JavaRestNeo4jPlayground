package de.allmaennitta.mindware.conceptmap;

import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATests {
//
//  @Autowired
//  private TestEntityManager entityManager;

  @Autowired
  private NodeRepository repository;

  @Test
  public void testNodes() {
    List<Node> nodes = this.repository.findAllNodes();
    assertThat(nodes.get(0).getName()).isEqualTo("Amsel");
  }

  @Test
  public void testNodeByName() {
    Node node = this.repository.findByName("Amsel");
    assertThat(node.getName()).isEqualTo("Amsel");
    assertThat(node.getId()).isEqualTo(1L);
  }

  @Test
  public void testCRUD(){

    Node node = this.repository.findByName("Amsel");
    assertThat(node.getName()).isEqualTo("Amsel");
    assertThat(node.getId()).isEqualTo(1L);
  }
}