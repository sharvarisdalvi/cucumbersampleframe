package org.testframework.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

public class DriverFactory {
    private String driverType;
    private String INPUT_ZIP_FILE_IEDRIVER = null;
    private String INPUT_ZIP_FILE_CHROMEDRIVER = null;
    private String OUTPUT_FOLDER = null;
    private boolean enableJavascript = true;
    private String LOCAL_FIREFOX_X11_PATH = "/opt/local/bin/firefox-x11";
    private String LOCAL_X11_DISPLAY = ":5";
    private Unzip unZip;

    public WebDriver loadDriver(String browser) throws InterruptedException, IOException {
        String Separator = getProperty("file.separator");
        File parentDir = new File(getProperty("user.dir"));

        OUTPUT_FOLDER = parentDir.getPath() + Separator + "test-modules" + Separator + "test-core" + Separator + "src" + Separator + "main" + Separator + "java" + Separator + "org" + Separator + "openlmis" + Separator + "UiUtils" + Separator;
        INPUT_ZIP_FILE_IEDRIVER = OUTPUT_FOLDER + "IEDriverServer_Win32_2.33.0.zip";
        INPUT_ZIP_FILE_CHROMEDRIVER = OUTPUT_FOLDER + "chromedriver.zip";

        return loadDriver(enableJavascript, browser);
    }

    public String driverType() throws InterruptedException {
        return driverType.trim();
    }

    public WebDriver getDriver() throws IOException, InterruptedException{
        return loadDriver(true, "firefox");
    }

    public WebDriver loadDriver(boolean enableJavascript, String browser) throws InterruptedException, IOException {
        switch (browser) {
            case "firefox":
                driverType = getProperty("web.driver", "Firefox");
                return createFirefoxDriver(enableJavascript);

            case "ie":
                unZip = new Unzip();
                unZip.unZipIt(INPUT_ZIP_FILE_IEDRIVER, OUTPUT_FOLDER);
                Thread.sleep(1500);
                driverType = setProperty("webdriver.ie.driver", OUTPUT_FOLDER + "IEDriverServer.exe");
                driverType = getProperty("webdriver.ie.driver");

                return createInternetExplorerDriver();


            case "chrome":
                unZip = new Unzip();
                unZip.unZipIt(INPUT_ZIP_FILE_CHROMEDRIVER, OUTPUT_FOLDER);
                Thread.sleep(1500);
                driverType = setProperty("webdriver.chrome.driver", OUTPUT_FOLDER + "chromedriver.exe");
                driverType = getProperty("webdriver.chrome.driver");
                return createChromeDriver();

            default:
                driverType = getProperty("web.driver", "Firefox");
                return createFirefoxDriver(enableJavascript);
        }
    }

    private WebDriver createFirefoxDriver(boolean enableJavascript) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("signed.applets.codebase_principal_support", true);
        profile.setPreference("javascript.enabled", enableJavascript);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");
        profile.setPreference("browser.download.dir",new File(System.getProperty("user.dir")).getParent());
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("dom.storage.enabled", true);
        profile.setPreference("device.storage.enabled", true);
        profile.setPreference("network.proxy.share_proxy_settings", true);
        profile.setPreference("network.proxy.type",2);
        profile.setPreference("network.proxy.autoconfig_url", "http://proxy.cat.com/");


        return new FirefoxDriver(profile);
    }

    private WebDriver createInternetExplorerDriver() throws IOException {
        Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability("ignoreZoomSetting", true);
        return new InternetExplorerDriver(ieCapabilities);
    }


    private WebDriver createChromeDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        Map<String, String> prefs = new Hashtable<>();
        prefs.put("download.prompt_for_download", "false");
        prefs.put("download.default_directory", "C:\\Users\\openlmis\\Downloads");

        String[] switches = {"--start-maximized","--ignore-certificate-errors"};
        capabilities.setCapability("chrome.prefs", prefs);
        capabilities.setCapability("chrome.switches", Arrays.asList(switches));

        return new ChromeDriver(capabilities);
    }

}
