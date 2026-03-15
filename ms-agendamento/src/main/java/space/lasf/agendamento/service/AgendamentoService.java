package space.lasf.agendamento.service;

import java.util.List;
import space.lasf.agendamento.dto.AgendamentoDto;

/**
 * Serviço para gerenciamento de pedidos.
 */
public interface AgendamentoService {

    AgendamentoDto createAgendamento(AgendamentoDto agendamento);

    AgendamentoDto getAgendamentoById(Long id);

    List<AgendamentoDto> getAllAgendamentos();

    void deleteAgendamento(Long id);
}
