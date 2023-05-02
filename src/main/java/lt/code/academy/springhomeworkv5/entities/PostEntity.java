package lt.code.academy.springhomeworkv5.entities;


import jakarta.persistence.*;
import lombok.*;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)

    private UUID id;
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    public static PostEntity convert(Post post, AccountService accountService) {
        AccountEntity account = AccountEntity.convert(accountService.findAccountById(post.getAccountId()));
        return new PostEntity(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                account
        );
    }
}
