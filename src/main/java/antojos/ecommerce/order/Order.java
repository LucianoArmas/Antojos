package antojos.ecommerce.shopping;

import antojos.ecommerce.products.Product;
import antojos.ecommerce.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Shopping {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;
  
  @ManyToOne
  @JoinColumn(name = "userDni")
  private User user;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product prod;

  private Date date;
  private double totPrice;


  
  public Shopping(){}

  public Shopping(Long cod, User user, Product prod, Date date, double totPrice) {
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
    return "Shopping [cod=" + cod + ", user=" + user + ", prod=" + prod + ", date=" + date + ", totPrice=" + totPrice
        + "]";
  }

  
}
