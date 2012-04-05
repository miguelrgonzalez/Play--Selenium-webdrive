package selenium;

import java.net.MalformedURLException;
import org.junit.runners.Parameterized;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import play.modules.selenium.custom.BaseTest;

public class GoogleTest extends BaseTest {

    public GoogleTest(DesiredCapabilities cap, String hub) throws MalformedURLException {
        super(cap,hub);
    }

    @Test
    public void checkWebpage () {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("Swets");
        driver.findElement(By.name("q")).submit();
        driver.close();
    }


}
