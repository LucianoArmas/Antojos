package antojos.ecommerce.products;

import java.util.List;

import antojos.ecommerce.order.Order;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prodId",unique = true)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Float price;

  @Column(nullable = false)
  private int stock;

  @OneToMany(mappedBy = "prod", cascade = CascadeType.ALL)
  private List<Order> orders;

  public Product(){}

  public Product(Long id, String name, String desc, Float price, int stock, List<Order> orders) {
    this.id = id;
    this.name = name;
    this.description = desc;
    this.price = price;
    this.stock = stock;
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "Products["+
    "id=" + id + 
    ", name=" + name + 
    ", description=" + description +
    ", price=" + price + 
    "]";
  }

  @Transient
  public String getType() {
    DiscriminatorValue discriminatorValue = this.getClass().getAnnotation(DiscriminatorValue.class);
    return (discriminatorValue != null) ? discriminatorValue.value() : null;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }


  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  

}