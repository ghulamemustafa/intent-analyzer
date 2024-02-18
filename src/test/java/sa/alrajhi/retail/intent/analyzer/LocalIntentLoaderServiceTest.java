package sa.alrajhi.retail.intent.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.service.IntentLoaderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {"intent.file.path=classpath:test-intent.json","local-loader=true"})
@Slf4j
public class LocalIntentLoaderServiceTest {

    @Autowired
    private IntentLoaderService intentLoaderService;

    @Test
    public void testLoadIntentsAndPrint() {
        List<Intent> intents = intentLoaderService.getIntents();
        assertTrue(intents != null && !intents.isEmpty());
        log.info(intents.toString());
    }
}

