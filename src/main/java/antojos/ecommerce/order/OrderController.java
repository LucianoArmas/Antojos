package antojos.ecommerce.order;

import java.util.List;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.products.Product;
import antojos.ecommerce.products.ProductService;
import antojos.ecommerce.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/orders")
public class OrderController {
  private OrderService orderService;

  private ProductService productService;

  public OrderController(OrderService orderService, ProductService productService) {
    this.orderService = orderService;
    this.productService = productService;
  }

  @GetMapping("/cart")
  public String viewCart(Model model, HttpSession session){
    Order order = (Order) session.getAttribute("order");
//    List<OrderLine> orderLineList = orderService.getOrderLinesFromSession(session);
    List<OrderLine> orderLineList = order.getOrderLineList();

    model.addAttribute("orderLines",orderLineList );
    return "/order/orderLines";
  }

  @PostMapping("/addToCart")
  public String addToCart(@RequestParam Long id, HttpSession session){
    Product product = productService.getProductById(id);
    Order order = (Order) session.getAttribute("order");
    orderService.addProductToOrder(product, order, session);
    return "redirect:/";
  }




  @GetMapping("/list")
  public String listShops(Model model){
    List<Order> shops = orderService.getAllOrders();
    model.addAttribute("orders", shops);
    return "shoppings/list";
  }



  @GetMapping("/add")
  public String addShopForm(Model model){
    model.addAttribute("order", new Order());
    return "shoppings/add";
  }
  @PostMapping("/add")
  public String addShop(@ModelAttribute Order shop){
    orderService.addOrder(shop);
    return "redirect:/shoppings/list";
  }


  @GetMapping("/edit/{cod}")
  public String editShopForm(@PathVariable Long cod, Model model){
    Order shop = orderService.getShopByCod(cod);
    model.addAttribute("order", shop);
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
