package antojos.ecommerce.user;

import java.util.List;
import java.util.Objects;

import antojos.ecommerce.auth.Verifier;
import antojos.ecommerce.jwt.JwtService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("users")//
public class UserController {
  private UserService userService;
  private Verifier verifier;
  private JwtService jwtService;
  private UserDetailsService userDetailsService;
  private PasswordEncoder passwordEncoder;



  @GetMapping("/list")
  public String listUsers(Model model, HttpSession session){

    String tokenInSession = (String) session.getAttribute("token");
    User userInSession = (User) session.getAttribute("user");

    if (verifier.verifyToken(tokenInSession,userInSession.getDni())){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){

        List<User> users = userService.getAllUsersExceptAdmin(userInSession.getDni());
        model.addAttribute("users", users);
        return "users/userlist";//

      } else{
        return "users/login";//
      }
    }else{
      return "users/login";//
    }

  }


  @GetMapping("/searchAdmin")
  public String searchUser_Admin(@RequestParam("query") String query, Model model, HttpSession session){

    User userInSession = (User) session.getAttribute("user");
    String tokenInSession = (String) session.getAttribute("token");

    if (verifier.verifyToken(tokenInSession,userInSession.getDni())){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        List<User> userList = userService.findByDniOrName(query, userInSession.getDni());

        if(!userList.isEmpty()){
          model.addAttribute("users", userList);
        }else {
          model.addAttribute("userNotFounded", "The user: " + query + " not exists");
        }
        return "users/userlist";//

      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }




  }



  @GetMapping("/add") //MEPA Q NO SE USA
  public String addUserForm(Model model){
    model.addAttribute("user", new User());
    return "users/add";
  }
  @PostMapping("/add") //MEPA Q NO SE USA
  public String addUser(@ModelAttribute User user){
    userService.addUser(user);
    return "redirect:users/list";//
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

    String tokenInSession = (String) session.getAttribute("token");

    if (verifier.verifyToken(tokenInSession,dniUser)){
      if (verifier.verifyRole(session) != null){
        User userBD = userService.getUserByDni(dniUser);

        model.addAttribute("user", userBD);

        boolean flagEditPassReset = resetFlagEditPass(session);
        session.setAttribute("flag_canEditPass", flagEditPassReset);

        return "users/editProfile";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }


  }



  @PostMapping("/editProfile")
  public String editProfile(@RequestParam String dni, @RequestParam String token, @ModelAttribute("user") @Valid User newUser, HttpSession session, BindingResult result){

    if(verifier.verifyToken(token,dni)){
      if (verifier.verifyRole(session) != null){
        userService.updateUser(newUser,dni, false);
        session.setAttribute("flag_canEditPass", false);
        return "users/editProfile";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }

  @PostMapping("/editPass")
  public String editPass(HttpSession session, @RequestParam String pass, @RequestParam String token, @RequestParam String dni, Model model){

    if (verifier.verifyToken(token,dni)){

      if (verifier.verifyRole(session) != null){
        User userByDni = userService.getUserByDni(dni);
        String passwordEncodedInBD = userByDni.getPassword();
        boolean flag_passwordsMatches = passwordEncoder.matches(pass, passwordEncodedInBD);

        User userSession = (User) session.getAttribute("user");

        if (flag_passwordsMatches){
          session.setAttribute("flag_canEditPass", true);
          session.setAttribute("editPassForm", true);
        }else {
          session.setAttribute("flag_canEditPass", false);
          session.setAttribute("editPassForm", false);
          model.addAttribute("error", "Incorrect userPass");
        }

        model.addAttribute("user", userSession);
        return "users/editProfile";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }


  @PostMapping("/editUser")
  public String editUser(@RequestParam String dni, @RequestParam String name, @RequestParam String lastname, @RequestParam String email, @RequestParam Role role, HttpSession session){

    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        User userEdit = new User();
        userEdit.setName(name);
        userEdit.setLastName(lastname);
        userEdit.setDni(dni);
        userEdit.setRole(role);
        userEdit.setEmail(email);
        userService.updateUser(userEdit, dni, true);
        return "redirect:users/list";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }


  @PostMapping("/delete")
  public String deleteUser(@RequestParam("dni") String dni, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        userService.deleteUser(dni);
        return "redirect:users/list";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }

  }


}
