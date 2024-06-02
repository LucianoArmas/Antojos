package antojos.ecommerce.products.drink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import antojos.ecommerce.auth.Verifier;
import antojos.ecommerce.user.Role;
import antojos.ecommerce.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/drinks")
public class DrinkController {
  private DrinkService drinkService;
  private Verifier verifier;


  @PostMapping("/edit")
  public String editDrink(@RequestParam Long id, @RequestParam String name, @RequestParam String desc, @RequestParam Float price, @RequestParam int stock, @RequestParam Float lts, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        Drink drink = new Drink();
        drink.setId(id);
        drink.setName(name);
        drink.setDescription(desc);
        drink.setPrice(price);
        drink.setStock(stock);
        drink.setLts(lts);
        drinkService.updateDrink(drink);
        return "redirect:/products/prodsList";
      }else {
        return "/users/login";
      }
    }else {
      return "/users/login";
    }
  }



  private void deleteImgDrink(Long id) throws IOException{
    Drink drink = drinkService.getDrinkById(id);

    if(drink != null){
      String path = "src/main/resources/static/imgs/"+drink.getName()+".png";
      Files.deleteIfExists(Paths.get(path));
    }
  }


  @PostMapping("delete")
  public String deleteDrink(@RequestParam("id") Long id, HttpSession session) throws IOException{
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        drinkService.deleteDrink(id);
        deleteImgDrink(id);
        return "redirect:/products/prodsList";
      }else {
        return "/users/login";
      }
    }else {
      return "/users/login";
    }
  }






  //CREO Q PUEDO ELIMINAR ESTOS METODOS DE ABAJO
//  @GetMapping("/list")
//  public String listDrinks(Model model){
//    List<Drink> drinks = drinkService.getAllDrinks();
//    model.addAttribute("drinks", drinks);
//    return "drinks/list";
//  }
//
//
//  @GetMapping("/add")
//  public String addDrinkForm(Model model){
//    model.addAttribute("drink", new Drink());
//    return "drinks/add";
//  }
//  @PostMapping("/add")
//  public String addDrink(@ModelAttribute Drink drink){
//    drinkService.addDrink(drink);
//    return "redirect:/drinks/list";
//  }


}
