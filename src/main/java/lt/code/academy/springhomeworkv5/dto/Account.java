package lt.code.academy.springhomeworkv5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.entities.AccountEntity;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    private UUID id;
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    @NotBlank
    @Size(min = 5, max = 20)
    private String name;
    @NotBlank
    @Size(min = 5, max = 20)
    private String surname;

    private Set<Role> roles;

    public static Account convert (AccountEntity entity){
        Set<Role> roles = entity.getRoles()
                .stream()
                .map(Role::convert)
                .collect(Collectors.toSet());
        return new Account(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getName(),
                entity.getSurname(),
                roles
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
