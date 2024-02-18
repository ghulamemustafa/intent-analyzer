package sa.alrajhi.retail.intent.analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.EditDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.similarity.SimilarityAlgorithmFactory;
import sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimilarityIntentMatcherService implements IntentMatcherService {

    @Value("${similarity-min-threshold:5.0}")
    public double SIMILARITY_MIN_THRESHOLD;
    private final IntentLoaderService intentLoaderService;
    private final SimilarityAlgorithmFactory similarityAlgorithmFactory;

    @Override
    public Intent match(String text, String language, TextSimilarityAlgorithm algorithm) {
        EditDistance<?> algo = similarityAlgorithmFactory.getAlgorithm(algorithm);
        List<Intent> intents = intentLoaderService.getIntents();
        boolean english = AppUtils.isEnglish(language);
        Intent intent = null;
        Intent closestIntent = null;
        double closest = SIMILARITY_MIN_THRESHOLD;
        double current = 1.0;
        String textLowerCase = text.toLowerCase();
        for (Iterator<Intent> iterator = intents.iterator(); iterator.hasNext(); ) {
            intent = iterator.next();
            List<String> textList = english ? intent.textEn() : intent.textAr();
            for (String intentText : textList) {
                Object temp = algo.apply(textLowerCase, intentText.toLowerCase());
                if (temp instanceof Integer) {
                    current = Double.valueOf((Integer) temp);
                } else {
                    current = (double) temp;
                }
                if (current < closest) {
                    closest = current;
                    closestIntent = intent;
                }
                log.info("text : {}, language : {}, algo : {}, intent : {}", text, language, algorithm, intent);
                log.debug("distance : {}, closest : {}", current, closest);
            }
        }
        return closestIntent;
    }
}
