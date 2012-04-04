package selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import play.modules.selenium.custom.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {

    public GoogleTest(DesiredCapabilities cap, String hub) throws Exception {
        super(cap, hub);
    }

    @Test
    public void checkWebpage () {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("Swets");
        driver.findElement(By.name("q")).submit();
        driver.close();
    }


}
