package in.ashar.spring_security.utility;

import in.ashar.spring_security.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateJwt(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role",userDetails.getAuthorities())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiry()))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateJwt(String token, UserDetails userDetails){
        try {

            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            String issuer = claims.getIssuer();


            if (username.equals(userDetails.getUsername()) && issuer.equals(jwtProperties.getIssuer()) && !isTokenExpired(token)) {
                return true;
            }
        }catch (Exception e){
            log.error("Error while validation jwt : {}", e.getMessage());
        }
        return false;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }


}
