package com.task2.task2.Controllers;

import com.task2.task2.Entities.Book;
import com.task2.task2.Entities.BookReview;
import com.task2.task2.Entities.User;
import com.task2.task2.Repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class userController {
    @Autowired
    private com.task2.task2.Services.userService userService;

    @Autowired
    private userRepo userrepo;

    //@PostMapping
    //public User createUser(@RequestBody User user, @RequestParam String rolename) {
    //    return userService.createuser(user,rolename);
    //}
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String username) {
        List<User> users = userService.searchByUsername(username);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userrepo.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        userrepo.delete(user);
        return ResponseEntity.ok("User with id " + id + " has been deleted.");
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getallusers();
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PostMapping("/{userid}/assignrole")
    public ResponseEntity<String> assignRoleToUser(@PathVariable long userid, @RequestParam String roleName) {
        Optional<User> optionalUser = userrepo.findById(userid);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        user.setRoles(roleName);
        userrepo.save(user);
        return ResponseEntity.ok("User with id " + userid + " role set to " + roleName);
    }
}
