package br.com.cucumber.boteco.integration;

import br.com.cucumber.boteco.BotecoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = {BotecoApplication.class})
public class TestCucumberConfig {
}
