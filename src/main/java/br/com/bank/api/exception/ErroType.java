package br.com.bank.api.exception;

public enum ErroType
{
   DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
   ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
   PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
   MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
   RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
   ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
   ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
   ERRO_AUTENTICACAO("/erro-autenticacao", "Erro de autenticação");

   ErroType(String path, String title)
   {
       this.uri = "http://pdn.com.br" + path;
       this.title = title;
   }
   
   private String title;

   private String uri;

   public String getTitle()
   {
      return title;
   }
   public void setTitle(String title)
   {
      this.title = title;
   }
   public String getUri()
   {
      return uri;
   }
   public void setUri(String uri)
   {
      this.uri = uri;
   }
}
