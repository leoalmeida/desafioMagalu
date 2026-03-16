package space.lasf.agendamento.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import space.lasf.agendamento.dto.AgendamentoDto;
import space.lasf.agendamento.service.AgendamentoService;

/**
 * Controller para gerenciamento de agendamentos.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/agendamento")
public class AgendamentoController implements AgendamentoApi {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoDto>> getAgendamentos() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(agendamentoService.getAllAgendamentos());
    }

    @GetMapping("/{agendamentoId}")
    public ResponseEntity<AgendamentoDto> getAgendamentoById(@PathVariable final Long agendamentoId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(agendamentoService.getAgendamentoById(agendamentoId));
    }

    @PostMapping
    public ResponseEntity<AgendamentoDto> createAgendamento(
            @RequestBody @Valid final AgendamentoDto agendamento, final UriComponentsBuilder uriBuilder) {
        agendamentoService.createAgendamento(agendamento);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<Void> removeAgendamento(@PathVariable final Long agendamentoId) {
        agendamentoService.deleteAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }
}
