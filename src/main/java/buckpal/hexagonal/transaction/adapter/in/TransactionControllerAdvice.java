package buckpal.hexagonal.transaction.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = TransactionController.class)
public class TransactionControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) { // 계좌이 체 중 발생 하는 예외 처리

        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("type", "Insufficient money", 403, e.getMessage(), "instance");
    }

    @AllArgsConstructor
    @Getter
    static class ErrorResult {
        private String type;
        private String title;
        private int status;
        private String detail;
        private String instance;
    }
}