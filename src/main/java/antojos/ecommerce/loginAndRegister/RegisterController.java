package antojos.ecommerce.loginAndRegister;

import antojos.ecommerce.auth.AuthResponse;
import antojos.ecommerce.auth.AuthService;
import antojos.ecommerce.auth.RegisterRequest;
import antojos.ecommerce.user.User;
import antojos.ecommerce.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController{
  private UserService userService;
  private AuthService authService;

//  public RegisterController(UserService userService) {
//    this.userService = userService;
//  }

  @GetMapping
  public String showRegisterForm(Model model){
    model.addAttribute("user", new User());
    return "/users/register";
  }

  @PostMapping
  public String register(@ModelAttribute("user") @Valid User user, BindingResult result, Model model){

    if (result.hasErrors()) {
      return "/users/register";
    }

    if ((userService.getUserByDni(user.getDni())) != null) {
      result.rejectValue("dni", "error.user", "User already exists");
      return "/users/register";
    }

    RegisterRequest request = new RegisterRequest(user.getDni(), user.getName(), user.getLastName(), user.getEmail(), user.getPassword());
    AuthResponse authResponse = authService.register(request);

    if (authResponse != null && authResponse.getToken() != null){
      return "/users/login";
    }else {
      result.reject("There was a problem");
      return "/users/register";
    }

  }

}
