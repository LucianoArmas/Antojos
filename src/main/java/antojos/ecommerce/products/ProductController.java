package antojos.ecommerce.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import antojos.ecommerce.products.food.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/","/products"})
public class ProductController {
  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("")
  public String listProds(Model model){
    Map<String, List<Product>> productsByType = productService.getAllProducts();
    model.addAttribute("foods", productsByType.getOrDefault("food", new ArrayList<>()));
    model.addAttribute("drinks", productsByType.getOrDefault("drink", new ArrayList<>()));
//    List<Product> products = productService.getAllProducts();
//    model.addAttribute("products", products);
//    List<Food> foods = productService.getFoods();
//    model.addAttribute("foods", foods);
    return "/products/prodlist";
  }
  @GetMapping("/delete/{id}")
  public String deleteProd(@PathVariable Long id){
    productService.deleteProduct(id);
    return "redirect:/products/list";
  }
  
}
