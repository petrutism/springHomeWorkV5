package lt.code.academy.springhomeworkv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.entities.RoleEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    private static final String ROLE_PREFIX = "ROLE_";

    private UUID id;
    private String name;

    public static Role convert(RoleEntity entity) {
        return new Role(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + name;
    }
}
