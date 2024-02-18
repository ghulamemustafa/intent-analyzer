package sa.alrajhi.retail.intent.analyzer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.service.IntentLoaderService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"intent.file.path=classpath:test-intent.json"})
@Slf4j
public class SiteCoreIntentLoaderServiceTest {
    @MockBean
    private IntentLoaderService intentLoaderService;
    @Value("${intent.file.path}")
    private Resource intentResource;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoadIntentsAndPrint() throws IOException {
        List<Intent> mockIntents = objectMapper.readValue(intentResource.getInputStream(), new TypeReference<List<Intent>>() {
        });
        when(intentLoaderService.getIntents()).thenReturn(mockIntents);
        List<Intent> intents = intentLoaderService.getIntents();
        assertTrue(intents != null && !intents.isEmpty());
        log.info(intents.toString());
    }
}
