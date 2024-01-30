package antojos.ecommerce.order;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.user.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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

  @Column(nullable = true)
  private Date date;
  @Column(nullable = true)
  private float totPrice;
  @Column(nullable = true)
  private String state;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderLine> orderLineList;


  
  public Order(){}

  public Order(Long cod, User user, Date date, float totPrice, String state, List<OrderLine> orderLineList) {
    this.cod = cod;
    this.user = user;
    this.date = date;
    this.totPrice = totPrice;
    this.state = state;
    this.orderLineList = orderLineList;
  }
  public Order(User user, Date date, Float totPrice, String state) {
    this.user = user;
    this.date = date;
    this.totPrice = totPrice;
    this.state = state;
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
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public float getTotPrice() {
    return totPrice;
  }
  public void setTotPrice(float totPrice) {
    this.totPrice = totPrice;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public List<OrderLine> getOrderLineList() {
    return orderLineList;
  }
  public void setOrderLineList(List<OrderLine> orderLineList) {
    this.orderLineList = orderLineList;
  }

  @Override
  public String toString() {
    return "Order [cod=" + cod + ", user=" + user + ", date=" + date + ", totPrice=" + totPrice
        + ", state=" + state +"]";
  }


  public float calcuTotal(){
    float total = 0;
    List<OrderLine> orderLines = orderLineList;
    for (OrderLine orderLine : orderLines) {
      total = total + orderLine.getSubTotPrice();
    }
    setTotPrice(total);
//    for (OrderLine ol: orderLineList) {
//      total = total + ol.getSubTotPrice();
//    }
//    setTotPrice(total);
    return total;
  }

  
}
