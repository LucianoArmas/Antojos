package antojos.ecommerce.products.food;

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
@RequestMapping("foods")//
public class FoodController {
  private FoodService foodService;
  private Verifier verifier;


  @PostMapping("/edit")
  public String editFood(@RequestParam Long id, @RequestParam String name, @RequestParam String desc, @RequestParam Float price, @RequestParam int stock, @RequestParam int amountPeople, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        Food food = new Food();
        food.setId(id);
        food.setName(name);
        food.setDescription(desc);
        food.setPrice(price);
        food.setStock(stock);
        food.setAmountPeopleEat(amountPeople);
        foodService.updateFood(food);
        return "redirect:products/prodsList";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }

  }



  private void deleteImgFood(Long id) throws IOException{
    Food food = foodService.getFoodById(id);

    if(food != null){
      String path = "src/main/resources/static/imgs/"+food.getName()+".png";
        Files.deleteIfExists(Paths.get(path));
    }
  }


  @PostMapping("/delete")
  public String deleteFood(@RequestParam("id") Long id, HttpSession session) throws IOException {
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        deleteImgFood(id);
        foodService.deleteFood(id);
        return "redirect:products/prodsList";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }



  //CREO Q PUEDO ELIMINAR ESTOS METODOS DE ABAJO
//  @GetMapping("/list")
//  public String listFoods(Model model){
//    List<Food> foods = foodService.getAllFoods();
//    model.addAttribute("foods", foods);
//    return "foods/list";
//  }
//
//
//  @GetMapping("/add")
//  public String addFoodForm(Model model){
//    model.addAttribute("food", new Food());
//    return "foods/add";
//  }
//  @PostMapping("/add")
//  public String addFood(@ModelAttribute Food food){
//    foodService.addFood(food);
//    return "redirect:/foods/list";
//  }



}
