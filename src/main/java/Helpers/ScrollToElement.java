package Helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollToElement {

    public static void scrollTo(WebDriver driver, WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Waiter.waitForVisibility(driver, element);
    }

    public static void scrollToTheBottom(WebDriver driver){
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollWheel(WebDriver driver, WebElement element){
//        JavascriptExecutor jse = (JavascriptExecutor) driver;
//        jse.executeScript("window.scrollTo(0,document.body.scrollHeight + 900);");
    }
}

