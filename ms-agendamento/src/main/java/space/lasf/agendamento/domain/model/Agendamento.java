package space.lasf.agendamento.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um agendamento.
 */
@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Agendamento {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora_agendamento")
    private LocalDateTime dataHoraAgendamento;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "mensagem")
    private String mensagem;
    // email, SMS, push e WhatsApp
    @Column(name = "tipo_entrega")
    private String tipoEntrega;

    @Column(name = "status_entrega")
    private String statusEntrega;

    public Agendamento updateData(final Agendamento agendamento) {
        this.dataHoraAgendamento = agendamento.getDataHoraAgendamento();
        this.destinatario = agendamento.getDestinatario();
        this.mensagem = agendamento.getMensagem();
        this.tipoEntrega = agendamento.getTipoEntrega();
        this.statusEntrega = agendamento.getStatusEntrega();
        return this;
    }
}
