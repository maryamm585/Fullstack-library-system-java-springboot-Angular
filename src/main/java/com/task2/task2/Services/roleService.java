package com.task2.task2.Services;

import com.task2.task2.Entities.Book;
import com.task2.task2.Entities.Role;
import com.task2.task2.Entities.User;
import com.task2.task2.Repos.roleRepo;
import com.task2.task2.Repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class roleService {

    @Autowired
    roleRepo rolerepo;
    @Autowired
    private userRepo userrepo;


    public ResponseEntity<String> saveRole(Role role) {
        try {
            Role savedRole = rolerepo.save(role);
            return ResponseEntity.ok("Role added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Role> getallroles() {
        return rolerepo.findAll();
    }

    public ResponseEntity<String> assignRoleToUser(long userid, String roleName) {
        Optional<User> userop = userrepo.findById(userid);
        if (userop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid user iD: " + userid);
        }
        User user = userop.get();
        userrepo.save(user);

        return ok("done successfully");
    }


//    public Set<Role> getRolesByUsername(String username) {
//        User user = userrepo.findByUsername(username);
//        if (user != null) {
//            return user.getRoles();
//        }
//        else return null;
//    }
}
