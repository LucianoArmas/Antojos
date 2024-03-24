package antojos.ecommerce.products;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import antojos.ecommerce.products.drink.Drink;
import antojos.ecommerce.products.drink.DrinkService;
import antojos.ecommerce.products.food.Food;
import antojos.ecommerce.products.food.FoodService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

  @GetMapping("/searchAdmin")
  public String searchProdsAdm(@RequestParam("query") String query, Model model){
    Map<String, List<Product>> productsByType = productService.getProducts(query);

    model.addAttribute("product", new Product());
    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));

    return "/products/prodlist";
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



  @PostMapping("/resetErrorNewProd")
  public String resetErrorNewProd(HttpSession session){
    if ((session.getAttribute("error_showed") != null) && (session.getAttribute("error_showed").equals(true))){
      session.removeAttribute("prodExist_error");
      session.setAttribute("error_showed", false);
    }
    return "redirect:/products/prodsList";
  }

  @GetMapping("/prodsList")
  public String productList(Model model, HttpSession session){
    Map<String, List<Product>> productsByType = productService.getProducts("");

    model.addAttribute("product", new Product());
    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));

//    resetErrorShowed(session);
//    session.setAttribute("error_showed", false);

    return "/products/prodlist";
  }






  private void saveImage(String prodName, MultipartFile img){
    String fileName = prodName+".png";
    String path = "src/main/resources/static/imgs/"+fileName;
    System.out.println(path);
    try{
      Files.write(Paths.get(path), img.getBytes());
    }catch (IOException e){
      e.printStackTrace();
    }
  }


  @PostMapping("/newProd")
  //, @RequestParam("imageProd") MultipartFile img
  public String createNewProd(@ModelAttribute("product") Product product, Model model, HttpSession session, @RequestParam("lts") float lts, @RequestParam("amountPeople") int amountPeople, @RequestParam("editType") String type, @RequestParam("imageProd") MultipartFile img){
    boolean flag_prodExist = productService.verifyProdByName(product.getName());


    if (!flag_prodExist){
      if (Objects.equals(type, "food")){
        Food food = new Food();
        food.setAmountPeopleEat(amountPeople);
        food.setStock(product.getStock());
        food.setName(product.getName());
        food.setDescription(product.getDescription());
        food.setPrice(product.getPrice());

        foodService.addFood(food);

      }else if(Objects.equals(type, "drink")){
        Drink drink = new Drink();
        drink.setMililts(lts);
        drink.setStock(product.getStock());
        drink.setName(product.getName());
        drink.setDescription(product.getDescription());
        drink.setPrice(product.getPrice());

        drinkService.addDrink(drink);

      }
      saveImage(product.getName(), img);
    }else {
      session.setAttribute("prodExist_error", "The product: " + product.getName() + " already exists ");
      session.setAttribute("error_showed", true);
    }

    return "redirect:/products/prodsList";
  }




  @GetMapping("/delete/{id}")
  public String deleteProd(@PathVariable Long id){
    productService.deleteProduct(id);
    return "redirect:/products/list";
  }


  
}
