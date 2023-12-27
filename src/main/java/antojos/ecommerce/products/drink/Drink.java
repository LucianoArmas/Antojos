package antojos.ecommerce.products.drink;

import java.util.List;

import antojos.ecommerce.products.Product;
import antojos.ecommerce.shopping.Shopping;


public class Drink extends Product{
  private Float mililts;

  public Drink() {
  }

  public Drink(Long id, String name, String desc, Float price, List<Shopping> shoppings, Float mililts) {
    super(id, name, desc, price, shoppings);
    this.mililts = mililts;
  }

  public Float getMililts() {
    return mililts;
  }

  public void setMililts(Float mililts) {
    this.mililts = mililts;
  }
  
}
