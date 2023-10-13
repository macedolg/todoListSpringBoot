package br.com.macedolg.todolist.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.macedolg.todolist.model.UserModel;
import br.com.macedolg.todolist.repo.iUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modifier
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private iUserRepo userRepo;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var userReturn = this.userRepo.findByUsername(userModel.getUsername());

        if(userReturn != null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }

        var passwordHash = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHash);

        var userCreated = this.userRepo.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
