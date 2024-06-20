package antojos.ecommerce.products;

import antojos.ecommerce.products.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
  @Modifying
  @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :prodID")
  void updateProductStock(@Param("prodID") Long product,@Param("stock") int stock);

  List<Product> findByNameContainingIgnoreCase(String name);
  List<Product> findByNameIgnoreCase(String name);



}
