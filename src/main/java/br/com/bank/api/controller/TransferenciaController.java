package br.com.bank.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bank.api.dto.transferencia.TransferenciaDto;
import br.com.bank.domain.business.operacao.enums.TipoOperacaoEnum;
import br.com.bank.domain.business.operacao.model.Operacao;
import br.com.bank.domain.business.operacao.service.OperacaoService;

@RestController
@RequestMapping("transferencia")
@CrossOrigin(origins = "*")
public class TransferenciaController {

    @Autowired
    private ModelMapper mapper;
   
    @Autowired
    private OperacaoService service;

    @PostMapping
    public ResponseEntity<Operacao> inserir(HttpServletRequest request, @Valid @RequestBody TransferenciaDto dto) {
        Operacao model = new Operacao();
        mapper.map(dto, model);
        model.setTipo(TipoOperacaoEnum.TRANSFERENCIA.getTipo());
        model.setDescricao("OPERAÇÃO DE TRANSFERÊNCIA");
        return ResponseEntity.ok(service.inserir(model));
    }

}