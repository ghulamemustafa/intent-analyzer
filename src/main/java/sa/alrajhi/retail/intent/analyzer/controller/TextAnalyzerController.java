package sa.alrajhi.retail.intent.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.alrajhi.retail.intent.analyzer.model.IntentResponseDto;
import sa.alrajhi.retail.intent.analyzer.model.mapper.IntentMapper;
import sa.alrajhi.retail.intent.analyzer.service.IntentMatcherService;
import sa.alrajhi.retail.intent.analyzer.similarity.TextSimilarityAlgorithm;

@RestController
@RequestMapping("/analyze")
public class TextAnalyzerController {

    @Value("${similarity-algorithm:LEVENSHTEIN}")
    private TextSimilarityAlgorithm algo;
    @Autowired
    private IntentMatcherService intentMatcherService;

    @RequestMapping(method = RequestMethod.GET)
    public IntentResponseDto analyze(@RequestParam String text, @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return IntentMapper.toIntentResponseDto(intentMatcherService.match(text, language, algo));
    }
}
