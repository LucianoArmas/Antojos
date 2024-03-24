package antojos.ecommerce.products.drink;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DrinkService {
  private DrinkRepository drinkRepository;

  public DrinkService(DrinkRepository drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<Drink> getAllDrinks(){
    return drinkRepository.findAll();
  }

  public Drink getDrinkById(Long id){
    return drinkRepository.findById(id).orElse(null);
  }

  public void addDrink(Drink drink){
    drinkRepository.save(drink);
  }

  public void updateDrink(Drink drink){
    Long id = drink.getId();
    Optional<Drink> existingDrink = drinkRepository.findById(id);
    if(existingDrink.isPresent()){
      Drink updatedDrink = existingDrink.get();
      updatedDrink.setId(id);
      updatedDrink.setName(drink.getName());
      updatedDrink.setDescription(drink.getDescription());
      updatedDrink.setPrice(drink.getPrice());
      updatedDrink.setMililts(drink.getMililts());
      updatedDrink.setStock(drink.getStock());

      drinkRepository.save(updatedDrink);
    }
    //MANEJAR EL CASO DONDE EL OBJ NO EXISTE
  }


  public void deleteDrink(Long id){
    drinkRepository.deleteById(id);
  }


}
