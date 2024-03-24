package antojos.ecommerce;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController_ELIMINAR {

  @GetMapping("/dynamic")
  public String dynamic(){
    return "/users/userlist";
  }



//  @GetMapping("/")
//  public String listUsers(Model model){
//    List<User> users = userService.getAllUsers();
//    model.addAttribute("users", users);
//    return "/users/userlist";
//  }
}
