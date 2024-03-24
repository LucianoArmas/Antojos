package antojos.ecommerce.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(SQLSyntaxErrorException.class)
  public ModelAndView handleSQLSyntaxException(SQLSyntaxErrorException e){
    System.err.println("XD");
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "Generic Error: "+e);
    return modelAndView;
  }


  @ExceptionHandler(Exception.class)
  public ModelAndView handleException(Exception e){
    System.err.println("XD");
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "Generic Error: "+e);
    return modelAndView;
  }
}
