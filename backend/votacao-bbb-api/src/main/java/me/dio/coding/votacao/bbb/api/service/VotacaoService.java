package me.dio.coding.votacao.bbb.api.service;

import lombok.AllArgsConstructor;
import me.dio.coding.votacao.bbb.api.model.ParticipanteModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class VotacaoService {

    private static final String TOPICO_VOTACAO = "votacao";

    private final KafkaTemplate<Object, Object> template;
    private final Map<String, ParticipanteModel> participantes;

    public VotacaoService(KafkaTemplate<Object, Object> template, List<ParticipanteModel> participantes) {
        this.template = template;
        this.participantes = new HashMap<>();
        for (ParticipanteModel participante : participantes) {
            this.participantes.put(participante.getId(), participante);
        }
    }

    @PostMapping("/votar")
    public String votar(@RequestBody ParticipanteModel participante) {
        if (validarParticipante(participante.getId())) {
            template.send(TOPICO_VOTACAO, participante);
            return "Voto registrado para participante " + participante.getId();
        } else {
            return "Participante " + participante.getId() + " não existe. Voto não registrado.";
        }
    }

    private boolean validarParticipante(String idParticipante) {
        return participantes.containsKey(idParticipante);
    }
}