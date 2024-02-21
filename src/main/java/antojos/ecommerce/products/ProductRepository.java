package antojos.ecommerce.products;

import antojos.ecommerce.products.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
//  @Query("SELECT food FROM Product food WHERE food.product_type = :product_type")
//  List<Food> findFoodByProductType(@Param("product_type") String productType);
//  List<Product> findDrinkByProductType(String productType);
  @Modifying
  @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :prodID")
  void updateProductStock(@Param("prodID") Long product,@Param("stock") int stock);
}
