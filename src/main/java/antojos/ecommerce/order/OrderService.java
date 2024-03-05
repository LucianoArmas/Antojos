package antojos.ecommerce.order;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.orderLine.OrderLineRepository;
import antojos.ecommerce.products.Product;
import antojos.ecommerce.products.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
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

  private final ProductRepository productRepository;

  public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.orderLineRepository = orderLineRepository;
    this.productRepository = productRepository;
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
  public List<Order> getOrdersByUserAndState(User user, String state){return orderRepository.findOrdersByUserAndState(user,state);}
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



  public void updateOrderPending(HttpSession session, Order order, OrderLine orderLine, boolean flag_newOL, boolean flag_DeleteOL){
    List<OrderLine> orderLineList = (List<OrderLine>) session.getAttribute("orderLineList");

    if(flag_newOL){
      orderLineList.add(orderLine);
    } else{
      for (OrderLine ol : orderLineList) {
        if (Objects.equals(ol.getNro(), orderLine.getNro())) {
          orderLineList.remove(ol);
          orderLineList.add(orderLine);
          break;
        }
      }
    }

    if(flag_DeleteOL){
      for (OrderLine ol : orderLineList) {
        if (Objects.equals(ol.getNro(), orderLine.getNro())) {
          orderLineList.remove(ol);
          break;
        }
      }
    }

    updateOrderLinesInSessionOrderPending(session, orderLineList);

    order.setOrderLineList(orderLineList);

    order.calcuTotal();
    orderRepository.save(order);
    session.setAttribute("orderPending", order);

  }

  public boolean verifyProdStock(Product product, int quantityProdToAdd){
    return product.getStock() >= quantityProdToAdd;
  }

  public void addProductToOrder(Product product, Order order, HttpSession session){
    OrderLine orderLine = orderLineRepository.findByProductAndOrder(product, order);
    boolean flag_thereIsStock;
    if (orderLine == null){
      flag_thereIsStock = verifyProdStock(product,1);
      if(flag_thereIsStock){
        orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setOrder(order);
        orderLine.setSubTotPrice(product.getPrice());
        orderLine.setQuantityProds(1);
        orderLineRepository.save(orderLine);
        updateOrderPending(session, order, orderLine, true, false);
      }
    }else{
      flag_thereIsStock = verifyProdStock(product,(orderLine.getQuantityProds()+1));
      if(flag_thereIsStock){
        orderLine.setQuantityProds(orderLine.getQuantityProds()+1);
        orderLine.setSubTotPrice(orderLine.getQuantityProds()*(product.getPrice()));
        orderLineRepository.save(orderLine);
        updateOrderPending(session, order, orderLine, false, false);
      }
    }

    if (!flag_thereIsStock){
      System.out.println("NO HAY STOCK"); //Ver como manejar la situacion en caso de no haber stock
    }

  }


  @Transactional
  public void updateProdStock(Long id, int newStock){
    productRepository.updateProductStock(id, newStock);
  }

  public void addProdToOrderFromCart(HttpSession session, Order order, OrderLine orderLine){
    boolean flag_thereIsStock = verifyProdStock(orderLine.getProduct(), (orderLine.getQuantityProds()+1));
    if(flag_thereIsStock){
      modProdCountFromCart(session, order, orderLine, 1);
    }else {
      System.out.println("NO HAY STOCK"); //Ver como manejar la situacion en caso de no haber stock
    }
  }

  public void deleteProdToOrderFromCart(HttpSession session, Order order, OrderLine orderLine){
    modProdCountFromCart(session, order, orderLine, -1);
  }

  private void modProdCountFromCart(HttpSession session, Order order, OrderLine orderLine, int quantMod){
    boolean flag_DeleteOL = false;
    orderLine.setQuantityProds(orderLine.getQuantityProds() + quantMod);
    if (orderLine.getQuantityProds() > 0) {
      orderLine.setSubTotPrice(orderLine.getSubTotPrice() + (quantMod) * (orderLine.getProduct().getPrice()));
    }else {
      orderLineRepository.delete(orderLine);
      flag_DeleteOL = true;
    }
    updateOrderPending(session, order, orderLine, false, flag_DeleteOL);
  }

  public OrderLine getOrderLineByNumbAndCodeOrder(Long numbOrderLine, Long codOrder){
    return orderLineRepository.findByNumberAndOrderCod(numbOrderLine, codOrder);
  }


  public List<OrderLine> getOrderLineByOrder(Order order){
    return orderLineRepository.findByOrder(order);
  }


  public List<OrderLine> getOrderLinesFromSession(HttpSession session){
    Order order = (Order) session.getAttribute("orderPending");
    return getOrderLineByOrder(order);

//    Object orderLines = session.getAttribute("orderLines");
//    if(orderLines instanceof List<?>){
//      return (List<OrderLine>) orderLines;
//    }else {
//      return Collections.emptyList();
//    }
  }
  public void updateOrderLinesInSessionOrderPending(HttpSession session, List<OrderLine> orderLineList){
    session.setAttribute("orderLineList", orderLineList);
  }

  public void acceptOrder(Order order, HttpSession session){
    order.setState("accepted");
    orderRepository.save(order);
    renewOrderPending(session);
  }

  public void renewOrderPending(HttpSession session){
    session.removeAttribute("orderLineList");
    session.removeAttribute("orderPending");

    User user = (User) session.getAttribute("user");

    Order order = getByUserAndState(user, "pending");

    if(order == null){
      Date today = new Date();
      order = new Order(user,today, (float) 0,"pending");
      addOrder(order);
    }

    session.setAttribute("orderPending", order);
    session.setAttribute("orderLineList", getOrderLinesFromSession(session));
  }


  public void cancelOrder(Long cod){
    Order order = getOrderByCod(cod);
    order.setState("cancelled");
    orderRepository.save(order);
  }


  public void deleteOrder(Long cod){
    orderRepository.deleteById(cod);
  }
}
