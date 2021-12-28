package br.com.bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import br.com.bank.api.dto.auth.AutenticacaoDto;
import br.com.bank.api.dto.auth.UsuarioAutenticadoDto;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.service.ContaService;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.business.seguranca.service.LoginService;
import br.com.bank.util.DatabaseCleaner;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ConsultaHistoricoIT {
 
   @Autowired
   private MockMvc mockMvc;
   
   @Autowired
   private DatabaseCleaner dataBaseCleaner;
   
   @Autowired
   private LoginService loginService;
   
   @Autowired
   private ContaService contaService;
   
   @BeforeEach
   public void setUp() {
      dataBaseCleaner.clearTables();
      this.prepararDados();
   }
   
   @Test
   public void deveRetornar200_quandoPesquisarMovimentacaoConta() throws Exception {
      this.mockMvc.perform(get("/movimentacao/2")
                       .header("Authorization", getToken()))
                  .andDo(print())
                  .andExpect(status().isOk());
   }
   
   @Test
   public void deveRetornar200_quandoPesquisaPaginado() throws Exception {
      this.mockMvc.perform(get("/movimentacao/2?page=0&size=2")
                        .header("Authorization", getToken()))
                   .andDo(print())
                   .andExpect(status().isOk());
   }
   
   public String getToken() throws Exception {
      AutenticacaoDto dto = new AutenticacaoDto();
      dto.setCpf("23472641096");
      dto.setSenha("123");
      
      UsuarioAutenticadoDto retorno = loginService.login(dto);
      return retorno.getToken();
   }
   
   public void prepararDados() {
      Conta conta1 = new Conta();
      conta1.setUsuario(new Usuario());
      conta1.getUsuario().setNome("André Paulo");
      conta1.getUsuario().setCpf(23472641096l);
      contaService.inserir(conta1);
   }
   
}
