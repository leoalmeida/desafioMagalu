package space.lasf.agendamento.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import space.lasf.agendamento.core.util.ObjectsValidator;
import space.lasf.agendamento.domain.model.Agendamento;
import space.lasf.agendamento.domain.repository.AgendamentoRepository;
import space.lasf.agendamento.dto.AgendamentoDto;
import space.lasf.agendamento.service.AgendamentoService;

/**
 * Implementação do serviço para gerenciamento de agendamentos.
 */
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private final AgendamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final ObjectsValidator<Agendamento> validador;

    @Override
    @Transactional
    public AgendamentoDto createAgendamento(final AgendamentoDto dto) {
        Agendamento agendamentoEntity = modelMapper.map(dto, Agendamento.class);
        agendamentoEntity.setId(null); // Garante que o ID seja nulo para criação
        validador.validate(agendamentoEntity);

        Agendamento result = repository.save(agendamentoEntity);

        if (result == null) {
            throw new IllegalStateException("Erro ao atualizar o pedido com ID: " + dto.getId());
        }

        AgendamentoDto resultDto = modelMapper.map(result, AgendamentoDto.class);
        return resultDto;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public AgendamentoDto getAgendamentoById(final Long id) {
        Agendamento entityIn = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        AgendamentoDto agendamentoDto = modelMapper.map(entityIn, AgendamentoDto.class);
        return agendamentoDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDto> getAllAgendamentos() {
        List<Agendamento> agendamentoEntities = repository.findAll();

        return agendamentoEntities.stream()
                .map(p -> modelMapper.map(p, AgendamentoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAgendamento(final Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Pedido não encontrado com ID: " + id);
        }
    }

}
