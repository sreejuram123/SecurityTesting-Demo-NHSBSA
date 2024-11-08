package utils;

import org.openqa.selenium.Proxy;
import org.zaproxy.clientapi.core.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ZapUtility {

    private static ClientApi clientApi;
    public static Proxy proxy;
    private static final String zapAddress = "127.0.0.1";
    private static final int zapPort = 8080;
    private static final String apiKey = "";
    public static ApiResponse apiResponse;

    static {
        clientApi = new ClientApi(zapAddress, zapPort, apiKey);
        proxy = new Proxy().setSslProxy(zapAddress + ":" + zapPort).setHttpProxy(zapAddress + ":" + zapPort);
    }

    public static void waitTillPassiveScanCompleted() {
        try {
            apiResponse = clientApi.pscan.recordsToScan();
            String tempValue = ((ApiResponseElement) apiResponse).getValue();

            while (!tempValue.equals("0")) {
                System.out.println("Passive scan is in progress " + tempValue);
                apiResponse = clientApi.pscan.recordsToScan();
                tempValue = ((ApiResponseElement) apiResponse).getValue();
            }
            System.out.println("Passive scan is completed");

        } catch (ClientApiException e) {
            e.printStackTrace();
        }
    }

    public static void waitTillActiveScanCompleted(String scanId) throws ClientApiException {

        apiResponse = clientApi.ascan.status(scanId);
        String status = ((ApiResponseElement) apiResponse).getValue();

        while (!status.equals("100")) {
            apiResponse = clientApi.ascan.status(scanId);
            status = ((ApiResponseElement) apiResponse).getValue();
            System.out.println("Active scan is in progress");
        }
        System.out.println("Active scan is completed");


    }

    public static void addURLToScanTree(String urls) throws ClientApiException {
        clientApi.core.accessUrl(urls, "false");
        if (getUrlsFromScanTree().contains(urls)) {
            System.out.println(urls + " has been added to scan tree");
        } else
            throw new RuntimeException(urls + " no added to scan tree, active scan is not possible");
    }


    public static List<String> getUrlsFromScanTree() throws ClientApiException {
        apiResponse = clientApi.core.urls();
        List<ApiResponse> responses = ((ApiResponseList) apiResponse).getItems();
        return responses.stream().map(r -> ((ApiResponseElement) r).getValue()).collect(Collectors.toList());
    }

    private static Integer getContextAfterImporting(String contextName) throws ClientApiException {
        apiResponse = clientApi.context.importContext(contextName);
        return Integer.parseInt(((ApiResponseElement) apiResponse).getValue());
    }

    public static void performActiveScan(String urls) throws ClientApiException {
        String url = urls;
        String recurse = null;
        String inscopeonly = null;
        String scanpolicyname = null;
        String method = null;
        String postdata = null;
        apiResponse = clientApi.ascan.scan(url, "false", inscopeonly, scanpolicyname, method, postdata);
        String scanId = ((ApiResponseElement) apiResponse).getValue();

        waitTillActiveScanCompleted(scanId);


    }

    public static void performSpidering(String urls) throws ClientApiException {

        apiResponse = clientApi.spider.scan(urls, null, null, null, null);
        String spiderScanId = ((ApiResponseElement) apiResponse).getValue();


        apiResponse = clientApi.spider.status(spiderScanId);
        String spiderScanStatus = ((ApiResponseElement) apiResponse).getValue();

        while (!spiderScanStatus.equals("100")) {
            apiResponse = clientApi.spider.status(spiderScanId);
            spiderScanStatus = ((ApiResponseElement) apiResponse).getValue();
            System.out.println("Spidering is in progress, current status=" + spiderScanStatus);

        }

        waitTillPassiveScanCompleted();

    }



    public static void generateZapReport() throws ClientApiException, IOException {

        byte[] bytes = clientApi.core.htmlreport();
        String str = new String(bytes, StandardCharsets.UTF_8);
        File newTextFile = new File("security_report.html");
        FileWriter fw = new FileWriter(newTextFile);
        fw.write(str);
        fw.close();
    }

}
