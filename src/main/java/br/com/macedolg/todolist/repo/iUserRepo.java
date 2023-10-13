package br.com.macedolg.todolist.repo;

import br.com.macedolg.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface iUserRepo extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
