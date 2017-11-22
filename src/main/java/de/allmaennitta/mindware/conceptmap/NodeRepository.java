package de.allmaennitta.mindware.conceptmap;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

interface NodeRepository extends Neo4jRepository<Node, Long> {

  Node findByName(String name);

  @Query("MATCH (n:Node) return n")
  List<Node> findAllNodes();
}
