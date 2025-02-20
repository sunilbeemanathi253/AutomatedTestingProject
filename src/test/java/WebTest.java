import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;
import org.junit.Test;
import java.time.Duration;

public class WebTest {

    @Test
    public void testECommercePurchase() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String[] items = {"add-to-cart-sauce-labs-backpack", "add-to-cart-sauce-labs-bike-light",
                          "add-to-cart-sauce-labs-bolt-t-shirt", "add-to-cart-sauce-labs-fleece-jacket"};

        for (String item : items) {
            driver.findElement(By.id(item)).click();
        }

        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        String itemTotal = driver.findElement(By.className("summary_subtotal_label")).getText();
        String tax = driver.findElement(By.className("summary_tax_label")).getText();
        String total = driver.findElement(By.className("summary_total_label")).getText();

        System.out.println("Item Total: " + itemTotal);
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);

        driver.findElement(By.id("finish")).click();

        String successMessage = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals("THANK YOU FOR YOUR ORDER", successMessage);
        System.out.println("Order Completed Successfully!");

        driver.quit();
    }
}
