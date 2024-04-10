package antojos.ecommerce.orderLine;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.products.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

  private OrderLineRepository orderLineRepository;

  public OrderLineService(OrderLineRepository orderLineRepository) {
    this.orderLineRepository = orderLineRepository;
  }

  public OrderLine getOrderLineByProdIdOrderCod(Product product, Order order){return orderLineRepository.findByProductAndOrder(product, order);};

  public List<OrderLine> getAllOrderLines(){
    return orderLineRepository.findAll();
  }

  public OrderLine getOrderLineByID(Long nro){
    return orderLineRepository.findById(nro).orElse(null);
  }

  public void deleteOrderLine(Long nro){
    orderLineRepository.deleteById(nro);
  }

  public void addOrderLine(OrderLine orderLine){
    orderLineRepository.save(orderLine);
  }
  public void updateOrderLine(OrderLine orderLine){
    Long nro = orderLine.getNumber();
    Optional<OrderLine> existingOrderLine = orderLineRepository.findById(nro);
    if(existingOrderLine.isPresent()){
      OrderLine updatedOL = existingOrderLine.get();
      updatedOL.setNumber(nro);
      updatedOL.setQuantityProds(orderLine.getQuantityProds());
      updatedOL.setSubTotPrice(orderLine.getSubTotPrice());
      updatedOL.setOrder(orderLine.getOrder());
      updatedOL.setProduct(orderLine.getProduct());

      orderLineRepository.save(updatedOL);
    }
  }
}
