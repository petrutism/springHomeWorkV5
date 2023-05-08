package lt.code.academy.springhomeworkv5.controllers;

import jakarta.validation.Valid;
import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    private final PostService postService;
    private final AccountService accountService;
    private final CommentService commentService;

    public PostController(PostService postService, AccountService accountService, CommentService commentService) {
        this.postService = postService;
        this.accountService = accountService;
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable UUID id, Model model) {
        Post post = postService.findPostById(id);
        Comment newComment = new Comment();
        if (post != null) {
            List<Comment> postComments = commentService.findAllCommentsByPostId(post.getId());
            Account account = accountService.findOneByUsername("admin");
            newComment.setAccountId(account.getId());
            newComment.setPostId(post.getId());
            model.addAttribute("post", post);
            model.addAttribute("comments", postComments);
            model.addAttribute("comment", newComment);

            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/newpost")
    public String createNewPost(Model model) {
        Account account = accountService.findOneByUsername("user");
        if (account != null) {
            Post post = new Post();
            post.setAccountId(account.getId());
            post.setUsername(account.getUsername());
            model.addAttribute("post", post);

            return "post_new";
        }
        return "404";
    }

    @PostMapping("/newpost")
    public String saveNewPost(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post_new";
        }
        Post savedPost = postService.savePost(post);

        return "redirect:/post/" + savedPost.getId();
    }

    @GetMapping("/post/{id}/delete")
    public String deletePostById(@PathVariable UUID id) {
        commentService.deleteCommentsFromPost(commentService.findAllCommentsByPostId(id));
        postService.deletePostById(id);

        return "redirect:/home";
    }

    @GetMapping("/post/{id}/update")
    public String openEditPostForm(@PathVariable UUID id, Model model) {
        Post post = postService.findPostById(id);
        Account account = accountService.findOneByUsername("user");
        List<Comment> postComments = commentService.findAllCommentsByPostId(id);
        if (account != null) {
            model.addAttribute("post", post);
            model.addAttribute("comments", postComments);

            return "post_edit";
        }
        return "404";
    }

    @PostMapping("/post/{id}/save")
    public String saveEditedPost(@PathVariable UUID id, Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        if (post.getTitle() == null || post.getTitle().isBlank() || post.getBody() == null || post.getBody().isBlank()) {
            commentService.deleteCommentsFromPost(commentService.findAllCommentsByPostId(id));
            postService.deletePostById(id);
            return "redirect:/home";
        }
        postService.savePost(post);

        return "redirect:/post/" + id;
    }
}
