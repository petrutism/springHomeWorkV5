package lt.code.academy.springhomeworkv5.services;

import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.entities.PostEntity;
import lt.code.academy.springhomeworkv5.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AccountService accountService;

    public PostService(PostRepository postRepository, AccountService accountService) {
        this.postRepository = postRepository;
        this.accountService = accountService;
    }

    public Post savePost(Post post) {
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        return Post.convert(postRepository.save(PostEntity.convert(post, accountService)));
    }

    public Page<Post> getAllPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(Post::convert);
    }

    public Post findPostById(UUID id) {
        return postRepository.findById(id)
                .map(Post::convert)
                .orElse(null);
    }

    public void deletePostById(UUID id) {
        postRepository.deleteById(id);
    }
}
