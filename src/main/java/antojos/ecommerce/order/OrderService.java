package antojos.ecommerce.order;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.orderLine.OrderLineRepository;
import antojos.ecommerce.products.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import antojos.ecommerce.user.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  private final OrderLineRepository orderLineRepository;

  public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
    this.orderRepository = orderRepository;
    this.orderLineRepository = orderLineRepository;
  }

  public List<Order> getAllOrders(){
    return orderRepository.findAll();
  }
  

  public List<Order> getByDate(Date dateFrom, Date dateTo){
    return orderRepository.findByDateBetween(dateFrom, dateTo);
  }
  public List<Order> getByUser(User user){
    return orderRepository.findByUser(user);
  }

  public Order getByUserAndState(User user, String state){return orderRepository.findByUserAndState(user, state);}
  public List<Order> getByUserAndDate(User user, Date dateFrom, Date dateTo){
    return orderRepository.findByUserAndDateBetween(user, dateTo, dateTo);
  }
  public Order getShopByCod(Long cod){
    return orderRepository.findById(cod).orElse(null);
  }


  public void addOrder(Order order){
    orderRepository.save(order);
  }


  public void updateShopping(Order order){
    Long cod = order.getCod();
    Optional<Order> existingShop = orderRepository.findById(cod);
    if(existingShop.isPresent()){
      Order updatedShop = existingShop.get();
      updatedShop.setCod(cod);
      updatedShop.setDate(order.getDate());
      updatedShop.setUser(order.getUser());
      updatedShop.setOrderLineList(order.getOrderLineList());
      updatedShop.setTotPrice(order.calcuTotal());
      //MODIFICAR

      orderRepository.save(updatedShop);
    }
  }

  public void addProductToOrder(Product product, Order order, HttpSession session){
    OrderLine orderLine = orderLineRepository.findByProductAndOrder(product, order);
    if (orderLine == null){
      orderLine = new OrderLine();
      orderLine.setProduct(product);
      orderLine.setOrder(order);
      orderLine.setSubTotPrice(product.getPrice());
      orderLine.setQuantityProds(1);
    }else{
      orderLine.setQuantityProds(orderLine.getQuantityProds()+1);
      orderLine.setSubTotPrice(orderLine.getSubTotPrice()+(product.getPrice()));
    }
    orderLineRepository.save(orderLine);

    List<OrderLine> orderLineList = getOrderLinesFromSession(session);
    orderLineList.add(orderLine);
    updateOrderLinesInSession(session, orderLineList);

    order.setOrderLineList(orderLineList);
    order.calcuTotal();
    orderRepository.save(order);

  }


  public List<OrderLine> getOrderLineByOrder(Order order){
    return orderLineRepository.findByOrder(order);
  }


  public List<OrderLine> getOrderLinesFromSession(HttpSession session){
    Order order = (Order) session.getAttribute("order");
    return getOrderLineByOrder(order);

//    Object orderLines = session.getAttribute("orderLines");
//    if(orderLines instanceof List<?>){
//      return (List<OrderLine>) orderLines;
//    }else {
//      return Collections.emptyList();
//    }
  }
  public void updateOrderLinesInSession(HttpSession session, List<OrderLine> orderLineList){
    session.setAttribute("orderLineList", orderLineList);
  }


  public void deleteShopping(Long cod){
    orderRepository.deleteById(cod);
  }
}
