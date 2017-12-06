package de.allmaennitta.mindware.conceptmap.node;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface NodeRepository extends Neo4jRepository<Node, Long> {

  Node findByName(String name);

  @Query("MATCH (n:Node) return n")
  List<Node> findAllNodes();
}
