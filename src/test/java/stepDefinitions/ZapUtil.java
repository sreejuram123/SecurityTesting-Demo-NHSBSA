package stepDefinitions;

import org.openqa.selenium.Proxy;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZapUtil {

    private static ClientApi clientApi;
    public static Proxy proxy;
    private static final String zapAddress = "127.0.0.1";
    private static final int zapPort = 8090;
    private static final String apiKey = "jnftamggomv1i3bcevokkgg1dv";


    static {
        clientApi = new ClientApi(zapAddress, zapPort, apiKey);
        proxy = new Proxy().setSslProxy(zapAddress + ":" + zapPort).setHttpProxy(zapAddress + ":" + zapPort);
    }

    public static void waitTillPassiveScanCompleted() {
        try {
            ApiResponse apiResponse = clientApi.pscan.recordsToScan();
            String tempValue = ((ApiResponseElement) apiResponse).getValue();

            while (!tempValue.equals("0")) {
                System.out.println("Passive scan is in progress");
                apiResponse = clientApi.pscan.recordsToScan();
                tempValue = ((ApiResponseElement) apiResponse).getValue();
            }
            System.out.println("Passive scan is completed");

        } catch (ClientApiException e) {
            e.printStackTrace();
        }
    }

//    public static void generateZapReport(String urlToTest, String reportName) {
//        String title = "Demo Title";
//        String sites = urlToTest;
//        String description = "Demo description";
//
//        String template = "traditional-html-plus";
//        String sections = "chart|alertcount|passingrules|instancecount|statistics|alertdetails";
//        String theme = "dark";
//
//        String includedrisks = "High|Medium|Low";
//        String includedconfidences = null;
//        String reportfilename = reportName;
//        //String reportfilenamepattern = "{{yyyy-MM-dd}}-ZAP-Report-[[site]]";
//        String reportdir = System.getProperty("user.dir")+"//reports";
//        String display = "true";
//        String contexts = null;
//
//        try {
//            clientApi.reports.generate(title, template, theme, description, contexts, sites, sections,
//                    includedconfidences, includedrisks, reportfilename, "", reportdir, display);
//        } catch (ClientApiException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void generateZapReport() throws ClientApiException, IOException {
//        byte[] bytes=clientApi.core.xmlreport();
//                String str=new String(bytes, StandardCharsets.UTF_8);
//        File newtxt=new File("target/zap.html");
//        try(FileWriter fw=new FileWriter(newtxt)){
//            fw.write(str);
//        }
//    }

    public static void generateZapReport(String urlToTest, String reportName) {
        String title = "Demo Title";
        String sites = urlToTest;
        String description = "Demo description";

        String template = "traditional-html-plus";
        String sections = "chart|alertcount|passingrules|instancecount|statistics|alertdetails";
        String theme = "dark";

        String includedrisks = "High|Medium|Low";
        String includedconfidences = null;
        String reportfilename = reportName;
        //String reportfilenamepattern = "{{yyyy-MM-dd}}-ZAP-Report-[[site]]";
        String reportdir = System.getProperty("user.dir") + "//reports";
        String display = "true";
        String contexts = null;

        try {
            clientApi.reports.generate(title, template, theme, description, contexts, sites, sections,
                    includedconfidences, includedrisks, reportfilename, "", reportdir, display);
        } catch (ClientApiException e) {
            e.printStackTrace();
        }
    }


}

