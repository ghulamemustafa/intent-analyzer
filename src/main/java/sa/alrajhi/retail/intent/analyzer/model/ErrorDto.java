package sa.alrajhi.retail.intent.analyzer.model;

import lombok.Builder;

@Builder
public record ErrorDto(ErrorCode code, String message) {
}
