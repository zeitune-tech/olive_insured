package sn.zeitune.oliveinsuranceinsured.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.BusinessException;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.handler.BusinessErrorCodes;
import sn.zeitune.oliveinsuranceinsured.handler.ExceptionResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusiness(BusinessException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException e) {
        return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(BusinessErrorCodes.USER_NOT_FOUND.getCode())
                .businessErrorDescription(BusinessErrorCodes.USER_NOT_FOUND.getDescription())
                .error(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException e) {

        log.error(e.getMessage());
        boolean isDuplicateEmail = e.getMessage().contains("email");
        boolean isDuplicateUsername = e.getMessage().contains("username");
        if (isDuplicateEmail) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.DUPLICATE_EMAIL.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.DUPLICATE_EMAIL.getDescription())
                                    .error(e.getMessage())
                                    .build()
                    );
        } else if (isDuplicateUsername) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.DUPLICATE_USERNAME.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.DUPLICATE_USERNAME.getDescription())
                                    .error(e.getMessage())
                                    .build()
                    );
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .error(e.getMessage())
                                    .build()
                    );
        }
    }


    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder().businessErrorCode(BusinessErrorCodes.NO_CODE.getCode())
                                .businessErrorDescription("Internal server error, please try again later")
                                .error(e.getMessage())
                                .build()
                );
    }
}