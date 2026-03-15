CREATE TABLE AGENDAMENTOS(
    id BIGINT(20) NOT NULL auto_increment,
    data_hora_agendamento DATETIME NULL,
    destinatario VARCHAR(255) NULL,
    mensagem TEXT NULL,
    tipo_entrega VARCHAR(50) NULL,
    status_entrega VARCHAR(50) NULL,
    PRIMARY KEY (id)
);