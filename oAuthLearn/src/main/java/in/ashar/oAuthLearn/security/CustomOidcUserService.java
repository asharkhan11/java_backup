package in.ashar.oAuthLearn.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService userService = new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = userService.loadUser(userRequest);

        Set<SimpleGrantedAuthority> mappedAuthorities = new HashSet<>();
        // Everyone gets USER role
        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Example: specific email gets ADMIN role
        if ("asharkhan.eidiko@gmail.com".equalsIgnoreCase(oidcUser.getEmail())) {
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new DefaultOidcUser(
                mappedAuthorities,
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
        );
    }
}
