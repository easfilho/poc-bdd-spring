package br.com.cucumber.boteco.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoOpcaoBebidaDTO {

    private List<OpcaoBebida> bebidas;
    private BigDecimal troco;

}
