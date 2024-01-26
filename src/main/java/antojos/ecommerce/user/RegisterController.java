package antojos.ecommerce.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController{
  private UserService userService;

  public RegisterController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String showRegisterForm(Model model){
    model.addAttribute("user", new User());
    return "/users/register";
  }

  @PostMapping
  public String getInfoRegisterForm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model){

    if (result.hasErrors()) {
      return "/users/register";
    }

    if ((userService.getUserByDni(user.getDni())) != null) {
      result.rejectValue("dni", "error.user", "User already exists");
      return "/users/register";
    }

    user.setAccessLvl("client");

    userService.addUser(user);

    return "redirect:/";
  }

}
