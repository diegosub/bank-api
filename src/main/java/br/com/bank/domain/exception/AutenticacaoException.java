package br.com.bank.domain.exception;

public class AutenticacaoException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public AutenticacaoException(Exception e) {
        super("Suas credenciais não conferem. Favor verificar login e senha.");
    }

    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }

    public AutenticacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
