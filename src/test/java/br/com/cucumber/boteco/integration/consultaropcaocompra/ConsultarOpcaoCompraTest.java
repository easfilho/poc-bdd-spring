package br.com.cucumber.boteco.integration.consultaropcaocompra;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@ContextConfiguration
@CucumberOptions(features = {"src/test/resources/features/US001-ConsularOpcaoCompraBebidas.feature"},
        glue = "br.com.cucumber.boteco.integration.consultaropcaocompra",
        strict = true)
public class ConsultarOpcaoCompraTest {
}
