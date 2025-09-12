package in.ashar.feign_client_learn.utility;

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

@Component
@Slf4j
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    public Claims validateJwt(String token){
        try {

            Claims claims = extractAllClaims(token);
            String issuer = claims.getIssuer();
            String type = claims.get("type", String.class);


            if (issuer.equals(jwtProperties.getIssuer()) && !isTokenExpired(token) && "active".equals(type)) {
                return claims;
            }

        }catch (Exception e){
            log.error("Error while validation jwt : {}", e.getMessage());
        }
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractType(String token){
        return (String) extractClaim(token, claims -> claims.get("type"));
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


    public Claims extractAllClaims(String token){
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
