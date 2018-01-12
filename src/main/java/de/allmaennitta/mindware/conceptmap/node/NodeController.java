package de.allmaennitta.mindware.conceptmap.node;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

  private static final Logger LOG = LoggerFactory.getLogger(NodeController.class);

  @Autowired
  NodeRepository nodeRepository;

  @RequestMapping(method = RequestMethod.GET)
  public Resources<Resource<Node>> getAllNodes() {
    return nodesToResources(this.nodeRepository.getAllNodes());
  }

  @RequestMapping(value = "/by", method = RequestMethod.GET)
  public Resource<Node> getNode(@RequestParam("name") String name) {
    return nodeToResource(this.nodeRepository.findByName(name));
  }

  @RequestMapping(method = RequestMethod.PUT)
  public Resource<Node> create(@RequestBody Node input) {
    return nodeToResource(this.nodeRepository.save(input));
  }

  @RequestMapping(value = "/start", method = RequestMethod.GET)
  public Resource<Node> getStartNode() {
    List<Node> result = this.nodeRepository.getFirst(); // should be a list with  one element

    Objects.requireNonNull(result, "node list could be empty but never be null");
    if (result.isEmpty()) {
      throw new IllegalStateException("There should be exactly one node "
          + "labeled with 'start'.");
    }
    return nodeToResource(result.get(0));
  }

  private Resources<Resource<Node>> nodesToResources(List<Node> nodes) {
    return new Resources<>(
        nodes.stream().
            map(node -> nodeToResource(node)).
            collect(Collectors.toList()),
        linkTo(methodOn(NodeController.class).getAllNodes()).withSelfRel());
  }


  private Resource<Node> nodeToResource(Node node) {
    return new Resource<>(
        node,
        linkTo(methodOn(NodeController.class).getAllNodes()).withSelfRel());
  }

}
//
//    @GetMapping
//    def list(model: Model) = {
//        val hotels = hotelRepository.findAll()
//        model.addAttribute("hotels", hotels)
//        "hotels/list"
//        }
//
//@GetMapping(Array("/edit/{id}"))
//  def edit(@PathVariable("id") id: Long, model: Model) = {
//        model.addAttribute("hotel", hotelRepository.findOne(id))
//        "hotels/edit"
//        }
//
//@GetMapping(params = Array("form"))
//  def createForm(model: Model) = {
//          model.addAttribute("hotel", new Hotel())
//          "hotels/create"
//          }
//
//@PostMapping
//  def create(@Valid hotel: Hotel, bindingResult: BindingResult) =
//        if (bindingResult.hasErrors()) {
//        "hotels/create"
//        } else {
//        hotelRepository.save(hotel)
//        "redirect:/hotels"
//        }
//
//
//@PostMapping(value = Array("/update"))
//  def update(@Valid hotel: Hotel, bindingResult: BindingResult) =
//        if (bindingResult.hasErrors()) {
//        "hotels/edit"
//        } else {
//        hotelRepository.save(hotel)
//        "redirect:/hotels"
//        }
//
//
//@GetMapping(value = Array("/delete/{id}"))
//  def delete(@PathVariable("id") id: Long) = {
//        hotelRepository.delete(id)
//        "redirect:/hotels"
//        }
