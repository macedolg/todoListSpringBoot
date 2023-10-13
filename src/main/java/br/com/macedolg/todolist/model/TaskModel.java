package br.com.macedolg.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * ID
 * Usuário (ID_USUARIO)
 * Descrição
 * Título
 * Data de Início
 * Data de término
 * Prioridade
 *
 */

@Data
@Entity(name ="tbTask")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    public UUID id;
    public String description;

    @Column(length = 50)
    public String tittle;
    public LocalDateTime startAt;
    public LocalDateTime endAt;
    public String priority;

    public UUID idUser;

    @CreationTimestamp
    public LocalDateTime createdAt;

    public void setTittle(String tittle) throws Exception {
        if (tittle.length() > 50) {
            throw new Exception("O campo deve receber ao máximo 50 caracteres");
        }

        this.tittle = tittle;
    }

}
