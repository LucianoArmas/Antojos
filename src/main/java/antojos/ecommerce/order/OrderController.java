package antojos.ecommerce.order;

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
public class OrderController {
  private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/list")
  public String listShops(Model model){
    List<Order> shops = orderService.getAllShoppings();
    model.addAttribute("shops", shops);
    return "shoppings/list";
  }


  @GetMapping("/add")
  public String addShopForm(Model model){
    model.addAttribute("shop", new Order());
    return "shoppings/add";
  }
  @PostMapping("/add")
  public String addShop(@ModelAttribute Order shop){
    orderService.addShop(shop);
    return "redirect:/shoppings/list";
  }


  @GetMapping("/edit/{cod}")
  public String editShopForm(@PathVariable Long cod, Model model){
    Order shop = orderService.getShopByCod(cod);
    model.addAttribute("shop", shop);
    return "shoppings/edit";
  }
  @PostMapping("/edit/{cod}")
  public String editShop(@PathVariable Long cod, @ModelAttribute Order order){
    orderService.updateShopping(order);
    return "redirect:/shoppings/list";
  }


  @GetMapping("/delete/{cod}")
  public String deleteShgop(@PathVariable Long cod){
    orderService.deleteShopping(cod);
    return "redirect:/shoppings/list";
  }

}
