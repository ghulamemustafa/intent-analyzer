package sa.alrajhi.retail.intent.analyzer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sa.alrajhi.retail.intent.analyzer.model.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ErrorCode code;
    private Object[] arguments;
}
