package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/public/home")
    public String home(Model model, @SortDefault(caseSensitive = false, sort = {"createdAt"}) Pageable pageable, Authentication authentication) {
        boolean isAdmin = false;
        if(authentication != null) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        }
        Page<Post> posts = postService.getAllPostsByPage(pageable);
        model.addAttribute("pageOfPosts", posts);
        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }
}
