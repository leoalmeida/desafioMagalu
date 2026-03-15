package space.lasf.agendamento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.lasf.agendamento.domain.model.Agendamento;

/**
 * Repositório para a entidade de agendamento.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
