package antojos.ecommerce.products.food;

import java.util.List;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.products.Product;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("food")
public class Food extends Product{
  @Column(name = "amount_people_eat")
  private int amountPeopleEat;

  public Food(){}

  public Food(Long id, String name, String desc, Float price, int stock, List<OrderLine> orderLineList, int amountPeopleEat) {
    super(id, name, desc, price, stock, orderLineList);
    this.amountPeopleEat = amountPeopleEat;
  }

  public int getAmountPeopleEat() {
    return amountPeopleEat;
  }

  public void setAmountPeopleEat(int amountPeopleEat) {
    this.amountPeopleEat = amountPeopleEat;
  }

  
}
