package space.lasf.agendamento.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import space.lasf.agendamento.domain.model.Agendamento;
import space.lasf.agendamento.domain.repository.AgendamentoRepository;

@EnabledIfSystemProperty(named = "docker.e2e", matches = "true")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
class AgendamentoFlowE2ETest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("agendamentodb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Value("${local.server.port}")
    private int port;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void createAndDeleteAgendamentoShouldFlowAndPersistInMySql() throws Exception {
        String requestBody =
                """
                {
                  \"id\": 9001,
                                    "dataHoraAgendamento": "2026-03-15T14:30:00",
                                    "destinatario": "contato@empresa.com",
                                    "mensagem": "Lembrete de consulta",
                                    "tipoEntrega": "email",
                                    "statusEntrega": "PENDENTE"
                }
                """;

        HttpResponse<String> createResponse = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/api/v1/agendamento"))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(202, createResponse.statusCode());

        awaitUntil(() -> agendamentoRepository.count() == 1);

        Agendamento storedAgendamento = agendamentoRepository.findAll().get(0);

        assertEquals("contato@empresa.com", storedAgendamento.getDestinatario());
        assertEquals("email", storedAgendamento.getTipoEntrega());
        assertEquals(LocalDateTime.of(2026, 3, 15, 14, 30), storedAgendamento.getDataHoraAgendamento());

        HttpResponse<String> deleteResponse = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(
                                "http://localhost:" + port + "/api/v1/agendamento/" + storedAgendamento.getId()))
                        .timeout(Duration.ofSeconds(10))
                        .DELETE()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(204, deleteResponse.statusCode());

        awaitUntil(() -> agendamentoRepository.count() == 0);
    }

    private void awaitUntil(Condition condition) throws Exception {
        long timeoutAt = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < timeoutAt) {
            if (condition.evaluate()) {
                return;
            }
            Thread.sleep(250);
        }
        assertTrue(condition.evaluate(), "Condition was not met within timeout");
    }

    @FunctionalInterface
    private interface Condition {
        boolean evaluate() throws Exception;
    }
}
