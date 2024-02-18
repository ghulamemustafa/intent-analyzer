package sa.alrajhi.retail.intent.analyzer.model;


import java.util.List;

public record Intent(String code, List<String> textAr, List<String> textEn, String tabIndex, String keywordIndex) {

}
