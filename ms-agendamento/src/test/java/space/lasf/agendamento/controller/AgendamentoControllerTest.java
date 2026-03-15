package space.lasf.agendamento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import space.lasf.agendamento.dto.AgendamentoDto;
import space.lasf.agendamento.service.AgendamentoService;

class AgendamentoControllerTest {

    private MockMvc mockMvc;

    private AgendamentoService agendamentoService;

    @BeforeEach
    void setUp() {
        agendamentoService = mock(AgendamentoService.class);

        AgendamentoController controller = new AgendamentoController();
        ReflectionTestUtils.setField(controller, "agendamentoService", agendamentoService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAgendamentosShouldReturnOk() throws Exception {
        List<AgendamentoDto> agendamentos = List.of(AgendamentoDto.builder()
                .id(1L)
                .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 9, 0))
                .destinatario("contato@empresa.com")
                .mensagem("Aviso")
                .tipoEntrega("email")
                .statusEntrega("PENDENTE")
                .build());
        when(agendamentoService.getAllAgendamentos()).thenReturn(agendamentos);

        mockMvc.perform(get("/api/v1/agendamento"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].destinatario").value("contato@empresa.com"));

        verify(agendamentoService).getAllAgendamentos();
    }

    @Test
    void getAgendamentoByIdShouldReturnOk() throws Exception {
        AgendamentoDto agendamento = AgendamentoDto.builder()
                .id(2L)
                .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 10, 0))
                .destinatario("5511988887777")
                .mensagem("Lembrete")
                .tipoEntrega("SMS")
                .statusEntrega("ENVIADO")
                .build();
        when(agendamentoService.getAgendamentoById(2L)).thenReturn(agendamento);

        mockMvc.perform(get("/api/v1/agendamento/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.tipoEntrega").value("SMS"));

        verify(agendamentoService).getAgendamentoById(2L);
    }

    @Test
    void createAgendamentoShouldReturnAccepted() throws Exception {
        String payload =
                """
            {
              "id": 4,
              "dataHoraAgendamento": "2026-03-15T12:15:00",
              "destinatario": "contato@empresa.com",
              "mensagem": "Mensagem de teste",
              "tipoEntrega": "email",
              "statusEntrega": "PENDENTE"
            }
            """;

        mockMvc.perform(post("/api/v1/agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isAccepted());

        verify(agendamentoService).createAgendamento(any(AgendamentoDto.class));
    }

    @Test
    void removeAgendamentoShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/agendamento/12")).andExpect(status().isNoContent());

        verify(agendamentoService).deleteAgendamento(12L);
    }
}
