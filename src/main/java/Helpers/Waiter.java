package Helpers;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Waiter {

    private static final int TIMEOUT = 10;

    public static void waitForVisibility(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClick(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForDisappearance(WebDriver driver, WebElement element) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void fluentWait(WebDriver driver, WebElement element) {
        Wait wait = new FluentWait(driver)
                .withTimeout(TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class);
    }


}