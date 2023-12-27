package antojos.ecommerce.products.food;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/foods")
public class FoodController {
  private FoodService foodService;

  public FoodController(FoodService foodService) {
    this.foodService = foodService;
  }

  @GetMapping("/list")
  public String listFoods(Model model){
    List<Food> foods = foodService.getAllFoods();
    model.addAttribute("foods", foods);
    return "foods/list";
  }


  @GetMapping("/add")
  public String addFoodForm(Model model){
    model.addAttribute("food", new Food());
    return "foods/add";
  }
  @PostMapping("/add")
  public String addFood(@ModelAttribute Food food){
    foodService.addFood(food);
    return "redirect:/foods/list";
  }

  
  @GetMapping("/edit/{id}")
  public String editFoodForm(@PathVariable Long id, Model model){
    Food food = foodService.getFoodById(id);
    model.addAttribute("food", food);
    return "foods/edit";
  }
  @PostMapping("/edit/{id}")
  public String editFood(@PathVariable Long id, @ModelAttribute Food food){
    foodService.updateFood(food);
    return "redirect:/foods/list";
  }

}
