package lt.code.academy.springhomeworkv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.entities.AccountEntity;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String surname;

    public static Account convert (AccountEntity entity){
        return new Account(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getName(),
                entity.getSurname()
        );
    }
}
