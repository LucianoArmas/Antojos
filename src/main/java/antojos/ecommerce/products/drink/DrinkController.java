package antojos.ecommerce.products.drink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import antojos.ecommerce.products.food.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/drinks")
public class DrinkController {
  private DrinkService drinkService;

  public DrinkController(DrinkService drinkService) {
    this.drinkService = drinkService;
  }


  @PostMapping("/edit")
  public String editDrink(@RequestParam Long id, @RequestParam String name, @RequestParam String desc, @RequestParam Float price, @RequestParam int stock, @RequestParam Float lts){
    Drink drink = new Drink();
    drink.setId(id);
    drink.setName(name);
    drink.setDescription(desc);
    drink.setPrice(price);
    drink.setStock(stock);
    drink.setMililts(lts);
    drinkService.updateDrink(drink);
    return "redirect:/products/prodsList";
  }



  private void deleteImgDrink(Long id){
    Drink drink = drinkService.getDrinkById(id);

    if(drink != null){
      String path = "src/main/resources/static/imgs/"+drink.getName()+".png";
      try {
        Files.deleteIfExists(Paths.get(path));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @PostMapping("delete")
  public String deleteDrink(@RequestParam("id") Long id){
    drinkService.deleteDrink(id);
    deleteImgDrink(id);
    return "redirect:/products/prodsList";
  }






  @GetMapping("/list")
  public String listDrinks(Model model){
    List<Drink> drinks = drinkService.getAllDrinks();
    model.addAttribute("drinks", drinks);
    return "drinks/list";
  }


  @GetMapping("/add")
  public String addDrinkForm(Model model){
    model.addAttribute("drink", new Drink());
    return "drinks/add";
  }
  @PostMapping("/add")
  public String addDrink(@ModelAttribute Drink drink){
    drinkService.addDrink(drink);
    return "redirect:/drinks/list";
  }


}
