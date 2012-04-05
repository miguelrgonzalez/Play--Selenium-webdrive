package play.modules.selenium.custom;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import play.Play;
import play.test.UnitTest;

@RunWith(Parameterized.class)
public class BaseTest extends UnitTest{
    protected WebDriver driver;
    private DesiredCapabilities cap;

    private static String[] browsers;
    private static final Map<String, DesiredCapabilities> drivers;

    static {
        //load list of supported browsers
        drivers = new HashMap<String, DesiredCapabilities>();
        drivers.put("iexplorer", DesiredCapabilities.internetExplorer()); 
        drivers.put("firefox", DesiredCapabilities.firefox()); 
        drivers.put("chrome", DesiredCapabilities.chrome()); 
        drivers.put("opera", DesiredCapabilities.opera()); 
        drivers.put("html", DesiredCapabilities.htmlUnit()); 
        drivers.put("iphone", DesiredCapabilities.iphone()); 
        drivers.put("android", DesiredCapabilities.android()); 
        //drivers.put("safari", DesiredCapabilities.safari()); 
        //drivers.put("ipad", DesiredCapabilities.ipad()); 
    }

    public BaseTest(DesiredCapabilities cap, String hub)  {
        try {
            this.driver = new RemoteWebDriver(new URL(hub), cap);
            this.cap = cap;
            this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            System.out.println("Starting browser [" + this.cap.getBrowserName() + "]");
        } catch (MalformedURLException e) {}
    }

    @After
    public void shutdown() throws Exception {
        System.out.println("Shutting down browser [" + this.cap.getBrowserName() + "]");
        driver.quit();
    }

    @Parameters
    public static Collection<Object[]> browsers() {
        if (!Play.started) {
            System.out.print("Starting");
            Play.init(new File("."), "test");
            Play.javaPath.add(Play.getVirtualFile("test"));
            Play.start();
        }
        Collection<Object[]> bros = new ArrayList();
        browsers = ((String) Play.configuration.get("test.selenium.browsers")).split(",");
        String hub = (String) Play.configuration.get("test.selenium.hub.url");
        for(String browser : browsers)
            bros.add(new Object[]{drivers.get(browser), hub});
        return bros;
    }
}
