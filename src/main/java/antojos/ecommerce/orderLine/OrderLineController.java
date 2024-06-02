package antojos.ecommerce.orderLine;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.products.Product;
import antojos.ecommerce.products.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderLine")
public class OrderLineController { //CREO Q PUEDO ELIMINAR ESTA CLASE
//  private OrderLineService orderLineService;
//
//  public OrderLineController(OrderLineService orderLineService) {
//    this.orderLineService = orderLineService;
//  }
//
//
//
//
//
//  @GetMapping("/list")
//  public String listOrderLine(Model model){
//    List<OrderLine> orderLineList = orderLineService.getAllOrderLines();
//    model.addAttribute("orderlines", orderLineList);
//    return "";
//  }
//
//
//  @GetMapping("/add")
//  public String addOL(Model model){
//    model.addAttribute("orderline", new OrderLine());
//    return "/";
//  }
//  @PostMapping("/add")
//  public String addOLForm(@ModelAttribute OrderLine orderLine){
//    orderLineService.addOrderLine(orderLine);
//    return "/";
//  }
//
//
//  @GetMapping("/edit/{number}")
//  public String editOLForm(@PathVariable Long nro, Model model){
//    OrderLine orderLine = orderLineService.getOrderLineByID(nro);
//    model.addAttribute("orderline", orderLine);
//    return "";
//  }
//  @PostMapping("/edit/{number}")
//  public String editOL(@PathVariable Long nro, @ModelAttribute OrderLine orderLine){
//    orderLineService.updateOrderLine(orderLine);
//    return "";
//  }
//
//
//  @GetMapping("/delete/{number}")
//  public String deleteOL(@PathVariable Long nro){
//    orderLineService.deleteOrderLine(nro);
//    return "";
//  }
}