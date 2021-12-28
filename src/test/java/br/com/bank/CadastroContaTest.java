package br.com.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import br.com.bank.api.dto.conta.ContaDto;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.service.ContaService;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.util.DatabaseCleaner;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CadastroContaTest {
 
   @Autowired
   private MockMvc mockMvc;
   
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
   public void deveRetornar200_quandoPesquisarContas() throws Exception {
      this.mockMvc.perform(get("/conta"))
                  .andDo(print())
                  .andExpect(status().isOk());
   }
   
   @Test
   public void deveRetornar200_quandoPesquisarContasPorId() throws Exception {
      this.mockMvc.perform(get("/conta/2"))
                  .andDo(print())
                  .andExpect(status().isOk());
   }
   
   @Test
   public void deveRetornar400_quandoNaoEncontrarContaComId() throws Exception {
      this.mockMvc.perform(get("/conta/0"))
                  .andDo(print())
                  .andExpect(status().isBadRequest())
                  .andExpect(jsonPath("$.userMessage", is("Conta não encontrada.")));
   }
   
   @Test
   public void deveRetornar200_quandoInserirConta() throws Exception {
      ContaDto dto = new ContaDto();
      dto.setCpf("94554979059");
      dto.setNome("Francisco das Chagas");
      this.mockMvc.perform(post("/conta")
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON)
                  .content(this.asJsonString(dto)))
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
   }
   
   @Test
   public void deveRetornar400_quandoInserirContaComCpfJaExistente() throws Exception {
      ContaDto dto = new ContaDto();
      dto.setCpf("26239818097");
      dto.setNome("Carlos Firmino");
      
      this.mockMvc.perform(post("/conta")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.asJsonString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage", is("Já existe uma conta para este cpf.")));
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
      conta1.getUsuario().setNome("Silva Rocha");
      conta1.getUsuario().setCpf(60878322035l);
      contaService.inserir(conta1);
      
      Conta conta2 = new Conta();
      conta2.setUsuario(new Usuario());
      conta2.getUsuario().setNome("Maria Paula");
      conta2.getUsuario().setCpf(26239818097l);
      contaService.inserir(conta2);
   }
   
}
