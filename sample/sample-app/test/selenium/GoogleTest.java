package selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import play.modules.selenium.custom.BaseTest;
import play.modules.selenium.custom.Testable;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest implements Testable{

    @Test
    public void checkWebpage () {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("Swets");
        driver.findElement(By.name("q")).submit();
        driver.close();
    }


}
