package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class CommentController {
    private final AccountService accountService;
    private final CommentService commentService;

    public CommentController(AccountService accountService, CommentService commentService) {
        this.accountService = accountService;
        this.commentService = commentService;
    }

    @PostMapping("/newcomment")
    public String saveNewComment(Comment comment) {
        if (!comment.getBody().equals("")) {
            Account account = accountService.findOneByUsername("user");
            comment.setAccountId(account.getId());
            commentService.saveComment(comment);
        }

        return "redirect:/post/" + comment.getPostId();
    }

    @PostMapping("/deletecomment")
    public String deleteComment(Comment comment) {
        UUID postId = comment.getPostId();
        commentService.deleteCommentById(comment.getId());

        return "redirect:/post/" + postId;
    }

    @PostMapping("/editcomment")
    public String editComment(Comment comment) {
        UUID postId = comment.getPostId();
        if (comment.getBody() == null || comment.getBody().isBlank()) {
            commentService.deleteCommentById(comment.getId());
            return "redirect:/post/" + postId;
        }
        comment.setUpdatedAt(LocalDateTime.now());

        commentService.saveComment(comment);

        return "redirect:/post/" + comment.getPostId();
    }
}
