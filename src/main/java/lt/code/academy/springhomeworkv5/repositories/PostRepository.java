package lt.code.academy.springhomeworkv5.repositories;

import lt.code.academy.springhomeworkv5.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
}
