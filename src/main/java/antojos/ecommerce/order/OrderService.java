package antojos.ecommerce.order;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.orderLine.OrderLineRepository;
import antojos.ecommerce.products.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import antojos.ecommerce.user.User;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
  public Order getOrderByCod(Long cod){
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

  public void updateOrder(HttpSession session, Order order, OrderLine orderLine, boolean flag_newOL){
    orderLineRepository.save(orderLine);
    List<OrderLine> orderLineList = getOrderLinesFromSession(session);

    if(flag_newOL){
      orderLineList.add(orderLine);
    } else {
      for (OrderLine ol : orderLineList) {
        if (Objects.equals(ol.getNro(), orderLine.getNro())) {
          orderLineList.remove(ol);
          orderLineList.add(orderLine);
          break;
        }
      }
    }

    updateOrderLinesInSession(session, orderLineList);

    order.setOrderLineList(orderLineList);



    order.calcuTotal();
    orderRepository.save(order);
    session.setAttribute("order", order);
  }

  public void addProductToOrder(Product product, Order order, HttpSession session){
    OrderLine orderLine = orderLineRepository.findByProductAndOrder(product, order);
    boolean flag_newOL = false;
    if (orderLine == null){
      orderLine = new OrderLine();
      orderLine.setProduct(product);
      orderLine.setOrder(order);
      orderLine.setSubTotPrice(product.getPrice());
      orderLine.setQuantityProds(1);
      flag_newOL = true;
    }else{
      orderLine.setQuantityProds(orderLine.getQuantityProds()+1);
//      orderLine.setSubTotPrice(orderLine.getSubTotPrice()+(product.getPrice()));
      orderLine.setSubTotPrice((float) 0);
      orderLine.setSubTotPrice(orderLine.getQuantityProds()*(product.getPrice()));
    }
    updateOrder(session, order, orderLine, flag_newOL);

  }

  public void addProdToOrderFromCart(HttpSession session, Order order, OrderLine orderLine){
    orderLine.setQuantityProds(orderLine.getQuantityProds()+1);
    orderLine.setSubTotPrice(orderLine.getSubTotPrice()+(orderLine.getProduct().getPrice()));
    updateOrder(session, order, orderLine, false);
  }

  public OrderLine getOrderLineByNumbAndCodeOrder(Long numbOrderLine, Long codOrder){
    return orderLineRepository.findByNumberAndOrderCod(numbOrderLine, codOrder);
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
