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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bank.api.dto.auth.AutenticacaoDto;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.service.ContaService;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.util.DatabaseCleaner;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class LoginTest {
 
   @Autowired
   private MockMvc mockMvc;
   
   @Autowired
   private ContaService contaService;
   
   @Autowired
   private DatabaseCleaner dataBaseCleaner;
   
   @BeforeEach
   public void setUp() {
      dataBaseCleaner.clearTables();
      this.prepararDados();
   }
   
   @Test
   public void deveRetornar200_quandoRealizarLogin() throws Exception {
      
      AutenticacaoDto dto = new AutenticacaoDto();
      dto.setCpf("30478617062");
      dto.setSenha("123");
      
      this.mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.asJsonString(dto)))
                  .andDo(print())
                  .andExpect(status().isOk());
   }
   
   @Test
   public void deveRetornar401_quandoCredenciaisInvalidas() throws Exception {
      AutenticacaoDto dto = new AutenticacaoDto();
      dto.setCpf("3047861706223");
      dto.setSenha("12399");
      
      this.mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.asJsonString(dto)))
                  .andDo(print())
                  .andExpect(status().isUnauthorized())
                  .andExpect(jsonPath("$.userMessage", is("Suas credenciais não conferem. Favor verificar seu login e senha")));
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
      conta1.getUsuario().setNome("Cris Ronald");
      conta1.getUsuario().setCpf(30478617062l);
      contaService.inserir(conta1);
      
   }
   
}
