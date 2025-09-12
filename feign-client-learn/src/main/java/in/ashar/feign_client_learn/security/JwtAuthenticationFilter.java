package in.ashar.feign_client_learn.security;


import aj.org.objectweb.asm.TypeReference;
import in.ashar.feign_client_learn.feignClient.SecurityClient;
import in.ashar.feign_client_learn.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityClient securityClient;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {

                Claims claims = jwtUtil.validateJwt(token);

                if (claims != null){
                    String username = claims.getSubject();
                    ArrayList<String> role = claims.get("role", ArrayList.class);

                    System.out.println("validated");
                    System.out.println(role);
                    System.out.println(username);

                    List<SimpleGrantedAuthority> authorities = role.stream().map(SimpleGrantedAuthority::new).toList();

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
                else {
                    System.out.println("else........");
                }



            } catch (Exception e) {
                logger.error("Error in JWT authentication filter....");
                logger.error(e);
                throw new RuntimeException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }


}
