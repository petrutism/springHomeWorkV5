package lt.code.academy.springhomeworkv5.config;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateData implements CommandLineRunner {
    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;

    public GenerateData(AccountService accountService, PostService postService, CommentService commentService) {
        this.accountService = accountService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) {
        List<Account> accounts = accountService.getAll();
        if (accounts.size() == 0) {
            Account user = new Account();
            user.setName("Jonas");
            user.setSurname("Jonaitis");
            user.setUsername("user");
            user.setPassword("1122");
            Account savedUser = accountService.saveAccount(user);

            Account admin = new Account();
            admin.setName("Petras");
            admin.setSurname("Petraitis");
            admin.setUsername("admin");
            admin.setPassword("1122");
            Account savedAdmin = accountService.saveAccount(admin);

            Post post1 = new Post();
            post1.setTitle("Pirmo posto pavadinimas");
            post1.setBody("""
                    Mergina turguje perka papūgiuką, pasisodina jį ant peties ir sako:
                    - Na, kvailiuk, įdomu ar moki kalbėt?
                    - Kalbėt tai aš moku. O tu, dūra, skraidyt moki?""");

            post1.setAccountId(savedUser.getId());
            Post savedPost1 = postService.savePost(post1);

            Post post2 = new Post();
            post2.setTitle("Antro posto pavadinimas");
            post2.setBody("""
                    Nueina senutė pas ginekologą. Šis ją apžiūri ir stebėdamasis sako:
                    - Senute, jūs nėščia! Kaip jūsų amžiaus moteriškė...?
                    - Vis tie paaugliai. Viską jiems papasakok, viską jiems parodyk, duok pabandyt...""");
            post2.setAccountId(savedAdmin.getId());
            Post savedPost2 = postService.savePost(post2);

            Comment comment1 = new Comment();
            comment1.setBody("Pirmas userio komentaras pirmam postui");
            comment1.setAccountId(savedUser.getId());
            comment1.setPostId(savedPost1.getId());
            commentService.saveComment(comment1);

            Comment comment2 = new Comment();
            comment2.setBody("Pirmas userio komentaras antram postui");
            comment2.setAccountId(savedUser.getId());
            comment2.setPostId(savedPost2.getId());
            commentService.saveComment(comment2);

            Comment comment3 = new Comment();
            comment3.setBody("Pirmas admino komentaras pirmam postui");
            comment3.setAccountId(savedAdmin.getId());
            comment3.setPostId(savedPost1.getId());
            commentService.saveComment(comment3);

            Comment comment4 = new Comment();
            comment4.setBody("Pirmas admino komentaras antram postui");
            comment4.setAccountId(savedAdmin.getId());
            comment4.setPostId(savedPost2.getId());
            commentService.saveComment(comment4);
            System.out.println("STOP");
        }
    }
}
