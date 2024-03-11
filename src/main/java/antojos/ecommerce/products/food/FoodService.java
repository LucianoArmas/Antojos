package antojos.ecommerce.products.food;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FoodService {
  private final FoodRepository foodRepository;

  public FoodService(FoodRepository foodRepository) {
    this.foodRepository = foodRepository;
  }

  public List<Food> getAllFoods(){
    return foodRepository.findAll();
  }

  public Food getFoodById(Long id){
    return foodRepository.findById(id).orElse(null);
  }

  public void addFood(Food food){
    foodRepository.save(food);
  }

  public void updateFood(Food food){
    Long id = food.getId();
    Optional<Food> existingFood = foodRepository.findById(id);
    if(existingFood.isPresent()){
      Food updatedFood = existingFood.get();
      updatedFood.setId(id);
      updatedFood.setName(food.getName());
      updatedFood.setDescription(food.getDescription());
      updatedFood.setPrice(food.getPrice());
      updatedFood.setAmountPeopleEat(food.getAmountPeopleEat());
      updatedFood.setStock(food.getStock());

      foodRepository.save(updatedFood);
    }
    //MANEJAR EL CASO DONDE EL OBJ NO EXISTE
  }
  
}
