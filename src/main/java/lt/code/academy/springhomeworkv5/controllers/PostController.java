package lt.code.academy.springhomeworkv5.controllers;

import jakarta.validation.Valid;
import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/public/post/{id}")
    public String getPost(@PathVariable UUID id, Model model, Authentication authentication) {
        Post post = postService.findPostById(id);
        if (post != null) {
            boolean isAdmin;
            Comment newComment = new Comment();
            if (authentication != null) {
                isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
                Account principal = (Account) authentication.getPrincipal();
                Account account = accountService.findAccountById(principal.getId());
                newComment.setAccountId(account.getId());
                newComment.setPostId(post.getId());
                model.addAttribute("isAdmin", isAdmin);
            }
            List<Comment> postComments = commentService.findAllCommentsByPostId(post.getId());
            model.addAttribute("post", post);
            model.addAttribute("comments", postComments);
            model.addAttribute("comment", newComment);
            return "post";
        } else {
            return "404";
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/newpost")
    public String createNewPost(Model model, Authentication authentication) {
        Account principal = (Account) authentication.getPrincipal();
        Account account = accountService.findAccountById(principal.getId());
        if (account != null) {
            Post post = new Post();
            post.setAccountId(account.getId());
            post.setUsername(account.getUsername());
            model.addAttribute("post", post);

            return "post_new";
        }
        return "404";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/newpost")
    public String saveNewPost(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post_new";
        }

        Post savedPost = postService.savePost(post);

        return "redirect:/public/post/" + savedPost.getId();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/post/{id}/delete")
    public String deletePostById(@PathVariable UUID id, Authentication authentication) {
        Post post = postService.findPostById(id);

        if (post != null) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                commentService.deleteCommentsFromPost(commentService.findAllCommentsByPostId(id));
                postService.deletePostById(id);

                return "redirect:/public/home";
            } else {
                Account principal = (Account) authentication.getPrincipal();
                Account account = accountService.findAccountById(principal.getId());
                if (account != null && post.getAccountId().equals(account.getId())) {
                    commentService.deleteCommentsFromPost(commentService.findAllCommentsByPostId(id));
                    postService.deletePostById(id);
                    return "redirect:/public/home";
                } else {
                    return "pig_error";
                }
            }
        }
        return "404";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/post/{id}/update")
    public String openEditPostForm(@PathVariable UUID id, Model model, Authentication authentication) {
        Post post = postService.findPostById(id);
        if (post != null) {
            Account principal = (Account) authentication.getPrincipal();
            Account account = accountService.findAccountById(principal.getId());

            if (account != null && post.getAccountId().equals(account.getId())) {
                List<Comment> postComments = commentService.findAllCommentsByPostId(id);
                model.addAttribute("post", post);
                model.addAttribute("comments", postComments);

                return "post_edit";
            } else {
                return "pig_error";
            }
        }
        return "404";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/post/{id}/save")
    public String saveEditedPost(@PathVariable UUID id, Post post, Authentication authentication) {
        Account principal = (Account) authentication.getPrincipal();
        Account account = accountService.findAccountById(principal.getId());
        if (account != null && post.getAccountId().equals(account.getId())) {
            if (post.getTitle() == null || post.getTitle().isBlank() || post.getBody() == null || post.getBody().isBlank()) {
                commentService.deleteCommentsFromPost(commentService.findAllCommentsByPostId(id));
                postService.deletePostById(id);
                return "redirect:/public/home";
            }
            post.setUpdatedAt(LocalDateTime.now());
            postService.savePost(post);
            return "redirect:/public/post/" + id;
        }
        return "pig_error";
    }
}
