package play.modules.selenium.custom;

import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;

public interface Testable {

    public void init(DesiredCapabilities cap, String hub) throws MalformedURLException;

}
