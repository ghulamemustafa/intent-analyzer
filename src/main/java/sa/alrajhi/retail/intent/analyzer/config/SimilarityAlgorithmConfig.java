package sa.alrajhi.retail.intent.analyzer.config;

import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimilarityAlgorithmConfig {

    @Bean
    public JaroWinklerDistance jaroWinklerDistance() {
        return new JaroWinklerDistance();
    }

    @Bean
    public LevenshteinDistance levenshteinDistance() {
        return new LevenshteinDistance();
    }

    @Bean
    public LongestCommonSubsequenceDistance longestCommonSubsequenceDistance() {
        return new LongestCommonSubsequenceDistance();
    }

    @Bean
    public JaccardDistance jaccardDistance() {
        return new JaccardDistance();
    }
}

