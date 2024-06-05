package antojos.ecommerce.order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import antojos.ecommerce.auth.Verifier;
import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.products.Product;
import antojos.ecommerce.products.ProductService;
import antojos.ecommerce.user.Role;
import antojos.ecommerce.user.User;
import antojos.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("orders")//
public class OrderController {
  private OrderService orderService;
  private ProductService productService;
  private UserService userService;
  private Verifier verifier;



  @GetMapping("/cart")
  public String viewCart(Model model, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.USER)){
        Order order = (Order) session.getAttribute("orderPending");
        List<OrderLine> orderLineList = order.getOrderLineList();

        model.addAttribute("orderLines",orderLineList );
        return "order/orderLines";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }

  @PostMapping("/addToCart")
  public String addToCart(@RequestParam Long id, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.USER)){
        Product product = productService.getProductById(id);
        Order order = (Order) session.getAttribute("orderPending");
        orderService.addProductToOrder(product, order, session);
        return "redirect:";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }

  @PostMapping("/addProd")
  public String addProd(@RequestParam Long numbOL, @RequestParam Long codOrder, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.USER)){
        return processOrderProd(numbOL, codOrder, session, true);
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }

  @PostMapping("/deleteProd")
  public String deleteProd(@RequestParam Long numbOL, @RequestParam Long codOrder, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.USER)){
        return processOrderProd(numbOL, codOrder, session, false);
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }

  private String processOrderProd(Long numbOL, Long codOrder, HttpSession session, boolean isAdd){
    OrderLine orderLine = orderService.getOrderLineByNumbAndCodeOrder(numbOL, codOrder);
    Order order = orderService.getOrderByCod(codOrder);

    if (orderLine != null){
      if (isAdd){
        orderService.addProdToOrderFromCart(session, order, orderLine);
      }else {
         orderService.deleteProdToOrderFromCart(session, order, orderLine);
      }
    }

    return "order/orderLines";//
  }



  @PostMapping("/acceptOrder/{orderCod}")
  private String acceptOrder(@PathVariable Long orderCod, HttpSession session, Model model){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.USER)){
        Order order = orderService.getOrderByCod(orderCod);
        boolean flag_StockAccepted = true;
        Product prodStockNoAccepted=null; //CREO Q TENDRIA Q ELIMINAR ESTO

        for (OrderLine ol: order.getOrderLineList()) {
          if(orderService.verifyProdStock(ol.getProduct(), ol.getQuantityProds())){
            orderService.updateProdStock(ol.getProduct().getId(), (ol.getProduct().getStock() - ol.getQuantityProds()));
          }else{
            flag_StockAccepted = false;
            //ESTO DE ABAJO TMB DEBERIA ELIMINAR CREO
            prodStockNoAccepted = ol.getProduct(); //AUN NO SE COMO IMPLEMENTARLO - XQ DIRECTAMENTE NO PASA X EL FALSO, XQ SI VE Q NO HAY STOCK NO TE DEJA PONER EL PROD EN EL CART
            break;
          }
        }

        if(flag_StockAccepted){
          orderService.acceptOrder(order, session);
          return "redirect:";//
        }else{
          model.addAttribute("errorProdStock", prodStockNoAccepted); //Y ESTO TM DEBERIA ELIMINAR
          return "order/orderLines";//
        }

      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }

  }


private Map<String, List<Order>> getOrdersMap(List<Order> orderList){
    return orderService.divideOrdersByStatus(orderList);
}


@GetMapping("orderList")
public String getOrderList(Model model, HttpSession session){
  User user = (User) session.getAttribute("user");
  String tokenInSession = (String) session.getAttribute("token");
  String dniUserInSession = user.getDni();

  if (verifier.verifyToken(tokenInSession,dniUserInSession)){
    if (Objects.equals(verifier.verifyRole(session), Role.USER)){
      List<Order> orderListAccepted = orderService.getOrdersByUserAndStatus(user, "accepted");
      List<Order> orderListCancelled = orderService.getOrdersByUserAndStatus(user, "cancelled");
      List<Order> orderListDelivered = orderService.getOrdersByUserAndStatus(user, "delivered");

      model.addAttribute("ordersAccepted", orderListAccepted);
      model.addAttribute("ordersCancelled", orderListCancelled);
      model.addAttribute("ordersDelivered", orderListDelivered);

      return "order/orderList";//
    }else {
      return "users/login";//
    }
  }else {
    return "users/login";//
  }
}


