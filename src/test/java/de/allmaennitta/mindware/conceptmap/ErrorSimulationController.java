package de.allmaennitta.mindware.conceptmap;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/error")
class ErrorSimulationController {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorSimulationController.class);

  @RequestMapping(value = "/nullpointer")
  public void handleRequestThrowingNullpointer(HttpServletResponse response) {
    throw new NullPointerException("Simulating a Nullpointer Exception...");
  }

  @RequestMapping(value = "/illegal_argument")
  public void handleRequestThrowingIllegalArgumentException(HttpServletResponse response) {
    throw new IllegalArgumentException("Simulating an Illegal Argument Exception...");
  }

  @RequestMapping(value = "/unsupported_operation")
  public void handleRequestThrowingUnsupportedOperationException(HttpServletResponse response) {
    throw new UnsupportedOperationException("Simulating an Unsupported Operation Exception...");
  }
}
