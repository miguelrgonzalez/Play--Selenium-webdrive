package play.modules.selenium.custom;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;

public class BaseTest {
    protected WebDriver driver;
    private DesiredCapabilities cap;

    public void init(DesiredCapabilities cap, String hub) throws MalformedURLException {
        this.driver = new RemoteWebDriver(new URL(hub), cap);
        this.cap = cap;
        this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun=true)
    public void shutdown() throws Exception {
        System.out.println("Shutting down browser [" + this.cap.getBrowserName() + "]");
        driver.quit();
    }
}
