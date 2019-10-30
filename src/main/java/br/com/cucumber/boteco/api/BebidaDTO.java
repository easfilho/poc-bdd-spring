package br.com.cucumber.boteco.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BebidaDTO {

    private String nome;
    private Integer qualidade;
    private BigDecimal valor;
}
