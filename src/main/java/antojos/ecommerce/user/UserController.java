package antojos.ecommerce.user;

import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
  private UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/list")
  public String listUsers(Model model, HttpSession session){
    User userInSession = (User) session.getAttribute("user");
    if (Objects.equals(userInSession.getAccessLvl(), "admin")){
      List<User> users = userService.getAllUsers();
      model.addAttribute("users", users);
      return "/users/userlist";
    }
    return "redirect:/";
  }


  @GetMapping("/add")
  public String addUserForm(Model model){
    model.addAttribute("user", new User());
    return "users/add";
  }
  @PostMapping("/add")
  public String addUser(@ModelAttribute User user){
    userService.addUser(user);
    return "redirect:/users/list";
  }


  @GetMapping("/edit/{dni}")
  public String editUserForm(@PathVariable String dni, Model model){
    User user = userService.getUserByDni(dni);
    model.addAttribute("user", user);
    return "users/edit";
  }
  @PostMapping("/edit/{dni}")
  public String editUser(@PathVariable String dni, @ModelAttribute User user){
    userService.updateUser(user);
    return "redirect:/users/list";
  }


  @GetMapping("/delete/{dni}")
  public String deleteUser(@PathVariable String dni){
    userService.deleteUser(dni);
    return "redirect:/users/list";
  }
}
