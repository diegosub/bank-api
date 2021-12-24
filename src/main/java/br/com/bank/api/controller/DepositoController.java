package br.com.bank.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deposito")
@CrossOrigin(origins = "*")
public class DepositoController extends BaseController {

}