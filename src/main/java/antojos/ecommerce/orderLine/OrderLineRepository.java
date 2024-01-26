package antojos.ecommerce.orderLine;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
  OrderLine findByProductAndOrder(Product product, Order order);
  List<OrderLine> findByOrder(Order order);

//  @Query("SELECT ol FROM OrderLine ol WHERE ol.order = :order AND ol.product = :product")
//  OrderLine findByProdIdAndOrderCod(@Param("product") Product product, @Param("order") Order order);
}