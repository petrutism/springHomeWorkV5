package lt.code.academy.springhomeworkv5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.dto.Account;

import java.util.UUID;

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
    private String username;
    private String password;
    private String name;
    private String surname;

    public static AccountEntity convert(Account account) {
        return new AccountEntity(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getName(),
                account.getSurname()
        );
    }
}