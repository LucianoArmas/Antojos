package antojos.ecommerce.error;

import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(SQLSyntaxErrorException.class)
  public ModelAndView handleSQLSyntaxException(SQLSyntaxErrorException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was a SQL Syntaxis Error: "+e);
    return modelAndView;
  }

  @ExceptionHandler(DataAccessException.class)
  public ModelAndView handleDBConnectionException(DataAccessException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was a DB connection exception: "+e);
    return modelAndView;
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFound(ObjectNotFoundException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was an exception, the object was not founded: "+e);
    return modelAndView;
  }


  @ExceptionHandler(IOException.class)
  public ModelAndView handleIOExceptionIMG(IOException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was an IOException, the img path does not exist: "+e);
    return modelAndView;
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ModelAndView handleMalformedJwt(MalformedJwtException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was an error with User Authentication"+ e);
    return modelAndView;
  }

  @ExceptionHandler(NullPointerException.class)
  public String handleNullPointer(NullPointerException e, Model model){
    model.addAttribute("error", "Invalid , error: " +e);
    return "users/login";//
  }


//  BadCredentialsException
  @ExceptionHandler(BadCredentialsException.class)
  public ModelAndView handleBadCredentials(BadCredentialsException e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "Invalid credentials: "+ e);
    return modelAndView;
  }



  @ExceptionHandler(Exception.class)
  public ModelAndView handleException(Exception e){
    ModelAndView modelAndView = new ModelAndView("errors/error");
    modelAndView.addObject("errorMessage", "There was a Generic Error: "+e);
    return modelAndView;
  }
}
