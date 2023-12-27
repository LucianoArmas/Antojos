package antojos.ecommerce.products.food;

import java.util.List;

import antojos.ecommerce.products.Product;
import antojos.ecommerce.shopping.Shopping;

public class Food extends Product{
  private int amountPeopleEat;

  public Food(){}

  public Food(Long id, String name, String desc, Float price, List<Shopping> shoppings, int amountPeopleEat) {
    super(id, name, desc, price, shoppings);
    this.amountPeopleEat = amountPeopleEat;
  }

  public int getAmountPeopleEat() {
    return amountPeopleEat;
  }

  public void setAmountPeopleEat(int amountPeopleEat) {
    this.amountPeopleEat = amountPeopleEat;
  }

  
}
