package sa.alrajhi.retail.intent.analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sa.alrajhi.retail.intent.analyzer.model.Intent;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "local-loader", havingValue = "false", matchIfMissing = true)
public class SiteCoreIntentLoaderService implements IntentLoaderService {

    private final WebClient sitecoreClient;

    @Override
    public List<Intent> getIntents() {
        return sitecoreClient.get().uri("/intent")
                .retrieve()
                .bodyToFlux(Intent.class)
                .collectList()
                .block();
    }
}
