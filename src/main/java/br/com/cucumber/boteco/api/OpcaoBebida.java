package br.com.cucumber.boteco.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoBebida {

    private String nome;
    private Integer quantidade;

}
