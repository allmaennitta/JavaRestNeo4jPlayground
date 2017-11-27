package de.allmaennitta.mindware.conceptmap;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
//import javax.persistence.GeneratedValue;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Node {

  public Node() { }

  public Node(String name) { this.name = name;}



  Long id;

  public Long getId() { return id;}

  protected void setId(Long id) { this.id = id;}
  @GraphId
  String name;

  public String getName() { return this.name;}

  public void setName(String name) { this.name = name;}

  @Relationship(type = "IS_A", direction = Relationship.OUTGOING)
  Node node;

  @Relationship(type = "HAS_SUBTYPES", direction = Relationship.OUTGOING)
  List<Node> subtypes;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }

    Node node = (Node) o;

    return getName().equals(node.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
