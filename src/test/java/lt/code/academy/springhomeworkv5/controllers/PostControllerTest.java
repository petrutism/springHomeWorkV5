package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostService postService;
    @Mock
    private CommentService commentService;
    @Mock
    private AccountService accountService;
    @Mock
    View view;
    @InjectMocks
    private PostController postController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(postController)
                .setSingleView(view)
                .build();
    }

    @Test
    void testFindPostByIdWhenPostDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        when(postService.findPostById(eq(id))).thenReturn(null);

        mockMvc.perform(get("/public/post/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }

    @Test
    void testFindPostById() throws Exception{
        UUID id = UUID.randomUUID();
        Post post = new Post(UUID.randomUUID(), "testTitle", "testBody", LocalDateTime.now(), null, UUID.fromString("0491f52f-32b4-4b4e-95cc-35bc2d9bb7e0"), "user", 0);
        when(postService.findPostById(eq(id))).thenReturn(post);

        mockMvc.perform(get("/public/post/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }
}