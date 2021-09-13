package com.prestashop;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {
    WebDriver driver;
    WebDriverWait wait;

    public void driverInitialisation() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 1);

        driver.get("https://demo.prestashop.com/");
        Thread.sleep(5000);
    }

    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod
    public void beforeTest() throws InterruptedException {
        driverInitialisation();
    }

    @AfterMethod
    public void afterTest() {
        cleanup();
    }

    @Test
    public void verifyTitle() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//footer[@id='footer']")));

        String title = driver.getTitle();
        Assert.assertEquals(title, "PrestaShop Live Demo");
    }

    @Test
    public void login() throws InterruptedException {
        WebElement hideButton = driver.findElement(By.xpath("//span[text()='Hide']"));
        hideButton.click();

        driver.switchTo().frame(0);

        WebElement signInButton = driver.findElement(By.xpath("//span[text()='Sign in']"));
        signInButton.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@class='form-control']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@class='form-control js-child-focus js-visible-password']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit-login']"));

        emailInput.sendKeys("fortuna1919@gmail.com", Keys.TAB);
        passwordInput.sendKeys("strong19");
        submitButton.click();

        Thread.sleep(5000);

        WebElement userName = driver.findElement(By.xpath("//span[text()='Oleksandr Rozhok']"));

        Assert.assertEquals(userName.getText(), "Oleksandr Rozhok");
    }

    @Test
    public void addFirstItemToCart() {
        WebElement hideButton = driver.findElement(By.xpath("//span[text()='Hide']"));
        hideButton.click();

        driver.switchTo().frame(0);

        List<WebElement> productItems = driver.findElements(By.xpath("//div[@itemprop='itemListElement']"));
        WebElement itemName = productItems.get(0).findElement(By.xpath("//h3[@itemprop='name']/a"));
        String itemNameMainPage = itemName.getText();
        productItems.get(0).click();

        WebElement addToCartButton = driver.findElement(By.xpath("//button[@data-button-action='add-to-cart']"));
        addToCartButton.click();

        WebElement proceedToCheckoutButton = driver.findElement(By.xpath("//a[text()='Proceed to checkout']"));
        proceedToCheckoutButton.click();

        WebElement shoppingCartTitle = driver.findElement(By.xpath("//h1[text()='Shopping Cart']"));
        Assert.assertEquals(shoppingCartTitle.getText().toUpperCase(), "SHOPPING CART");

        List<WebElement> itemsInCart = driver.findElements(By.xpath("//li[@class='cart-item']"));
        WebElement itemInCartName = itemsInCart.get(0).findElement(By.xpath("//a[@class='label']"));
        Assert.assertEquals(itemInCartName.getText().toLowerCase(), itemNameMainPage.toLowerCase());
    }
}

