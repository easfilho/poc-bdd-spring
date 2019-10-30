package br.com.cucumber.boteco.business;

import br.com.cucumber.boteco.api.BebidaDTO;
import br.com.cucumber.boteco.api.ConsultaOpcaoBebidasDTO;
import br.com.cucumber.boteco.api.OpcaoBebida;
import br.com.cucumber.boteco.api.ResultadoOpcaoBebidaDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BebidaService {

    public ResultadoOpcaoBebidaDTO criarOpcoesBebida(ConsultaOpcaoBebidasDTO consultaOpcaoBebidasDTO) {
        if (!consultaOpcaoBebidasDTO.getQualidade()) {
            BebidaDTO bebidaDTO = consultaOpcaoBebidasDTO.getBebidas()
                    .stream()
                    .min(Comparator.comparing(BebidaDTO::getQualidade))
                    .get();
            Integer quantidade = consultaOpcaoBebidasDTO.getValorGastar()
                    .divide(bebidaDTO.getValor(), RoundingMode.HALF_EVEN)
                    .intValue();
            List<OpcaoBebida> listaOpcaoBebida = Arrays.asList(new OpcaoBebida(bebidaDTO.getNome(), quantidade));
            BigDecimal troco = consultaOpcaoBebidasDTO.getValorGastar()
                    .subtract(bebidaDTO.getValor().multiply(new BigDecimal(quantidade)))
                    .setScale(2, RoundingMode.HALF_EVEN);

            return new ResultadoOpcaoBebidaDTO(listaOpcaoBebida, troco);
        }
        List<OpcaoBebida> listaOpcaoBebida = new ArrayList<>();
        List<BebidaDTO> listaBebidaDTO = consultaOpcaoBebidasDTO.getBebidas()
                .stream()
                .sorted(Comparator.comparing(BebidaDTO::getQualidade).reversed())
                .collect(Collectors.toList());

        listaBebidaDTO.forEach(bebidaDTO -> {
            Integer quantidade = consultaOpcaoBebidasDTO.getValorGastar()
                    .divide(bebidaDTO.getValor(), RoundingMode.HALF_EVEN)
                    .intValue();
            if (quantidade > 0) {
                listaOpcaoBebida.add(new OpcaoBebida(bebidaDTO.getNome(), quantidade));
                BigDecimal valorRestante = consultaOpcaoBebidasDTO.getValorGastar()
                        .subtract(bebidaDTO.getValor().multiply(new BigDecimal(quantidade)));
                consultaOpcaoBebidasDTO.setValorGastar(valorRestante);
            }
        });
        return new ResultadoOpcaoBebidaDTO(listaOpcaoBebida, consultaOpcaoBebidasDTO.getValorGastar());
    }
}
