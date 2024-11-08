package stepDefinitions;

import driver.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.zaproxy.clientapi.core.ClientApiException;

import java.io.IOException;

import static utils.ZapUtility.generateZapReport;

public class Hooks {

    @Before
    public static void setUp() {
        Driver.initDriver();
    }

    @After
    public static void tearDown(Scenario scenario) throws IOException, ClientApiException {
        generateZapReport();
        Driver.quitDriver();
    }

}
