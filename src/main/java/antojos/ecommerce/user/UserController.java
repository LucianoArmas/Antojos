package antojos.ecommerce.user;

import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
      List<User> users = userService.getAllUsersExceptAdmin(userInSession.getDni());
      model.addAttribute("users", users);
      return "/users/userlist";
    }
    return "redirect:/";
  }


  @GetMapping("/searchAdmin")
  public String searchUser_Admin(@RequestParam("query") String query, Model model, HttpSession session){
    User userInSession = (User) session.getAttribute("user");
    List<User> userList = userService.findByDniOrName(query, userInSession.getDni());

    if(!userList.isEmpty()){
      model.addAttribute("users", userList);
    }else {
      model.addAttribute("userNotFounded", "The user: " + query + " not exists");
    }

    return "/users/userlist";
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

  private boolean resetFlagEditPass(HttpSession session){
    boolean flag = true;
    if((session.getAttribute("editPassForm") == null)||(session.getAttribute("editPassForm").equals(true))){
      flag = false;
    }
    return flag;
  }

  @GetMapping("/edit")
  public String editUserForm( Model model, HttpSession session){
    User userSession = (User) session.getAttribute("user");
    String dniUser = userSession.getDni();

    User userBD = userService.getUserByDni(dniUser);

    model.addAttribute("user", userBD);

    boolean flagEditPassReset = resetFlagEditPass(session);
    session.setAttribute("flag_canEditPass", flagEditPassReset);

    return "/users/editProfile";
  }
  @PostMapping("/editProfile")
  public String editProfile(@RequestParam String dni, @ModelAttribute("user") @Valid User newUser, HttpSession session, BindingResult result){
    userService.updateUser(newUser,dni, false);
    session.setAttribute("flag_canEditPass", false);
    return "/users/editProfile";
  }

  @PostMapping("/editPass")
  public String editPass(HttpSession session, @RequestParam String pass, @RequestParam String dni, Model model){
    User userFound = userService.findByDniAndUserPass(dni,pass);
    User userSession = (User) session.getAttribute("user");
    if(userFound != null){
      session.setAttribute("flag_canEditPass", true);
      session.setAttribute("editPassForm", true);
    }else {
      session.setAttribute("flag_canEditPass", false);
      session.setAttribute("editPassForm", false);
      model.addAttribute("error", "Incorrect password");
    }
    model.addAttribute("user", userSession);
    return "/users/editProfile";
  }


  @PostMapping("/editUser")
  public String editUser(@RequestParam String dni, @RequestParam String name, @RequestParam String lastname, @RequestParam String email, @RequestParam String lvlAcc){
    User userEdit = new User();
    userEdit.setName(name);
    userEdit.setLastName(lastname);
    userEdit.setDni(dni);
    userEdit.setAccessLvl(lvlAcc);
    userEdit.setEmail(email);
    userService.updateUser(userEdit, dni, true);
    return "redirect:/users/list";
  }


  @PostMapping("/delete")
  public String deleteFood(@RequestParam("dni") String dni){
    userService.deleteUser(dni);
    return "redirect:/users/list";
  }
}
