package sa.alrajhi.retail.intent.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.CollectionUtils;
import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.service.AppUtils;
import sa.alrajhi.retail.intent.analyzer.service.IntentMatcherService;
import sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm.JACCARD_DISTANCE;
import static sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm.JARO_WINKLER;
import static sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm.LCS;
import static sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm.LEVENSHTEIN;

@SpringBootTest
@TestPropertySource(properties = {"intent.file.path=classpath:test-intent.json"})
@Slf4j
public class MatcherServiceTest {

    public static final String LANG_EN = "en-US";
    public static final String LANG_AR = "ar-SA";
    @Autowired
    private IntentMatcherService intentMatcherService;

    public static Stream<Arguments> getExactMatchInput() {
        return Stream.of(
                Arguments.of("new beneficiary general", LANG_EN, LEVENSHTEIN),
                Arguments.of("transfer to alrajhi", LANG_EN, LEVENSHTEIN),
                Arguments.of("transfer to urpay", LANG_EN, LEVENSHTEIN),
                Arguments.of("اضافة مستفيد جديد", LANG_AR, LEVENSHTEIN),
                Arguments.of("أضافة مستفيد الراجحي", LANG_AR, LEVENSHTEIN),
                Arguments.of("التحويل الى مستفيد دولي", LANG_AR, LEVENSHTEIN)
        );
    }

    public static Stream<Arguments> getPartialMatchInput() {
        return Stream.of(
                Arguments.of("new international", LANG_EN, JARO_WINKLER),
                Arguments.of("new international", LANG_EN, LEVENSHTEIN),
                Arguments.of("new international", LANG_EN, LCS),
                Arguments.of("new international", LANG_EN, JACCARD_DISTANCE),
                Arguments.of("international", LANG_EN, JARO_WINKLER),
                Arguments.of("international", LANG_EN, LEVENSHTEIN),
                Arguments.of("international", LANG_EN, LCS),
                Arguments.of("international", LANG_EN, JACCARD_DISTANCE),
                Arguments.of("transfer to", LANG_EN, JARO_WINKLER),
                Arguments.of("Transfer to", LANG_EN, LEVENSHTEIN),
                Arguments.of("Transfer to", LANG_EN, LCS),
                Arguments.of("Transfer to", LANG_EN, JACCARD_DISTANCE),
                Arguments.of("my account", LANG_EN, JARO_WINKLER),
                Arguments.of("my account", LANG_EN, LEVENSHTEIN),
                Arguments.of("my account", LANG_EN, LCS),
                Arguments.of("my account", LANG_EN, JACCARD_DISTANCE),
                Arguments.of("my account", LANG_EN, JACCARD_DISTANCE),
                Arguments.of("مستفيد جديد", LANG_AR, JARO_WINKLER),
                Arguments.of("مستفيد جديد", LANG_AR, LEVENSHTEIN),
                Arguments.of("مستفيد جديد", LANG_AR, LCS),
                Arguments.of("مستفيد جديد", LANG_AR, JACCARD_DISTANCE),
                Arguments.of("ارسال", LANG_AR, JARO_WINKLER),
                Arguments.of("ارسال", LANG_AR, LEVENSHTEIN),
                Arguments.of("ارسال", LANG_AR, LCS),
                Arguments.of("ارسال", LANG_AR, JACCARD_DISTANCE)
        );
    }

    @ParameterizedTest
    @MethodSource("getExactMatchInput")
    public void testExactMatch(String input, String lang, TextSimilarityAlgorithm algo) {
        Intent intent = intentMatcherService.match(input, lang, algo);
        log.info("matched intent :{}", intent);
        assertNotNull(intent);
        List<String> collection = AppUtils.isEnglish(lang) ? intent.textEn() : intent.textAr();
        assertFalse(CollectionUtils.isEmpty(collection));
        assertTrue(collection.stream().anyMatch(text -> text.equalsIgnoreCase(input)));
    }

    @ParameterizedTest
    @MethodSource("getPartialMatchInput")
    public void testPartialMatch(String input, String lang, TextSimilarityAlgorithm algo) {
        Intent intent = intentMatcherService.match(input, lang, algo);
        log.info("matched intent : {}", intent);
        assertNotNull(intent);
    }
}
