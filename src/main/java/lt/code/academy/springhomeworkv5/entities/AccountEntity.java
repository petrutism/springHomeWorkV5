package lt.code.academy.springhomeworkv5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.dto.Account;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public static AccountEntity convert(Account account) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Set<RoleEntity> roles = account.getRoles()
                .stream()
                .map(RoleEntity::convert)
                .collect(Collectors.toSet());
        return new AccountEntity(
                account.getId(),
                account.getUsername(),
                encoder.encode(account.getPassword()),
                account.getName(),
                account.getSurname(),
                roles
        );
    }
}