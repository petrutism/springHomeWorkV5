package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class HomeController {
    private PostService postService;
    public HomeController(PostService postService){
        this.postService = postService;
    }
    @GetMapping("/home")
    public String home(Model model){
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "home";
    }
}
