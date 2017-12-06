package de.allmaennitta.mindware.conceptmap.node;

import java.util.List;
//import javax.persistence.GeneratedValue;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Node {

  public Node() { }

  public Node(String name) {
    this.name = name;
  }

  @GraphId
  protected Long id;

  public Long getId() { return id;}

  String name;
  public String getName() { return this.name;}
  public void setName(String name) { this.name = name;}

  @Relationship(type = "IS_A", direction = Relationship.OUTGOING)
  Node node;

  @Relationship(type = "HAS_SUBTYPES", direction = Relationship.OUTGOING)
  List<Node> subtypes;

  @Override
  public String toString() {
    return "Node{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

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
