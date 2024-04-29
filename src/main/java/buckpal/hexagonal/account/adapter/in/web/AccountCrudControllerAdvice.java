package buckpal.hexagonal.account.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = AccountCrudController.class)
public class AccountCrudControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResult illegalExHandle(DataIntegrityViolationException e) { //duplicate 계좌번호
        log.error("[exceptionHandle] ex key 중복 에러 발생~", e);
        return new ErrorResult("type", "Duplicate key", 403, e.getMessage(), "instance");
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
