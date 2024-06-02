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



}
