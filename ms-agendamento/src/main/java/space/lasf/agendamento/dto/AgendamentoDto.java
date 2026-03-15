package space.lasf.agendamento.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AgendamentoDto {

    private Long id;
    private LocalDateTime dataHoraAgendamento;
    private String destinatario;
    private String mensagem;
    // email, SMS, push e WhatsApp
    private String tipoEntrega;
    private String statusEntrega;
}
