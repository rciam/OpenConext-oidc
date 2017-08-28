package oidc.saml;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class SAMLUser extends User {

    private static final long serialVersionUID = 5582621468007779146L;

    public SAMLUser(String sub, boolean isAdmin, boolean isSuperAdmin) {
        super(
                sub,
                "N/A",
                isAdmin ? (isSuperAdmin
                                ? Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"))
                                : Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_ADMIN")))
                        : Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}
