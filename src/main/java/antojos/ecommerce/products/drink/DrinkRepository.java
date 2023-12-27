package antojos.ecommerce.products.drink;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DrinkRepository extends JpaRepository<Drink, Long>{
  List<Drink> findAll();
}
