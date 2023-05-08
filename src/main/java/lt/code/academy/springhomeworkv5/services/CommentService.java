package lt.code.academy.springhomeworkv5.services;

import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.entities.CommentEntity;
import lt.code.academy.springhomeworkv5.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final PostService postService;

    public CommentService(CommentRepository postRepository, AccountService accountService, PostService postService) {
        this.commentRepository = postRepository;
        this.accountService = accountService;
        this.postService = postService;
    }

    public void saveComment(Comment comment) {
        Post post = postService.findPostById(comment.getPostId());
        int commentCount = post.getTotalComments();
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());
            post.setTotalComments(commentCount + 1);
        }
        postService.savePost(post);
        commentRepository.save(CommentEntity.convert(comment, accountService, postService));
    }

    public Comment findCommentById(UUID id) {
        return commentRepository.findById(id)
                .map(Comment::convert)
                .orElse(null);
    }

    public List<Comment> findAllCommentsByPostId(UUID id) {

        return commentRepository.getAllByPostId(id)
                .stream()
                .map(Comment::convert)
                .toList();
    }

    public void deleteCommentsFromPost(List<Comment> comments) {
        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }
    }

    public void deleteCommentById(UUID id) {
        Post post = postService.findPostById(findCommentById(id).getPostId());
        int commentCount = post.getTotalComments();
        post.setTotalComments(commentCount - 1);
        postService.savePost(post);
        commentRepository.deleteById(id);
    }
}
