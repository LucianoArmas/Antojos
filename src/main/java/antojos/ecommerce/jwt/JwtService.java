package antojos.ecommerce.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private static final String SECRET_KEY = "8F38F133E5B87EA43AA97A3D7280070287954F7E86FD0A9512C9B6E0E0D3FA33";

  public String getToken(UserDetails user){
    return getToken(new HashMap<>(), user);
  }

  private String getToken(Map<String,Object> extraClaims, UserDetails user) {
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims getAllClaims(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Date getExpiration(String token){
    return getClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token){
    return getExpiration(token).before(new Date());
  }

  public String getUserDniFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userDni = getUserDniFromToken(token);
    return (userDni.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
