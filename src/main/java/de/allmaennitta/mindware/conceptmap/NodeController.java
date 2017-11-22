package de.allmaennitta.mindware.conceptmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
class NodeController {

  private static final Logger LOG = LoggerFactory.getLogger(NodeController.class);

  @Autowired
  NodeRepository nodeRepository;

  private static final String ROOT_REDIRECT = "/node/all";

  @RequestMapping(value = "/")
  public void handleRootRequest(HttpServletResponse response) {
    try {
      response.sendRedirect(ROOT_REDIRECT);
    } catch (IOException e) {
      throw new IllegalStateException(
          String.format("There is an error redirecting to URL %s", ROOT_REDIRECT));
    }
  }

  @RequestMapping(value = ROOT_REDIRECT, method = RequestMethod.GET)
  Map<String,List<Node>> getAllNodes() {
    Map nodes = new HashMap();
    nodes.put("nodes", nodeRepository.findAllNodes());
    return nodes;
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
