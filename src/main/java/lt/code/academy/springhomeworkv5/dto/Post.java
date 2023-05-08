package lt.code.academy.springhomeworkv5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 5, max = 20)
    private String title;
    @NotBlank
    @Size(min = 5, max = 2000)
    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID accountId;
    private String username;
    private int totalComments;

    public static Post convert(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getAccount().getId(),
                entity.getAccount().getUsername(),
                entity.getTotalComments()
        );
    }
}
