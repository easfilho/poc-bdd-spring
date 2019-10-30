package br.com.cucumber.boteco.api;

import br.com.cucumber.boteco.business.BebidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController

public class BebidaRest {

    private final BebidaService bebidaService;

    public BebidaRest(BebidaService bebidaService) {
        this.bebidaService = bebidaService;
    }

    @PostMapping("/bebidas/opcoes")
    public ResponseEntity<ResultadoOpcaoBebidaDTO> criarOpcoes(@Valid @RequestBody ConsultaOpcaoBebidasDTO consultaOpcaoBebidasDTO) {
        ResultadoOpcaoBebidaDTO resultadoOpcaoBebidaDTO = bebidaService.criarOpcoesBebida(consultaOpcaoBebidasDTO);
        return ResponseEntity.ok(resultadoOpcaoBebidaDTO);
    }
}
