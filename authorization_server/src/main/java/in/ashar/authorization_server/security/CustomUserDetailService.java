package in.ashar.authorization_server.security;

import in.ashar.authorization_server.entity.UserCredential;
import in.ashar.authorization_server.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCredential credential = userCredentialRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Set<SimpleGrantedAuthority> authorities = credential.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.name())).collect(Collectors.toSet());

        return new User(credential.getUsername(), credential.getPassword(), authorities);

    }
}