@GetMapping("/orderListAdmin")
public String getOrderListAdmin(Model model, HttpSession session){
  String tokenInSession = (String) session.getAttribute("token");
  String dniUserInSession = ((User) session.getAttribute("user")).getDni();

  if (verifier.verifyToken(tokenInSession,dniUserInSession)){
    if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
      List<Order> orderList = orderService.getAllOrders();
      Map<String, List<Order>> ordersMap = getOrdersMap(orderList);

      List<Order> ordersAccepted =  ordersMap.get("accepted");
      List<Order> ordersCancelled = ordersMap.get("cancelled");
      List<Order> ordersDelivered = ordersMap.get("delivered");



      model.addAttribute("orderListAccepted", ordersAccepted);
      model.addAttribute("orderListCancelled", ordersCancelled);
      model.addAttribute("orderListDelivered", ordersDelivered);

      return "order/orderListAdmin";//
    }else {
      return "users/login";//
    }
  }else {
    return "users/login";//
  }
}



private Timestamp formatter(String stringToDate){
  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  LocalDateTime date = LocalDateTime.parse(stringToDate, dateFormatter);
  return java.sql.Timestamp.valueOf((date));
}

@GetMapping("/search")
public String searchOrder(HttpSession session,Model model, @RequestParam("userDni") String userDni, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo){
  String tokenInSession = (String) session.getAttribute("token");
  String dniUserInSession = ((User) session.getAttribute("user")).getDni();

  if (verifier.verifyToken(tokenInSession,dniUserInSession)){
    if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
      User user;
      List<Order> orderList;
      Timestamp orderDateFrom;
      Timestamp orderDateTo;

      if (dateFrom.isBlank()){
        orderDateFrom = formatter("2000-01-01 00:00:00");
      }else {
        orderDateFrom = formatter(dateFrom+" 00:00:00");
      }

      if (dateTo.isBlank()){
        String today = (LocalDate.now()).toString();
        orderDateTo = formatter(today+" 23:59:59");
      }else {
        orderDateTo = formatter(dateTo+" 23:59:59");
      }



      if (userDni.isBlank()){
        orderList = orderService.getOrdersBetweenDates(orderDateFrom, orderDateTo);
      }else{
        user = userService.getUserByDni(userDni);
        orderList = orderService.getByUserAndDate(user, orderDateFrom, orderDateTo);
      }

      Map<String, List<Order>> ordersMap = orderService.divideOrdersByStatus(orderList);

      List<Order> ordersAccepted = ordersMap.get("accepted");
      List<Order> ordersCancelled = ordersMap.get("cancelled");
      List<Order> ordersDelivered = ordersMap.get("delivered");



      model.addAttribute("orderListAccepted", ordersAccepted);
      model.addAttribute("orderListCancelled", ordersCancelled);
      model.addAttribute("orderListDelivered", ordersDelivered);

      return "order/orderListAdmin";//
    }else {
      return "users/login";//
    }
  }else {
    return "users/login";//
  }
}






@PostMapping("/modStatusAdmin")
public String modOrderStatusAdmin(@RequestParam Long cod, @RequestParam String orderStatus, HttpSession session){
  String tokenInSession = (String) session.getAttribute("token");
  String dniUserInSession = ((User) session.getAttribute("user")).getDni();

  if (verifier.verifyToken(tokenInSession,dniUserInSession)){
    if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
      Order order = orderService.getOrderByCod(cod);
      orderService.updateOrder(order, orderStatus);

      return "redirect:orders/orderListAdmin";//
    }else {
      return "users/login";//
    }
  }else {
    return "users/login";//
  }
}




@PostMapping("/cancel")
public String cancelOrder(@RequestParam Long cod, HttpSession session){
  String tokenInSession = (String) session.getAttribute("token");
  String dniUserInSession = ((User) session.getAttribute("user")).getDni();

  if (verifier.verifyToken(tokenInSession,dniUserInSession)){
    if (Objects.equals(verifier.verifyRole(session), Role.USER)){
      orderService.cancelOrder(cod);
      return "redirect:orders/orderList";//
    }else {
      return "users/login";//
    }
  }else {
    return "users/login";//
  }
}

  @PostMapping("/delete")
  public String deleteOrder(@RequestParam Long cod, HttpSession session){
    String tokenInSession = (String) session.getAttribute("token");
    String dniUserInSession = ((User) session.getAttribute("user")).getDni();

    if (verifier.verifyToken(tokenInSession,dniUserInSession)){
      if (Objects.equals(verifier.verifyRole(session), Role.ADMIN)){
        orderService.deleteOrder(cod);
        return "redirect:orders/orderListAdmin";//
      }else {
        return "users/login";//
      }
    }else {
      return "users/login";//
    }
  }




}
