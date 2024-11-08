package stepDefinitions;

import driver.DriverManager;
import enums.ConfigProperties;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.zaproxy.clientapi.core.ClientApiException;
import utils.ConfigFileReader;

import java.text.SimpleDateFormat;

import static utils.ZapUtility.*;

public class SecuritySteps {

    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");


    @And("The user is doing Passive scanning")
    public void the_user_is_doing_passive_scanning() throws ClientApiException {

        DriverManager.getDriver().get(ConfigFileReader.get(ConfigProperties.URL));
        waitTillPassiveScanCompleted();

    }

    @Then("The user is doing Active scanning")
    public void the_user_is_doing_active_scanning() throws ClientApiException {
        addURLToScanTree(ConfigFileReader.get(ConfigProperties.URL));
        performActiveScan(ConfigFileReader.get(ConfigProperties.URL));
    }

    @Then("The user is doing Spider scanning")
    public void the_user_is_doing_spider_scanning() throws ClientApiException {
        performSpidering(ConfigFileReader.get(ConfigProperties.URL));
    }


}
