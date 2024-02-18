package sa.alrajhi.retail.intent.analyzer.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sa.alrajhi.retail.intent.analyzer.exception.BusinessException;
import sa.alrajhi.retail.intent.analyzer.model.ErrorCode;
import sa.alrajhi.retail.intent.analyzer.model.ErrorDto;

import java.util.Locale;

@ControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@Slf4j
public class GlobalAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleBusinessException(BusinessException ex, Locale locale) {
        ErrorCode code = ex.getCode();
        String localizedMessage = messageSource.getMessage(code.name(), ex.getArguments(), ex.getMessage(), locale);
        log.error("BusinessException", ex);
        return ResponseEntity.badRequest().body(ErrorDto.builder().code(code).message(localizedMessage).build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleBusinessException(Exception ex, WebRequest webRequest, Locale locale) {
        ErrorCode code = ErrorCode.E_001;
        String localizedMessage = messageSource.getMessage(code.name(), null, "something went wrong", locale);
        log.error("Exception", ex);
        return ResponseEntity.badRequest().body(ErrorDto.builder().code(code).message(localizedMessage).build());
    }
}