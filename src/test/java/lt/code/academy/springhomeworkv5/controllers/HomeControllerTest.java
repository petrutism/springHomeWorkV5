package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

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
}