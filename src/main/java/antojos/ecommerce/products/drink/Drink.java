package antojos.ecommerce.products.drink;

import java.util.List;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.products.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("drink")
public class Drink extends Product{
  private Float mililts;

  public Drink() {
  }

  public Drink(Long id, String name, String desc, Float price, int stock, List<OrderLine> orderLineList, Float mililts) {
    super(id, name, desc, price, stock, orderLineList);
    this.mililts = mililts;
  }

  public Float getMililts() {
    return mililts;
  }

  public void setMililts(Float mililts) {
    this.mililts = mililts;
  }
  
}
