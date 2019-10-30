package br.com.cucumber.boteco.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ConsultaOpcaoBebidasDTO {

    @NotEmpty(message = "As bebidas devem ser informadas.")
    private List<BebidaDTO> bebidas;
    @NotNull(message = "Opção de escolha por qualidade deve ser informada.")
    private Boolean qualidade;
    @NotNull(message = "Valor a gastar deve ser informado.")
    private BigDecimal valorGastar;

}
