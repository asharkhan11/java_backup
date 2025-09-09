package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userDetailsRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole())));
//        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRole())));

        return new User(user.getUsername(), user.getPassword(), authorities);

    }
}
