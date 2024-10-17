package beepkart.com;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class beepKart {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        WebDriver driver= new ChromeDriver(options);

        driver.get("https://www.beepkart.com/second-hand-bike/buy/bangalore");
        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String actTitle= driver.getTitle();
        String expTitle = "BeepKart - India's Most Trusted Place To Buy & Sell Used Bikes Online";

        if(actTitle.contains(expTitle)){
            System.out.println("Title is correct");
        }else{
            System.out.println("Title is incorrect");
        }

        WebElement bikecount = driver.findElement(By.xpath("//h1[contains(text(),'Bikes in Bangalore')]"));
        String AvailableBikes =bikecount.getText().trim();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<AvailableBikes.length(); i++){
            char ch= AvailableBikes.charAt(i);
            if (Character.isDigit(ch)){
                sb.append(ch);
            }
        }
        int extractBikeCount = Integer.parseInt(sb.toString());

        System.out.println(AvailableBikes);
        WebElement loadmorebtn = driver.findElement(By.xpath("//h6[text()='LOAD MORE BIKES']"));
        WebElement target = driver.findElement(By.xpath("//div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-3 css-1h77wgb']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", loadmorebtn);
        int size = 0;
        while(loadmorebtn.isDisplayed()){
            Thread.sleep(2000);
            loadmorebtn.click();
            List<WebElement> list = target.findElements(By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-lg-4 css-1gs20v']"));
            size = list.size();
            if(elementExists(driver, "//h6[text()='LOAD MORE BIKES']")){
                loadmorebtn = driver.findElement(By.xpath("//h6[text()='LOAD MORE BIKES']"));
            }
            else if(elementExists(driver, "//h6[text()='LOAD MORE BIKE']")){
                loadmorebtn = driver.findElement(By.xpath("//h6[text()='LOAD MORE BIKE']"));
            }
            else{
                break;
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", loadmorebtn);
            Thread.sleep(2000);
        }
        if(extractBikeCount==size){
            System.out.println(size+" is equals to "+extractBikeCount);
        }else{
            System.out.println("Total Count is Not Matched: Actual Count is: "+ extractBikeCount);
        }
        driver.quit();
    }

    public static Boolean elementExists(WebDriver driver, String xPath){
        List<WebElement> elements = driver.findElements(By.xpath(xPath));
        return !elements.isEmpty();

    }
}