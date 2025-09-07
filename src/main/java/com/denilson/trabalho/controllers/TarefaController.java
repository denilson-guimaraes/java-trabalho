package com.denilson.trabalho.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.denilson.trabalho.models.Tarefa;
import com.denilson.trabalho.repositories.TarefaRepository;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    @GetMapping("")
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarUnico(@PathVariable Long id) {
        return repository.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("")
    public Tarefa cadastro(@RequestBody Tarefa body) {
        body.setId(null);
        return repository.save(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Tarefa body) {
         return repository.findById(id)
            .map(record -> {
                record.setDataEntrega(body.getDataEntrega());
                record.setNome(body.getNome());
                record.setResponsavel(body.getResponsavel());
                Tarefa updated = repository.save(record);
                return ResponseEntity.ok().body(updated);
            })
            .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return repository.findById(id)
        .map(record -> {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        })
        .orElse(ResponseEntity.badRequest().build());
    }
}
