package antojos.ecommerce.order;

import org.springframework.stereotype.Service;

import antojos.ecommerce.user.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
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
  public List<Order> getByUserAndDate(User user, Date dateFrom, Date dateTo){
    return orderRepository.findByUserAndDateBetween(user, dateTo, dateTo);
  }
  public Order getShopByCod(Long cod){
    return orderRepository.findById(cod).orElse(null);
  }


  public void addShop(Order order){
    orderRepository.save(order);
  }


  public void updateShopping(Order order){
    Long cod = order.getCod();
    Optional<Order> existingShop = orderRepository.findById(cod);
    if(existingShop.isPresent()){
      Order updatedShop = existingShop.get();
      updatedShop.setCod(cod);
      updatedShop.setDate(order.getDate());
      updatedShop.setTotPrice(order.getTotPrice());
      updatedShop.setUser(order.getUser());
      //MODIFICAR

      orderRepository.save(updatedShop);
    }
  }


  public void deleteShopping(Long cod){
    orderRepository.deleteById(cod);
  }
}
