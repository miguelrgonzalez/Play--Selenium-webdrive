package play.modules.selenium.custom;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.testng.annotations.Factory;
import org.testng.annotations.BeforeSuite;

import play.Play;
import play.test.PlayJUnitRunner;

public class TestSuite {
    public PlayJUnitRunner.StartPlay startPlayBeforeTests;

    @BeforeSuite(alwaysRun = true)
    private void startPlay() {
        if (!Play.started) {
            System.out.print("Starting");
            Play.init(new File("."), "test");
            Play.javaPath.add(Play.getVirtualFile("test"));
            Play.start();
        }
    }

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

    private final static class ClassNameComparator implements Comparator<Class> {
        public int compare(Class aClass, Class bClass) {
            return aClass.getName().compareTo(bClass.getName());
        }
    }
    

    public static List<Class> allSeleniumTests() {
        List<Class> classes = Play.classloader.getAssignableClasses(BaseTest.class);
        for (ListIterator<Class> it = classes.listIterator(); it.hasNext();) {
            Class c = it.next();
            if (Modifier.isAbstract(c.getModifiers())) it.remove();
        }
        Collections.sort(classes, new ClassNameComparator());
        return classes;
    }


    //Run all tests
    @Factory
    public Object[] runTest() {
        try {
            this.startPlay();
            browsers = ((String) Play.configuration.get("test.selenium.browsers")).split(",");
            String hub = (String) Play.configuration.get("test.selenium.hub.url");

            List<Object> results = new ArrayList();
            for(Class clazz: allSeleniumTests())
                for(String browser : browsers) {
                    try {
                        System.out.println("Loading browser driver [" + drivers.get(browser).getBrowserName() + "]");
                        Testable test = (Testable) clazz.newInstance();
                        test.init(drivers.get(browser), hub);
                        results.add(test);
                    } catch(Exception e) {e.printStackTrace();}
                }
            return results.toArray(new Object[results.size()]); 
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}
