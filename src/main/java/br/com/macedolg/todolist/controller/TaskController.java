package br.com.macedolg.todolist.controller;

import br.com.macedolg.todolist.model.TaskModel;
import br.com.macedolg.todolist.repo.iTaskRepo;
import br.com.macedolg.todolist.utils.Utils;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/tasks")
public class TaskController {

    @Autowired
    public iTaskRepo taskRepo;

    @PostMapping("/")
    public ResponseEntity createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");

        System.out.println("Chegou no Controller o ID do Usuário: " + idUser);

        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início / data do término deve ser maior a data atual");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }
        var taskSave = this.taskRepo.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskSave);
    }

    @GetMapping("/")
    public List<TaskModel> getTask(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var taskFind = this.taskRepo.findByIdUser((UUID) idUser);
        return taskFind;
    }

    @PutMapping("/{id}")
    public TaskModel updateTask(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var taskSave = this.taskRepo.save(taskModel);

        var taskFind = this.taskRepo.findById(id).orElseThrow(null);

        Utils.copyNonNullProperties(taskModel, taskFind);

        return taskSave;
    }
}
