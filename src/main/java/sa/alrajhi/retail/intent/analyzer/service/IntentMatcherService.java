package sa.alrajhi.retail.intent.analyzer.service;

import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm;

public interface IntentMatcherService {
    Intent match(String text, String language, TextSimilarityAlgorithm algorithm);
}
