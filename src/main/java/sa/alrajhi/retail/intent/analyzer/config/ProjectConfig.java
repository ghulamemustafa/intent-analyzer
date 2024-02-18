package sa.alrajhi.retail.intent.analyzer.config;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    @Value("${client-connection-timeout:5000}")
    public int connectionTimeout;
    @Value("${client-response-timeout:5000}")
    public int responseTimeout;

    private final WebClient.Builder builder;

    @Value("${sitecore-base-url}")
    private String sitecoreBaseUrl;

    @Bean
    public WebClient sitecoreClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout) // Connection timeout
                .responseTimeout(Duration.ofMillis(responseTimeout)); // Max time between data packets
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(sitecoreBaseUrl)
                .build();
    }
}
