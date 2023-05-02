package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/posts/{id}")
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
            System.out.println("STOP");
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
    public String saveNewPost(Model model, Post post) {
        Post savedPost = postService.savePost(post);
        model.addAttribute("post", savedPost);

        return "redirect:/posts/" + savedPost.getId();
    }

    @PostMapping("/deletepost")
    public String deletePost(Post post) {
        List<Comment> postComments = commentService.findAllCommentsByPostId(post.getId());
        commentService.deleteCommentsFromPost(postComments);
        postService.deletePost(post);
        return "redirect:/home";
    }

    @GetMapping("/editpost")
    public String editPost(Model model, Post post) {
        Account account = accountService.findOneByUsername("user");
        List<Comment> postComments = commentService.findAllCommentsByPostId(post.getId());
        if (account != null && post != null) {
            model.addAttribute("post", post);
            model.addAttribute("comments", postComments);

            return "post_edit";
        }
        return "404";
    }

    @PostMapping("/editpost")
    public String saveEditedPost(Model model, Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        postService.savePost(post);
        List<Comment> postComments = commentService.findAllCommentsByPostId(post.getId());
        model.addAttribute("post", post);
        model.addAttribute("comments", postComments);
        return "redirect:/posts/" + post.getId();
    }

}
