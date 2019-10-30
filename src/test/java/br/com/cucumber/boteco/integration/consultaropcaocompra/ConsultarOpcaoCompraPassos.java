package br.com.cucumber.boteco.integration.consultaropcaocompra;

import br.com.cucumber.boteco.api.BebidaDTO;
import br.com.cucumber.boteco.api.ConsultaOpcaoBebidasDTO;
import br.com.cucumber.boteco.api.ResultadoOpcaoBebidaDTO;
import br.com.cucumber.boteco.integration.ErroBadRequet;
import br.com.cucumber.boteco.integration.TestCucumberConfig;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsultarOpcaoCompraPassos extends TestCucumberConfig implements Pt {
    private Integer statusCodeRecebido;
    private ConsultaOpcaoBebidasDTO dto = new ConsultaOpcaoBebidasDTO();
    private ResultadoOpcaoBebidaDTO resultadoOpcaoBebidaDTO;
    private ErroBadRequet erro;

    public ConsultarOpcaoCompraPassos() {
        Dado("que tenho as seguintes bebidas para compra", (DataTable dataTable) -> {
            List<Map<String, String>> mapasBebidas = dataTable.asMaps();
            List<BebidaDTO> listaBebidaDTO = mapasBebidas.stream().map(mapaBebida -> {
                return BebidaDTO.builder()
                        .qualidade(new Integer(mapaBebida.get("Qualidade")))
                        .nome(mapaBebida.get("Nome"))
                        .valor(new BigDecimal(mapaBebida.get("Valor")))
                        .build();
            }).collect(Collectors.toList());
            dto.setBebidas(listaBebidaDTO);
        });

        Dado("que tenho {string} reais para comprar bebidas", (String valorGastar) -> {
            dto.setValorGastar(new BigDecimal(valorGastar));
        });

        Dado("que quero consultar as bebidas de menor qualidade", () -> {
            dto.setQualidade(false);
        });

        Dado("que quero consultar as bebidas de maior qualidade", () -> {
            dto.setQualidade(true);
        });

        Dado("que não informo o valor a gastar", () -> {
            dto.setValorGastar(null);
        });

        Quando("consultar as bebidas", () -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            Gson gson = gsonBuilder.create();

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, gson.toJson(dto));
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:8080/bebidas/opcoes")
                    .header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                statusCodeRecebido = response.code();
                if(statusCodeRecebido == HttpStatus.OK.value()) {
                    resultadoOpcaoBebidaDTO = new Gson().fromJson(response.body().string(), ResultadoOpcaoBebidaDTO.class);
                }
                if(statusCodeRecebido == HttpStatus.BAD_REQUEST.value()) {
                    erro = new Gson().fromJson(response.body().string(), ErroBadRequet.class);
                }
            }
        });

        Então("deve retornar as seguintes opções de bebidas", (DataTable resultadoEsperado) -> {
            List<Map<String, String>> mapasResultadoEsperado = resultadoEsperado.asMaps();
            for (int i = 0; i < mapasResultadoEsperado.size(); i++) {
                Assert.assertEquals(mapasResultadoEsperado.get(i).get("Nome"), resultadoOpcaoBebidaDTO.getBebidas().get(i).getNome());
                Assert.assertEquals(new Integer(mapasResultadoEsperado.get(i).get("Quantidade")), resultadoOpcaoBebidaDTO.getBebidas().get(i).getQuantidade());
            }
        });

        Então("deve sobrar de troco {string} reais", (String trocoEsperado) -> {
            Assert.assertEquals(trocoEsperado, resultadoOpcaoBebidaDTO.getTroco().toString());
        });

        Então("deve retornar o status {int}", (Integer statusEsperado) -> {
            Assert.assertEquals(statusEsperado, statusCodeRecebido);
        });

        Então("deve retornar a mensagem {string}", (String mensagemEsperada) -> {
            Assert.assertEquals(mensagemEsperada, erro.getErrors().get(0).getDefaultMessage());
        });

        Então("não deve retornar bebidas", () -> {
            Assert.assertEquals(0, resultadoOpcaoBebidaDTO.getBebidas().size());
        });
    }
}
