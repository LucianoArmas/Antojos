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
  public void updateOrderLine(OrderLine oRderLine){
    Long nro = oRderLine.getNro();
    Optional<OrderLine> existingOrderLine = orderLineRepository.findById(nro);
    if(existingOrderLine.isPresent()){
      OrderLine updatedOL = existingOrderLine.get();
      updatedOL.setNro(nro);
      updatedOL.setQuantityProds(oRderLine.getQuantityProds());
      updatedOL.setSubTotPrice(oRderLine.getSubTotPrice());
      updatedOL.setOrder(oRderLine.getOrder());
      updatedOL.setProduct(oRderLine.getProduct());

      orderLineRepository.save(updatedOL);
    }
  }
}
