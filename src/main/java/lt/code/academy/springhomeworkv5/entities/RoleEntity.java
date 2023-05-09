package lt.code.academy.springhomeworkv5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.dto.Role;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
        public static RoleEntity convert(Role role){
        return new RoleEntity(
                role.getId(),
                role.getName()
        );
    }
}
