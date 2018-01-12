package de.allmaennitta.mindware.conceptmap;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.allmaennitta.mindware.conceptmap.node.NodeController;

@RestController
@RequestMapping(produces = "application/hal+json")
class BaseController {

  private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

  @RequestMapping(value = "/")
  public Resource<String> handleRootRequest() {
    return new Resource<>("Welcome! Here are your options",
        linkTo(methodOn(NodeController.class).getStartNode()).withRel("first").withType
            ("GET"),
        linkTo(methodOn(NodeController.class).create(null)).withRel("create").withType
            ("PUT"),
        linkTo(methodOn(NodeController.class).getNode(null)).withRel("by_name").withType("GET"));
  }

//  @RequestMapping(value = "/old")
//  public void redirectRootRequest(HttpServletResponse response) {
//    final String rootRedirect = "/node/all";
//    try {
//      response.sendRedirect(rootRedirect);
//    } catch (IOException e) {
//      throw new IllegalStateException(
//          String.format("There is an error redirecting to URL %s", rootRedirect));
//    }
//  }

}