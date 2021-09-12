import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        //
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 1);
//        WebElement input = driver.findElement(By.xpath("//input[@name='q']"));
//        input.sendKeys("Java", Keys.ENTER);
        try {
            driver.get("https://demo.prestashop.com/");
            Thread.sleep(5000);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//footer[@id='footer']")));

            String title = driver.getTitle();
            Assert.assertEquals(title, "PrestaShop Live Demo");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
