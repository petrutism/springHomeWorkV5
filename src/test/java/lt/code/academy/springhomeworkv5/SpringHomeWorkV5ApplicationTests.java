package lt.code.academy.springhomeworkv5;

import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.services.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.UUID;

@SpringBootTest
class SpringHomeWorkV5ApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    void testCommentsSizeIsCorrect() {
        UUID id = UUID.fromString("6e161785-7ce2-4a2c-b8aa-f2e44365e3a8");
        List<Comment> comments = commentService.findAllCommentsByPostId(id);

        Assertions.assertEquals(2, comments.size());
    }

}
