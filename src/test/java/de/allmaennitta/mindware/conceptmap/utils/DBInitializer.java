package de.allmaennitta.mindware.conceptmap.utils;

import de.allmaennitta.mindware.conceptmap.node.Node;
import org.springframework.data.repository.CrudRepository;


public class DBInitializer {
  private CrudRepository rep;

  public DBInitializer(CrudRepository repository){
     this.rep = repository;
  }
  public void init(){
    rep.deleteAll();
    rep.save(new Node("Cuckoo"));
    rep.save(new Node("Aves"));
    rep.save(new Node("Ornithurae"));
    rep.save(new Node("Chordata"));
    rep.save(new Node("Animalia"));
    rep.save(new Node("Filozoa"));
    rep.save(new Node("Holozoa"));
    rep.save(new Node("Opisthokonta"));
    rep.save(new Node("Unikonta"));
    rep.save(new Node("Eukaryota"));
  }




}