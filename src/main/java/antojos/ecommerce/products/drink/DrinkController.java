package antojos.ecommerce.products.drink;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/drinks")
public class DrinkController {
  private DrinkService drinkService;

  public DrinkController(DrinkService drinkService) {
    this.drinkService = drinkService;
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


  @GetMapping("/edit/{id}")
  public String editDrinkForm(@PathVariable Long id, Model model){
    Drink drink = drinkService.getDrinkById(id);
    model.addAttribute("drink", drink);
    return "drinks/edit";
  }
  @PostMapping("/edit/{id}")
  public String editDrink(@PathVariable Long id, @ModelAttribute Drink drink){
    drinkService.updateDrink(drink);
    return "redirect:/drinks/list";
  }
}
