package sa.alrajhi.retail.intent.analyzer.model.mapper;

import sa.alrajhi.retail.intent.analyzer.model.Intent;
import sa.alrajhi.retail.intent.analyzer.model.IntentResponseDto;

import java.util.Objects;

public class IntentMapper {
    public static IntentResponseDto toIntentResponseDto(Intent intent) {
        return Objects.isNull(intent) ? null : new IntentResponseDto(intent.code(), intent.tabIndex(), intent.keywordIndex());
    }
}
