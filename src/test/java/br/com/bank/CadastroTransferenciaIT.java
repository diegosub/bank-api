package br.com.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bank.api.dto.auth.AutenticacaoDto;
import br.com.bank.api.dto.auth.UsuarioAutenticadoDto;
import br.com.bank.api.dto.deposito.DepositoDto;
import br.com.bank.api.dto.transferencia.TransferenciaDto;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.service.ContaService;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.business.seguranca.service.LoginService;
import br.com.bank.util.DatabaseCleaner;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CadastroTransferenciaIT {
 
   @Autowired
   private MockMvc mockMvc;
   
   @Autowired
   private LoginService loginService;
   
   @Autowired
   private DatabaseCleaner dataBaseCleaner;
   
   @Autowired
   private ContaService contaService;
   
   @BeforeEach
   public void setUp() {
      dataBaseCleaner.clearTables();
      this.prepararDados();
   }
   
   @Test
   public void deveRetornar200_quandoInserirTransferencia() throws Exception {
      TransferenciaDto dto = new TransferenciaDto();
      dto.setContaId(2l);
      dto.setContaDestId(3l);
      dto.setValor(100.0);
      this.mockMvc.perform(post("/transferencia")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header("Authorization", getToken())
                  .accept(MediaType.APPLICATION_JSON)
                  .content(this.asJsonString(dto)))
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.valor", is(100.0)));
   }
   
   @Test
   public void deveRetornar400_quandoTransferenciaNegativarConta() throws Exception {
      TransferenciaDto dto = new TransferenciaDto();
      dto.setContaId(2l);
      dto.setContaDestId(3l);
      dto.setValor(100000.0);
      
      this.mockMvc.perform(post("/transferencia")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getToken())
            .accept(MediaType.APPLICATION_JSON)
            .content(this.asJsonString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage", is("A conta de origem da transferência não pode ficar negativa.")));
   }
   
   @Test
   public void deveRetornar400_quandoInserirTransferenciaContaInexistente() throws Exception {
      TransferenciaDto dto = new TransferenciaDto();
      dto.setContaId(1000l);
      dto.setContaDestId(3000l);
      dto.setValor(10.0);
      
      this.mockMvc.perform(post("/transferencia")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getToken())
            .accept(MediaType.APPLICATION_JSON)
            .content(this.asJsonString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage", is("Conta não encontrada.")));
   }
   
   public String getToken() throws Exception {
      AutenticacaoDto dto = new AutenticacaoDto();
      dto.setCpf("23043408063");
      dto.setSenha("123");
      
      UsuarioAutenticadoDto retorno = loginService.login(dto);
      return retorno.getToken();
   }
   
   public String asJsonString(final Object obj) {
      try {
          return new ObjectMapper().writeValueAsString(obj);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
   }
   
   public void prepararDados() {
      Conta conta1 = new Conta();
      conta1.setUsuario(new Usuario());
      conta1.getUsuario().setNome("André Paulo");
      conta1.getUsuario().setCpf(23043408063l);
      conta1.setSaldo(200.0);
      contaService.inserir(conta1);
      
      Conta conta2 = new Conta();
      conta2.setUsuario(new Usuario());
      conta2.getUsuario().setNome("Anderson Piquet");
      conta2.getUsuario().setCpf(45058526018l);
      contaService.inserir(conta2);
   }
   
}
