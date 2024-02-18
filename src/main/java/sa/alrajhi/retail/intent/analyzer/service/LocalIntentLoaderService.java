package sa.alrajhi.retail.intent.analyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sa.alrajhi.retail.intent.analyzer.model.Intent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "local-loader", havingValue = "true")
@Slf4j
public class LocalIntentLoaderService implements IntentLoaderService {

    @Value("${intent.file.path:classpath:intent.json}")
    private Resource intentResource;

    private final ObjectMapper objectMapper;
    private List<Intent> intents;

    @PostConstruct
    public void loadIntents() {
        try {
            intents = objectMapper.readValue(intentResource.getInputStream(), new TypeReference<List<Intent>>() {
            });
            log.debug("intent count : {}", intents.size());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load intents from JSON", e);
        }
    }

    @Override
    public List<Intent> getIntents() {
        return Collections.unmodifiableList(intents);
    }
}
