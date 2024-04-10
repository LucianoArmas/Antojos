package antojos.ecommerce.user;

import java.util.Collection;
import java.util.List;

import antojos.ecommerce.order.Order;
import jakarta.persistence.*;
import antojos.ecommerce.direction.Dir;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

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

  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return userPass;
  }

  @Override
  public String getUsername() {
    return dni;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


//  public User(){}
//
//  public User(String dni, String name, String lastName, String userPass, String email, String accessLvl, List<Dir> directions, List<Order> orders){
//    this.dni = dni;
//    this.name = name;
//    this.lastName = lastName;
//    this.userPass = userPass;
//    this.email = email;
//    this.accessLvl = accessLvl;
//    this.directions = directions;
//    this.orders = orders;
//  }
//
//  @Override
//  public String toString(){
//    return "User["+
//    "dni"+ dni +
//    "name"+ name +
//    "lastName"+ lastName +
//    "userPass"+ userPass +
//    "email"+ email +
//    "]";
//  }
//
//
//  public String getDni() {
//    return dni;
//  }
//
//  public void setDni(String dni) {
//    this.dni = dni;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public String getLastName() {
//    return lastName;
//  }
//
//  public void setLastName(String lastName) {
//    this.lastName = lastName;
//  }
//
//  public String getUserPass() {
//    return userPass;
//  }
//
//  public void setUserPass(String userPass) {
//    this.userPass = userPass;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getAccessLvl() {
//    return accessLvl;
//  }
//
//  public void setAccessLvl(String accessLvl) {
//    this.accessLvl = accessLvl;
//  }
//
//  public List<Dir> getDirections() {
//    return directions;
//  }
//
//  public void setDirections(List<Dir> directions) {
//    this.directions = directions;
//  }
//
//  public List<Order> getOrders() {
//    return orders;
//  }
//
//  public void setOrders(List<Order> orders) {
//    this.orders = orders;
//  }
}
