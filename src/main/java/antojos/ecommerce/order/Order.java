package antojos.ecommerce.order;

import antojos.ecommerce.products.Product;
import antojos.ecommerce.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orderEntity")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long cod;
  
  @ManyToOne
  @JoinColumn(name = "userDni")
  private User user;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product prod;

  @Column(nullable = false)
  private Date date;
  @Column(nullable = false)
  private double totPrice;


  
  public Order(){}

  public Order(Long cod, User user, Product prod, Date date, double totPrice) {
    this.cod = cod;
    this.user = user;
    this.prod = prod;
    this.date = date;
    this.totPrice = totPrice;
  }

  public Long getCod() {
    return cod;
  }
  public void setCod(Long cod) {
    this.cod = cod;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public Product getProd() {
    return prod;
  }
  public void setProd(Product prod) {
    this.prod = prod;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public double getTotPrice() {
    return totPrice;
  }
  public void setTotPrice(double totPrice) {
    this.totPrice = totPrice;
  }

  @Override
  public String toString() {
    return "Order [cod=" + cod + ", user=" + user + ", prod=" + prod + ", date=" + date + ", totPrice=" + totPrice
        + "]";
  }

  
}
