package antojos.ecommerce.products;

import java.util.List;

import antojos.ecommerce.shopping.Shopping;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String desc;
  private Float price;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<Shopping> shoppings;

  public Product(){}

  public Product(Long id, String name, String desc, Float price, List<Shopping> shoppings) {
    this.id = id;
    this.name = name;
    this.desc = desc;
    this.price = price;
    this.shoppings = shoppings;
  }

  @Override
  public String toString() {
    return "Products["+
    "id=" + id + 
    ", name=" + name + 
    ", desc=" + desc + 
    ", price=" + price + 
    "]";
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public List<Shopping> getShoppings() {
    return shoppings;
  }

  public void setShoppings(List<Shopping> shoppings) {
    this.shoppings = shoppings;
  }

  

}
