package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
    @Mock
    private PostService postService;
    @Mock
    View view;
    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = standaloneSetup(homeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver( ))
                .setSingleView(view)
                .build();
    }

    @Test
    void testHomePageWhenPostListIsEmpty() throws Exception{
        when(postService.getAllPostsByPage(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/public/home")
                .param("sort", "createdAt"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("pageOfPosts", Page.empty()));
    }

    @Test
    void testHomePageWithSomePosts() throws Exception{
        Post post = new Post(UUID.randomUUID(), "testTitle", "testBody", LocalDateTime.now(), null, UUID.fromString("0491f52f-32b4-4b4e-95cc-35bc2d9bb7e0"), "user", 0);
        Page<Post> postPage = new PageImpl<>(List.of(post));
        when(postService.getAllPostsByPage(any(Pageable.class))).thenReturn(postPage);

        MvcResult mvcResult = mockMvc.perform(get("/public/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        Object pageOfPosts = model.get("pageOfPosts");

        if(pageOfPosts instanceof Page page){
            assertNotNull(page);
            assertEquals(page, postPage);
        }
    }
}