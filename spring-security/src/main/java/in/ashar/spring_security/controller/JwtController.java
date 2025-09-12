package in.ashar.spring_security.controller;

import in.ashar.spring_security.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/jwt")
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public UsernamePasswordAuthenticationToken validateToken(@RequestHeader("token") String token){

        try {

            String username = jwtUtil.extractUsername(token);
            System.out.println(username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

             if (jwtUtil.validateJwt(token, userDetails)){
                 System.out.println("validated...........");
                 return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

             }

        } catch (Exception e){
            System.out.println("error...........");
            log.error(e.getMessage());
        }

        System.out.println("validation failed...........");

        return null;
    }

}
