package antojos.ecommerce.user;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import antojos.ecommerce.direction.Dir;
import antojos.ecommerce.shopping.Shopping;

@Entity
public class User {

  @Id
  private String dni;
  private String name;
  private String lastName;
  private String userPass;
  private String email;
  private String accessLvl;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Dir> directions;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Shopping> shoppings; 

  public User(){}

  public User(String dni, String name, String lastName, String userPass, String email, String accessLvl, List<Dir> directions, List<Shopping> shoppings){
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.userPass = userPass;
    this.email = email;
    this.accessLvl = accessLvl;
    this.directions = directions;
    this.shoppings = shoppings;
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

  public List<Shopping> getShoppings() {
    return shoppings;
  }

  public void setShoppings(List<Shopping> shoppings) {
    this.shoppings = shoppings;
  }
  

}
