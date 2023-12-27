package antojos.ecommerce.shopping;

import org.springframework.stereotype.Service;

import antojos.ecommerce.user.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingService {
  private final ShoppingRepository shoppingRepository;

  public ShoppingService(ShoppingRepository shoppingRepository) {
    this.shoppingRepository = shoppingRepository;
  }

  public List<Shopping> getAllShoppings(){
    return shoppingRepository.findAll();
  }
  

  public List<Shopping> getByDate(Date dateFrom, Date dateTo){
    return shoppingRepository.findByDateBetween(dateFrom, dateTo);
  }
  public List<Shopping> getByUser(User user){
    return shoppingRepository.findByUser(user);
  }
  public List<Shopping> getByUserAndDate(User user, Date dateFrom, Date dateTo){
    return shoppingRepository.findByUserAndDateBetween(user, dateTo, dateTo);
  }
  public Shopping getShopByCod(Long cod){
    return shoppingRepository.findById(cod).orElse(null);
  }


  public void addShop(Shopping shopping){
    shoppingRepository.save(shopping);
  }


  public void updateShopping(Shopping shopping){
    Long cod = shopping.getCod();
    Optional<Shopping> existingShop = shoppingRepository.findById(cod);
    if(existingShop.isPresent()){
      Shopping updatedShop = existingShop.get();
      updatedShop.setCod(cod);
      updatedShop.setDate(shopping.getDate());
      updatedShop.setTotPrice(shopping.getTotPrice());
      updatedShop.setUser(shopping.getUser());
      updatedShop.setProd(shopping.getProd());

      shoppingRepository.save(updatedShop);
    }
  }


  public void deleteShopping(Long cod){
    shoppingRepository.deleteById(cod);
  }
}
