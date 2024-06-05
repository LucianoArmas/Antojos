package antojos.ecommerce.loginAndRegister;

import antojos.ecommerce.auth.AuthResponse;
import antojos.ecommerce.auth.AuthService;
import antojos.ecommerce.auth.LoginRequest;
import antojos.ecommerce.order.Order;
import antojos.ecommerce.order.OrderService;
import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.user.Role;
import antojos.ecommerce.user.User;
import antojos.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("login")//
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;
  private final OrderService orderService;
  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;



  @GetMapping
  public String showLoginForm(){
    return "users/login";//
  }

  private Order setOrderSession(User user){
    Order order = orderService.getByUserAndStatus(user, "pending");
    if(order == null){
      Date today = new Date();
      order = new Order(user,today, (float) 0,"pending");
      orderService.addOrder(order);
    }
    return order;
  }





  @PostMapping
  public String login(@RequestParam String dni, @RequestParam String pass, HttpSession session, Model model){
    LoginRequest request = new LoginRequest(dni,pass);

    AuthResponse authResponse = authService.login(request);

    if (authResponse != null && authResponse.getToken() != null){

      User userByDni = userService.getUserByDni(request.getDni());
      String passwordEncodedInBD = userByDni.getPassword();
      boolean flag_passwordsMatches = passwordEncoder.matches(request.getPassword(), passwordEncodedInBD);

      if (flag_passwordsMatches){

        session.setAttribute("token",authResponse.getToken());
        session.setAttribute("dni", request.getDni());
        session.setAttribute("user", userByDni);

        Order order = setOrderSession(userByDni);
        session.setAttribute("orderPending", order);

        List<OrderLine> orderLineList = orderService.getOrderLinesFromSession(session);
        session.setAttribute("orderLineList", orderLineList);

        return "redirect:";

      }else{
        model.addAttribute("error", "Invalid user");
        return "users/login";//
      }


    }else{
      model.addAttribute("error", "Invalid user");
      return "users/login";//
    }
  }

}
