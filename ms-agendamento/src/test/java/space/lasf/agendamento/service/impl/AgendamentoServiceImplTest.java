package space.lasf.agendamento.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import space.lasf.agendamento.core.util.ObjectsValidator;
import space.lasf.agendamento.domain.model.Agendamento;
import space.lasf.agendamento.domain.repository.AgendamentoRepository;
import space.lasf.agendamento.dto.AgendamentoDto;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceImplTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ObjectsValidator<Agendamento> validador;

    private AgendamentoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AgendamentoServiceImpl(repository, validador);
        ReflectionTestUtils.setField(service, "modelMapper", modelMapper);
    }

    @Test
    void createAgendamentoShouldSaveAgendamento() {
        AgendamentoDto dtoIn = AgendamentoDto.builder()
            .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 10, 0))
            .destinatario("contato@empresa.com")
            .mensagem("Lembrete de reunião")
            .tipoEntrega("email")
            .statusEntrega("PENDENTE")
                .build();

        Agendamento agendamentoEntity = Agendamento.builder()
            .id(99L)
            .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 10, 0))
            .destinatario("contato@empresa.com")
            .mensagem("Lembrete de reunião")
            .tipoEntrega("email")
            .statusEntrega("PENDENTE")
            .build();
        Agendamento savedAgendamento = Agendamento.builder()
            .id(1L)
            .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 10, 0))
            .destinatario("contato@empresa.com")
            .mensagem("Lembrete de reunião")
            .tipoEntrega("email")
            .statusEntrega("PENDENTE")
            .build();

        AgendamentoDto mappedAgendamentoOut = AgendamentoDto.builder()
            .id(1L)
            .dataHoraAgendamento(LocalDateTime.of(2026, 3, 15, 10, 0))
            .destinatario("contato@empresa.com")
            .mensagem("Lembrete de reunião")
            .tipoEntrega("email")
            .statusEntrega("PENDENTE")
            .build();

        when(modelMapper.map(dtoIn, Agendamento.class)).thenReturn(agendamentoEntity);
        when(repository.save(agendamentoEntity)).thenReturn(savedAgendamento);
        when(modelMapper.map(savedAgendamento, AgendamentoDto.class)).thenReturn(mappedAgendamentoOut);

        AgendamentoDto result = service.createAgendamento(dtoIn);

        assertEquals(1L, result.getId());
        assertEquals("contato@empresa.com", result.getDestinatario());

        verify(validador).validate(agendamentoEntity);
    }

    @Test
    void deleteAgendamentoShouldDeleteWhenAgendamentoExists() {
        when(repository.findById(10L))
                .thenReturn(Optional.of(Agendamento.builder().id(10L).build()));

        service.deleteAgendamento(10L);

        verify(repository).deleteById(10L);
    }

    @Test
    void deleteAgendamentoShouldThrowWhenAgendamentoDoesNotExist() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.deleteAgendamento(10L));

        assertTrue(ex.getMessage().contains("Pedido não encontrado"));
        verify(repository, never()).deleteById(10L);
    }
}
