package br.com.bank.api.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.bank.domain.exception.AutenticacaoException;
import br.com.bank.domain.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

   public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
         + "o problema persistir, entre em contato com um administrador.";

   public static final String MSG_ERRO_AUTH_BAD_CREDENTIALS = "Suas credenciais não conferem. Favor verificar seu login e senha";

   @Autowired
   private MessageSource messageSource;

   @Override
   protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      return ResponseEntity.status(status).headers(headers).build();
   }

   @Override
   protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
   }

   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
   }

   private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
      ErroType erroType = ErroType.DADOS_INVALIDOS;
      String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

      List<ErroObject> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
         String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

         String name = objectError.getObjectName();

         if (objectError instanceof FieldError) {
            name = ((FieldError) objectError).getField();
         }

         return ErroObject.builder().name(name).userMessage(message).build();
      }).collect(Collectors.toList());

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).objects(problemObjects).build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      ErroType erroType = ErroType.RECURSO_NAO_ENCONTRADO;
      String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      if (ex instanceof MethodArgumentTypeMismatchException) {
         return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
      }

      return super.handleTypeMismatch(ex, headers, status, request);
   }

   private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      ErroType erroType = ErroType.PARAMETRO_INVALIDO;

      String detail = String.format(
            "O parâmetro de URL '%s' recebeu o valor '%s', "
                  + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      Throwable rootCause = ExceptionUtils.getRootCause(ex);

      if (rootCause instanceof InvalidFormatException) {
         return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
      } else if (rootCause instanceof PropertyBindingException) {
         return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
      }

      ErroType problemType = ErroType.MENSAGEM_INCOMPREENSIVEL;
      String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

      Erro erro = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
            .build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      String path = joinPath(ex.getPath());

      ErroType problemType = ErroType.MENSAGEM_INCOMPREENSIVEL;
      String detail = String
            .format("A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

      Erro erro = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
            .build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      String path = joinPath(ex.getPath());

      ErroType erroType = ErroType.MENSAGEM_INCOMPREENSIVEL;
      String detail = String.format(
            "A propriedade '%s' recebeu o valor '%s', "
                  + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            path, ex.getValue(), ex.getTargetType().getSimpleName());

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

      return handleExceptionInternal(ex, erro, headers, status, request);
   }

   @ExceptionHandler(NegocioException.class)
   public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
      HttpStatus status = HttpStatus.BAD_REQUEST;
      ErroType erroType = ErroType.ERRO_NEGOCIO;
      String detail = ex.getMessage();

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();

      return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
   }

   @ExceptionHandler(AutenticacaoException.class)
   public ResponseEntity<?> handleAutenticacao(AutenticacaoException ex, WebRequest request) {
      HttpStatus status = HttpStatus.UNAUTHORIZED;
      ErroType erroType = ErroType.ERRO_AUTENTICACAO;
      String detail = ex.getMessage();

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();

      return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
   }

   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Object> handleLogin(BadCredentialsException ex, WebRequest request) {
      HttpStatus status = HttpStatus.UNAUTHORIZED;
      ErroType erroType = ErroType.ERRO_AUTENTICACAO;
      String detail = MSG_ERRO_AUTH_BAD_CREDENTIALS;

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();

      return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
      HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
      ErroType erroType = ErroType.ERRO_DE_SISTEMA;
      String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

      log.error(ex.getMessage(), ex);

      Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();

      return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
   }

   @Override
   protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
      if (body == null) {
         body = Erro.builder().timestamp(new Date()).title(status.getReasonPhrase()).status(status.value())
               .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
      } else if (body instanceof String) {
         body = Erro.builder().timestamp(new Date()).title((String) body).status(status.value())
               .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
      }

      return super.handleExceptionInternal(ex, body, headers, status, request);
   }

   private Erro.ErroBuilder createProblemBuilder(HttpStatus status, ErroType problemType, String detail) {
      return Erro.builder().timestamp(new Date()).status(status.value()).type(problemType.getUri())
            .title(problemType.getTitle()).detail(detail);
   }

   private String joinPath(List<Reference> references) {
      return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
   }
}
