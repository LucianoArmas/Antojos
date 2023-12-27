package antojos.ecommerce.shopping;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shoppings")
public class ShoppingController {
  private ShoppingService shoppingService;

  public ShoppingController(ShoppingService shoppingService) {
    this.shoppingService = shoppingService;
  }

  @GetMapping("/list")
  public String listShops(Model model){
    List<Shopping> shops = shoppingService.getAllShoppings();
    model.addAttribute("shops", shops);
    return "shoppings/list";
  }


  @GetMapping("/add")
  public String addShopForm(Model model){
    model.addAttribute("shop", new Shopping());
    return "shoppings/add";
  }
  @PostMapping("/add")
  public String addShop(@ModelAttribute Shopping shop){
    shoppingService.addShop(shop);
    return "redirect:/shoppings/list";
  }


  @GetMapping("/edit/{cod}")
  public String editShopForm(@PathVariable Long cod, Model model){
    Shopping shop = shoppingService.getShopByCod(cod);
    model.addAttribute("shop", shop);
    return "shoppings/edit";
  }
  @PostMapping("/edit/{cod}")
  public String editShop(@PathVariable Long cod, @ModelAttribute Shopping shopping){
    shoppingService.updateShopping(shopping);
    return "redirect:/shoppings/list";
  }


  @GetMapping("/delete/{cod}")
  public String deleteShgop(@PathVariable Long cod){
    shoppingService.deleteShopping(cod);
    return "redirect:/shoppings/list";
  }

}
