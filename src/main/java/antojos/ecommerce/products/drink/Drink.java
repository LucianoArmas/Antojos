package antojos.ecommerce.products.drink;

import antojos.ecommerce.products.Product;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("drink")
public class Drink extends Product{

  @Column(name = "lts")
  private Float lts;


  public Float getLts() {
    return lts;
  }

  public void setLts(Float lts) {
    this.lts = lts;
  }
  
}
