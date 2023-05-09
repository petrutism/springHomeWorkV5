package lt.code.academy.springhomeworkv5.repositories;

import lt.code.academy.springhomeworkv5.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(String name);
}
