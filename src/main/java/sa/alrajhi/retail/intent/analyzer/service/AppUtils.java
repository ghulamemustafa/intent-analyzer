package sa.alrajhi.retail.intent.analyzer.service;

import java.util.Locale;

public class AppUtils {
    public static boolean isEnglish(String language) {
        return Locale.ENGLISH.getLanguage().equals(Locale.forLanguageTag(language).getLanguage());
    }
}