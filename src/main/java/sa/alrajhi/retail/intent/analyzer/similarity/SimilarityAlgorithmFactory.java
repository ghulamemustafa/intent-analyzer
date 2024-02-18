package sa.alrajhi.retail.intent.analyzer.similarity;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimilarityAlgorithmFactory {

    private final JaroWinklerDistance jaroWinklerDistance;
    private final LevenshteinDistance levenshteinDistance;
    private final LongestCommonSubsequenceDistance longestCommonSubsequenceDistance;
    private final JaccardDistance jaccardDistance;

    public EditDistance<?> getAlgorithm(TextSimilarityAlgorithm type) {
        return switch (type) {
            case JARO_WINKLER -> jaroWinklerDistance;
            case LEVENSHTEIN -> levenshteinDistance;
            case LCS -> longestCommonSubsequenceDistance;
            case JACCARD_DISTANCE -> jaccardDistance;
            // Add other cases as needed
            default -> throw new IllegalArgumentException("Unknown algorithm type: " + type);
        };
    }
}
