package space.lasf.agendamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import space.lasf.agendamento.domain.repository.AgendamentoRepository;
import space.lasf.agendamento.dto.AgendamentoDto;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AgendamentoServiceIntegrationTest {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @BeforeEach
    void cleanUp() {
        agendamentoRepository.deleteAll();
    }

    @Test
    void createAgendamentoAndGetByIdShouldPersistData() {
        AgendamentoDto payload = AgendamentoDto.builder()
                .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 11, 30))
                .destinatario("cliente@exemplo.com")
                .mensagem("Confirmação de agendamento")
                .tipoEntrega("SMS")
                .statusEntrega("PENDENTE")
                .build();

        AgendamentoDto created = agendamentoService.createAgendamento(payload);

        assertNotNull(created.getId());
        assertEquals("cliente@exemplo.com", created.getDestinatario());

        AgendamentoDto loaded = agendamentoService.getAgendamentoById(created.getId());
        assertEquals(created.getId(), loaded.getId());
        assertEquals("cliente@exemplo.com", loaded.getDestinatario());
        assertEquals("SMS", loaded.getTipoEntrega());
    }

    @Test
    void deleteAgendamentoShouldRemovePersistedAgendamento() {
        AgendamentoDto payload = AgendamentoDto.builder()
                .dataHoraAgendamento(LocalDateTime.of(2026, 3, 16, 8, 0))
                .destinatario("5511999999999")
                .mensagem("Seu agendamento foi confirmado")
                .tipoEntrega("whatsapp")
                .statusEntrega("ENVIADO")
                .build();

        AgendamentoDto created = agendamentoService.createAgendamento(payload);
        Long agendamentoId = created.getId();

        agendamentoService.deleteAgendamento(agendamentoId);

        assertEquals(0, agendamentoRepository.count());
    }

    @Test
    void deleteAgendamentoShouldThrowWhenAgendamentoDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> agendamentoService.deleteAgendamento(9999L));
    }
}
