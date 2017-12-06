package de.allmaennitta.mindware.conceptmap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory
      .getLogger(RestResponseEntityExceptionHandler.class);

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<String> handleApiFault(RuntimeException ex, WebRequest request) {
    return createErrorResponseEntity("API", ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NullPointerException.class})
  protected ResponseEntity<String> handleServerFault(RuntimeException ex, WebRequest request) {
    return createErrorResponseEntity("Server", ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<String> handleUnknownFault(RuntimeException ex, WebRequest request) {
    return createErrorResponseEntity("Unknown", ex, HttpStatus.NOT_IMPLEMENTED);
  }

  private ResponseEntity<String> createErrorResponseEntity(String errorType, RuntimeException ex,
      HttpStatus status) {
    LOG.error(String.format("%s Fault: %s", errorType, ex.getLocalizedMessage()));
    return new ResponseEntity<String>(
        String.format("%s Fault at %s", errorType,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)),
        new HttpHeaders(),
        status);
  }



}
//return ResponseEntity.ok().headers(responseHeaders).body(user);

/*
    String bodyOfResponse = "This should be application specific";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.CONFLICT, request);



    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Exception> handleNoResultException(
            NoResultException nre) {
        log.error("> handleNoResultException");
        log.error("- NoResultException: ", nre);
        log.error("< handleNoResultException");
        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        log.error("> handleException");
        log.error("- Exception: ", e);
        log.error("< handleException");
        return new ResponseEntity<Exception>(e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundError(HttpServletRequest req, NotFoundException exception) {
        List<Error> errors = Lists.newArrayList();
        errors.add(new Error(String.valueOf(exception.getCode()), exception.getMessage()));
        return new ErrorResponse(errors);
    }
 */