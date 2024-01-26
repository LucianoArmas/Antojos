package antojos.ecommerce.user;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.order.OrderService;
import antojos.ecommerce.orderLine.OrderLine;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
  private UserService userService;
  private OrderService orderService;

  public LoginController(UserService userService, OrderService orderService) {
    this.userService = userService;
    this.orderService = orderService;
  }

  @GetMapping
  public String showLoginForm(){
    return "/users/login";
  }

  private Order setOrderSession(User user){
    Order order = orderService.getByUserAndState(user, "pending");
    if(order == null){
      Date today = new Date();
      order = new Order(user,today,0,"pending");
      orderService.addOrder(order);
    }
    return order;
  }

  @PostMapping
  public String getInfoLoginForm(@RequestParam String dni, @RequestParam String pass, Model model, HttpSession session){
    User user = userService.findByDniAndUserPass(dni, pass);
    if( user != null){
      session.setAttribute("dni", dni);
      session.setAttribute("user", user);

      Order order = setOrderSession(user);
      session.setAttribute("order", order);

      List<OrderLine> orderLineList = orderService.getOrderLinesFromSession(session);
      session.setAttribute("orderLineList", orderLineList);
      return "redirect:/";
    } else {
      model.addAttribute("error", "Invalid user");
      return "/users/login";
    }
  }
}
