package me.dio.coding.votacao.bbb.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.dio.coding.votacao.bbb.api.model.ParticipanteModel;
import me.dio.coding.votacao.bbb.api.repository.ParticipanteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/participantes")
@AllArgsConstructor
@CrossOrigin
public class ParticipanteController {

    private final ParticipanteRepository repository;

    @PostMapping("/salvar")
    public ResponseEntity<ParticipanteModel> salvar(@RequestBody ParticipanteModel participante) {
        ParticipanteModel entidade = repository.save(participante);
        return ResponseEntity.ok(entidade);
    }

    @GetMapping("/consultar")
    public ResponseEntity<ParticipanteModel> consulta(@RequestParam String id) {
        Optional<ParticipanteModel> opt = repository.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/todos")
    public ResponseEntity<List<ParticipanteModel>> todos() {
        List<ParticipanteModel> list = repository.findAll();
        return ResponseEntity.ok(list);
    }
}