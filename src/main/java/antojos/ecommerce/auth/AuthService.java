package antojos.ecommerce.auth;

import antojos.ecommerce.jwt.JwtService;
import antojos.ecommerce.user.Role;
import antojos.ecommerce.user.User;
import antojos.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  public AuthResponse login(LoginRequest request){

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getDni(), request.getPassword()));

    UserDetails userDetails = userRepository.findById(request.getDni()).orElseThrow();
    String token = jwtService.getToken(userDetails);

    return AuthResponse.builder()
            .token(token)
            .build();
  }


  public AuthResponse register(RegisterRequest request){
    User user = User.builder()
            .dni(request.dni)
            .email(request.email)
            .name(request.name)
            .lastName(request.lastname)
            .userPass(passwordEncoder.encode(request.password))
            .role(Role.USER)
            .accessLvl("client")
            .build();

    userRepository.save(user);

    return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
  }

}
