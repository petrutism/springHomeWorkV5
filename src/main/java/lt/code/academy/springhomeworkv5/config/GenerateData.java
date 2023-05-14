package lt.code.academy.springhomeworkv5.config;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.dto.Comment;
import lt.code.academy.springhomeworkv5.dto.Post;
import lt.code.academy.springhomeworkv5.dto.Role;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.CommentService;
import lt.code.academy.springhomeworkv5.services.PostService;
import lt.code.academy.springhomeworkv5.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GenerateData implements CommandLineRunner {
    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;
    private final RoleService roleService;

    public GenerateData(AccountService accountService, PostService postService, CommentService commentService, RoleService roleService) {
        this.accountService = accountService;
        this.postService = postService;
        this.commentService = commentService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        List<Account> accounts = accountService.getAll();

        if (accounts.size() == 0) {
            Role userRole = new Role();
            userRole.setName("USER");
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role savedUserRole = roleService.saveRole(userRole);
            Role savedAdminRole = roleService.saveRole(adminRole);

            Account user = new Account();
            user.setName("Useris");
            user.setSurname("Uservavičius");
            user.setUsername("user");
            user.setPassword("1122");
            user.setRoles(Set.of(savedUserRole));
            Account savedUser = accountService.saveAccount(user);

            Account admin = new Account();
            admin.setName("Adminas");
            admin.setSurname("Administratorius");
            admin.setUsername("admin");
            admin.setPassword("1122");
            admin.setRoles(Set.of(savedAdminRole, savedUserRole));
            Account savedAdmin = accountService.saveAccount(admin);

            Post post1 = new Post();
            post1.setTitle("1. Pirmo userio posto pavadinimas");
            post1.setBody("""
                    Kašalotas (Physeter catodon arba Physeter macrocephalus) - tai
                    žinduolis, visą laiką gyvenantis vandenyje, vienintelis
                    banginių (Cetacea) būriui piklausančios kašalotinių
                    (Physeteridae) šeimos atstovas. Kašalotas yra ne tik vienas
                    iš didžiausių pasaulyje plėšrųjų žinduolių, bet ir
                    triukšmingiausias gyvūnas pasaulyje, jo spragsėjimas, gali
                    pasiekti net 235 decibelų, tokio garso vibracijos gali pražudyti
                    žmogų. Palyginimui, garsus roko koncertas skleidžia tik maždaug
                    115 decibelų garsą, o lėktuvo variklis – 140 decibelų garsą, o
                    žmogaus kalbėjimo metu yra skleidžiamas 60-65 decibelų stiprumo garsas.""");

            post1.setAccountId(savedUser.getId());
            post1.setUsername(savedUser.getUsername());
            Post savedPost1 = postService.savePost(post1);

            Post post2 = new Post();
            post2.setTitle("2. Pirmo admino posto pavadinimas");
            post2.setBody("""
                    Kašalotai yra apie 11-20 m ilgio ir iki 20-57 tonų svorio,
                    ką tik gimęs jauniklis sveria apie toną ir būna net 4-4,5 metrų ilgio.
                    Galva kašalotų labai didelė, jos priekinė dalis buka, beveik kvadratinė,
                    pilna skystų riebalų, kurie padeda išlaikyti plūdrumą,
                    t.y. gebėjimą plaukti vandens paviršiuje. Ieškodamas maisto
                    kašalotas paneria labai giliai, teigiama kad.. į didesnį kaip
                    3000 m gylį, kuriame gaudo galvakojus moliuskus,
                    kurie ir yra pagrindinis jų maistas.""");
            post2.setAccountId(savedAdmin.getId());
            post2.setUsername(savedAdmin.getUsername());
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
        }
    }
}
