package antojos.ecommerce.orderLine;

import antojos.ecommerce.order.Order;
import antojos.ecommerce.products.Product;
import jakarta.persistence.*;

@Entity

public class OrderLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long number;
  @Column(nullable = true)
  private int quantityProds;
  @Column(nullable = true)
  private Float subTotPrice;

  @ManyToOne
  @JoinColumn(name = "prodId")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "orderCod")
  private Order order;

  public OrderLine(Long nro, int quantityProds, Float subTotPrice, Product product, Order order) {
    this.number = nro;
    this.quantityProds = quantityProds;
    this.subTotPrice = subTotPrice;
    this.product = product;
    this.order = order;
  }
  public OrderLine(Product product, Order order) {
    this.product = product;
    this.order = order;
  }

  public OrderLine() {

  }

  public Long getNro() {
    return number;
  }

  public void setNro(Long nro) {
    this.number = nro;
  }

  public int getQuantityProds() {
    return quantityProds;
  }

  public void setQuantityProds(int quantityProds) {
    this.quantityProds = quantityProds;
  }

  public Float getSubTotPrice() {
    return subTotPrice;
  }

  public void setSubTotPrice(Float subTotPrice) {
    this.subTotPrice = subTotPrice;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
