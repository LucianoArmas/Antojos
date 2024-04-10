package antojos.ecommerce.auth;

import antojos.ecommerce.jwt.JwtService;
import antojos.ecommerce.user.Role;
import antojos.ecommerce.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Verifier {
  UserDetailsService userDetailsService;
  JwtService jwtService;
  public boolean verifyToken(String token, String userDni){
    UserDetails userDetails = userDetailsService.loadUserByUsername(userDni);
    return jwtService.isTokenValid(token,userDetails);
  }

  public Role verifyRole(HttpSession session){
    User userInSession = (User) session.getAttribute("user");
    Role role = null;

    if (userInSession.getRole().equals(Role.ADMIN)){
      role = Role.ADMIN;
    }

    if (userInSession.getRole().equals(Role.USER)){
      role = Role.USER;
    }

    return role;

  }
}
