package lt.code.academy.springhomeworkv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.springhomeworkv5.entities.PostEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private UUID id;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID accountId;
    private String username;

    public static Post convert(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getAccount().getId(),
                entity.getAccount().getUsername()
        );
    }
}
