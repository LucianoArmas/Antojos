package antojos.ecommerce.config;

import antojos.ecommerce.jwt.JwtAuthenticationFilter;
import antojos.ecommerce.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
            .csrf(crsf -> crsf.disable())
            .authorizeHttpRequests(authReq -> authReq

//                            .requestMatchers("/users/edit/**").hasAnyRole("ADMIN","USER")
//
//                            .requestMatchers(
//                                    "/users/searchAdmin/**","/users/list", "/users/editUser",
//                                    "/products/searchAdmin/**", "/products/newProd", "/products/prodsList","/products/resetErrorNewProd",
//                                    "/orders/acceptOrder/**","/orders/orderListAdmin","/orders/modStatusAdmin",
//                                    "/foods/edit","/foods/delete",
//                                    "/drinks/edit","/drinks/delete").hasRole("ADMIN")
//
//                            .requestMatchers("http://www.thymeleaf.org", "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/**","/css/**","/js/**","/imgs/**",
//                                    "/products","/products/details/{id}","/products/search","/products/filterFood","/products/filterDrink","/","/login","/register", "/auth/**", "/details/**"
//                                    ).permitAll()

                            .requestMatchers("/**").permitAll()

                            .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();

  }


}
