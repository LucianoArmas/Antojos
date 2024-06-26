package antojos.ecommerce.orderLine;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
  OrderLine findByProductAndOrder(Product product, Order order);
  List<OrderLine> findByOrder(Order order);

  OrderLine findByNumberAndOrderCod(Long numberOrderLine, Long codeOrder);

}