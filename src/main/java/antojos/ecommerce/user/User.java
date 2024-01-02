package antojos.ecommerce.user;

import java.util.List;

import antojos.ecommerce.order.Order;
import jakarta.persistence.*;
import antojos.ecommerce.direction.Dir;

@Entity
public class User {

  @Id
  @Column(unique = true, nullable = false)
  private String dni;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String userPass;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String accessLvl;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Dir> directions;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders;

  public User(){}

  public User(String dni, String name, String lastName, String userPass, String email, String accessLvl, List<Dir> directions, List<Order> orders){
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.userPass = userPass;
    this.email = email;
    this.accessLvl = accessLvl;
    this.directions = directions;
    this.orders = orders;
  }

  @Override
  public String toString(){
    return "User["+
    "dni"+ dni +
    "name"+ name +
    "lastName"+ lastName +
    "userPass"+ userPass +
    "email"+ email +
    "]";
  }

  

  public List<Dir> getDirections() {
    return directions;
  }

  public void setDirections(List<Dir> directions) {
    this.directions = directions;
  }

  public String getDni() {
    return dni;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUserPass() {
    return userPass;
  }

  public String getEmail() {
    return email;
  }

  public String getAccessLvl() {
    return accessLvl;
  }

  public List<Order> getShoppings() {
    return orders;
  }

  public void setShoppings(List<Order> orders) {
    this.orders = orders;
  }
  

}
