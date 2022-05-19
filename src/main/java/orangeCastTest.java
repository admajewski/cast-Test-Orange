import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;

public class orangeCastTest {
    private static AppiumDriver appiumDriver;
    public static void main(String[] args) {
        //Initialization of config file
        File configFile = new File("config.properties");
        Properties properties = null;
        try{
            FileReader reader = new FileReader(configFile);
            properties = new Properties();
            properties.load(reader);

        } catch(FileNotFoundException e){
            System.out.println("Config file does not exist in this location: " + configFile.getAbsolutePath() + ". Aborting...");
            System.exit(1);
        }catch(IOException r){
            r.printStackTrace();
        }
        //Configurable variables from config.properties
        String category = properties.getProperty("CATEGORY");
        String movie = properties.getProperty("MOVIE");
        // Server configuration for Appium server
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getProperty("PLATFORM_VERSION"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("DEVICE_NAME"));
        capabilities.setCapability(MobileCapabilityType.UDID, properties.getProperty("UDID"));
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        if(properties.getProperty("FULLRESET").equals("true"))
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        if(properties.getProperty("FULLRESET").equals("false"))
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        if(properties.getProperty("APPFILEPATH") != null)
            capabilities.setCapability(MobileCapabilityType.APP, properties.getProperty("APPFILEPATH"));
        capabilities.setCapability("appPackage", properties.getProperty("APPPACKAGE"));
        capabilities.setCapability("appActivity", properties.getProperty("APPACTIVITY"));
        capabilities.setCapability("appWaitActivity", properties.getProperty("APPWAITACTIVITY"));
        // Session initialization
        try {
            appiumDriver = new AppiumDriver(new URL(properties.getProperty("URL")), capabilities);
            System.out.println("Connected to Appium Server!");
        } catch (MalformedURLException e) {
            System.out.println("The URL isn't correct. Check the settings");
            System.exit(1);

        } catch (SessionNotCreatedException r){
             r.printStackTrace();
        }
        // Web driver initialization
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(3));

        // Check if menu login element is present. If it is click button "Pomiń".
        // If it doesn't exists, go forward, since someone can be already logged in.
        if(ifExists("menu_login")) {
            appiumDriver.findElement(By.id("menu_login")).click();
            System.out.println("Clicked Pomin button.");
        }

        // Chceck if consent element is present. If it is click button "Zaczynamy"
        // If it doesn't exists, go forward, since someone could have given consent.
        if(ifExists("welcome_btn_start")) {
            appiumDriver.findElement(By.id("welcome_btn_start")).click();
            System.out.println("Clicked Zaczynamy button.");
        }
        // Click button "VOD"
        try {
            appiumDriver.findElement(By.id("main_btn_vod")).click();
            System.out.println("Clicked VOD button");
        }catch(NoSuchElementException e){
            System.out.println("Cannot find VOD button. Aborting...");
        }

        //Wait till Vod content box will be available
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("vod_tabs")));

        //Scroll the Vod category bar till you find a category
        System.out.println("Searching for: " + category);
        try {
            appiumDriver.findElement(new AppiumBy.ByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceIdMatches(\".*id/vod_tabs\").scrollable(true)).setAsHorizontalList()" +
                            ".scrollIntoView(UiSelector().textContains(\"" + category + "\"))"));
        }catch (NoSuchElementException e) {
            System.out.println("Cannot find the category: "+ category +". Aborting test.");
            System.exit(1);
        }
        // Click the category.
        try {
            appiumDriver.findElement(new AppiumBy.ByAccessibilityId("" + category + "")).click();
            System.out.println("Found " + category + ". Clicked it.");
        }catch(NoSuchElementException e){
            System.out.println("Cannot find the category: "+ category +". Aborting test.");
            System.exit(1);
        }
        // Find the movie in Vod content list.
        System.out.println("Searching for: " + movie);
        try {
            appiumDriver.findElement(new AppiumBy.ByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceIdMatches(\".*id/vod_tab_content\").scrollable(true))" +
                            ".scrollIntoView(UiSelector().textContains(\"" + movie + "\"))"));
        }catch (NoSuchElementException e){
            System.out.println("Cannot find the movie. Aborting test.");
            System.exit(1);
        }
        // Find element with the movie name and click it
        System.out.println("Found: " + movie + " Clicking it now.");
        List<WebElement> elementsMovies = appiumDriver.findElements(By.id("vod_entry_scalable_name"));
        for (WebElement element : elementsMovies) {
            if (element.getText().equals(movie)) {
                element.click();
                break;
            }
        }

        // Scroll to the "Obsada" element
        System.out.println("Searching for obsada element.");
        appiumDriver.findElement(new AppiumBy.ByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(UiSelector().resourceIdMatches(\".*id/actor_name\"))"));
        System.out.println("Gathering list of actors");
        // Collect all Actor's names and list it out in console.
           WebElement listActorsMove = appiumDriver.findElement(By.id("vod_details_actors_list"));
           Actions actions = new Actions(appiumDriver);
        boolean endOfTheList = false;
        List<String> listCast = new ArrayList<>();
        System.out.println("Cast members:");
        do {
            List<WebElement> elementsCast = appiumDriver.findElements(By.id("actor_name"));
            for (WebElement element : elementsCast) {
                if (listCast.isEmpty()) {

                    listCast.add(element.getText());
                    System.out.println(element.getText());
                }
                if (!(listCast.get(listCast.size() - 1).equals(elementsCast.get(elementsCast.size()-1).getText()))) {
                    if (!listCast.contains(element.getText())) {
                        listCast.add(element.getText());
                        System.out.println(element.getText());
                    }
                }else{
                    endOfTheList=true;
                    break;
                }
            }
                actions.dragAndDropBy(listActorsMove, -500, 0).perform();
        }while(!endOfTheList);

        System.out.println("Test concluded. Exiting the app.");
        //End the test
        appiumDriver.quit();
}
// Function checks if element with give id exists, if not it gives false nad if yes it gives true.
    static boolean ifExists (String id){
        try{
            appiumDriver.findElement(By.id(id));
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
