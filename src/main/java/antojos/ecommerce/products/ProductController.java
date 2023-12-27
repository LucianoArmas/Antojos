package antojos.ecommerce.products;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/list")
  public String listUsers(Model model){
    List<Product> products = productService.getAllProducts();
    model.addAttribute("products", products);
    return "products/list";
  }
  @GetMapping("/delete/{id}")
  public String deleteUser(@PathVariable Long id){
    productService.deleteProduct(id);
    return "redirect:/products/list";
  }
  
}
