package antojos.ecommerce.products;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import antojos.ecommerce.products.drink.Drink;
import antojos.ecommerce.products.drink.DrinkService;
import antojos.ecommerce.products.food.Food;
import antojos.ecommerce.products.food.FoodService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/","/products"})
public class ProductController {
  private ProductService productService;
  private FoodService foodService;
  private DrinkService drinkService;

  public ProductController(ProductService productService, FoodService foodService, DrinkService drinkService) {
    this.productService = productService;
    this.foodService = foodService;
    this.drinkService = drinkService;
  }


  @GetMapping("")
  public String index(Model model){
    Map<String, List<Product>> productsByType = productService.getProducts("");
    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));
    return "/products/index";
  }

  @GetMapping("/details/{id}")
  public String getDetailsProd(@PathVariable Long id, Model model){
    Product product = productService.getProductById(id);
    model.addAttribute("productSelected", product);
    return "products/details";
  }


  @GetMapping("/search")
  public String searchProds(@RequestParam("query") String query, Model model){
    Map<String, List<Product>> productsByType = productService.getProducts(query);
    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));
    return "/products/index";
  }

  private List<Product> setFilter(List<Product> productList, String filter){
    switch (filter) {
      case "alphabetic" -> {
        assert productList != null;
        productList.sort(Comparator.comparing(Product::getName));
      }
      case "lowPrice" -> {
        assert productList != null;
        productList.sort(Comparator.comparing(Product::getPrice));
      }
      case "highPrice" -> {
        assert productList != null;
        productList.sort(Comparator.comparing(Product::getPrice).reversed());
      }
    }
    return productList;
  }


  private void filterProducts(String filterFood, String filterDrink, Model model){
    Map<String, List<Product>> productsByType = productService.getProducts("");
    List<Product> foods = productsByType.getOrDefault("food", new ArrayList<>());
    List<Product> drinks = productsByType.getOrDefault("drink", new ArrayList<>());
    List<Product> foodList;
    List<Product> drinkList;

    if (!filterFood.isBlank()){
      foodList = setFilter(foods, filterFood);
      model.addAttribute("foods", foodList);
    }else{
      model.addAttribute("foods", foods);
    }

    if (!filterDrink.isBlank()){
      drinkList = setFilter(drinks, filterDrink);
      model.addAttribute("drinks", drinkList);
    }else{
      model.addAttribute("drinks", drinks);
    }
  }

  @GetMapping("/filterFood")
  public String filterFood(@RequestParam("filter") String filter, Model model, HttpSession session){

    session.setAttribute("filterFood", filter);

    String filterDrink ="";

    if(!(session.getAttribute("filterDrink") == null)){
      filterDrink = session.getAttribute("filterDrink").toString();
    }

    filterProducts(filter,filterDrink,model);

    return "/products/index";
  }

  @GetMapping("/filterDrink")
  public String filterDrink(@RequestParam("filter") String filter, Model model, HttpSession session){

    session.setAttribute("filterDrink", filter);

    String filterFood = "";
    
    if (!(session.getAttribute("filterFood") == null)){
      filterFood = session.getAttribute("filterFood").toString();
    }

    filterProducts(filterFood, filter, model);

    return "/products/index";
  }

  @GetMapping("/prodsList")
  public String productList(Model model){
    Map<String, List<Product>> productsByType = productService.getProducts("");

    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));

    return "/products/prodlist";
  }




  @GetMapping("/delete/{id}")
  public String deleteProd(@PathVariable Long id){
    productService.deleteProduct(id);
    return "redirect:/products/list";
  }


  
}
