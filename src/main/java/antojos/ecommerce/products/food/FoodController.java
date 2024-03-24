package antojos.ecommerce.products.food;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/foods")
public class FoodController {
  private FoodService foodService;

  public FoodController(FoodService foodService) {
    this.foodService = foodService;
  }


  @PostMapping("/edit")
  public String editFood(@RequestParam Long id, @RequestParam String name, @RequestParam String desc, @RequestParam Float price, @RequestParam int stock, @RequestParam int amountPeople){
    Food food = new Food();
    food.setId(id);
    food.setName(name);
    food.setDescription(desc);
    food.setPrice(price);
    food.setStock(stock);
    food.setAmountPeopleEat(amountPeople);
    foodService.updateFood(food);
    return "redirect:/products/prodsList";
  }



  private void deleteImgFood(Long id){
    Food food = foodService.getFoodById(id);

    if(food != null){
      String path = "src/main/resources/static/imgs/"+food.getName()+".png";
      try {
        Files.deleteIfExists(Paths.get(path));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @PostMapping("/delete")
  public String deleteFood(@RequestParam("id") Long id){
    deleteImgFood(id);
    foodService.deleteFood(id);
    return "redirect:/products/prodsList";
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



}
