package antojos.ecommerce.order;

import antojos.ecommerce.orderLine.OrderLine;
import antojos.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
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
  private String status;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderLine> orderLineList;


  public Order(Long cod, User user, Date date, float totPrice, String status, List<OrderLine> orderLineList) {
    this.cod = cod;
    this.user = user;
    this.date = date;
    this.totPrice = totPrice;
    this.status = status;
    this.orderLineList = orderLineList;
  }
  public Order(User user, Date date, Float totPrice, String status) {
    this.user = user;
    this.date = date;
    this.totPrice = totPrice;
    this.status = status;
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
