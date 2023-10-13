package br.com.macedolg.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tbUser")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    public UUID id;
    @Column(unique = true)
    public String username;
    public String name;
    public String password;

    @CreationTimestamp
    public LocalDateTime createdAt;

}
