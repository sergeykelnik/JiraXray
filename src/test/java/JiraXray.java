import Helpers.ScrollToElement;
import Helpers.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Test
public class JiraXray {
    String preconditions = null;
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public int countNumberOfSteps(String numberOfSteps) {
        numberOfSteps = numberOfSteps.replaceAll("[^0-9.]", "");               //replace everything expect numbers and .

        System.out.println(numberOfSteps);

        int index = numberOfSteps.lastIndexOf('.');
        numberOfSteps = numberOfSteps.substring(0, index);
        System.out.println(numberOfSteps);

        numberOfSteps = numberOfSteps.substring(Math.max(numberOfSteps.length() - 4, 0));       //get last 4 characters from string
        System.out.println(numberOfSteps);

        numberOfSteps = numberOfSteps.substring(numberOfSteps.indexOf(".") + 1);                //get value between . and .
        numberOfSteps = numberOfSteps.substring(0, numberOfSteps.indexOf("."));                 //get value between . and .

        return (Integer.valueOf(numberOfSteps));
    }

    public String removePreconditionFromTestSteps(String testSteps) {
        preconditions = testSteps.substring(0, testSteps.indexOf("1.")); //get preconditions
        testSteps = testSteps.substring(testSteps.indexOf("1.") + 0);
        testSteps.trim();
        return (testSteps);
    }

    public void moveToXrayFields(int numberOfSteps, String[] steps, String[] expectedResults) throws InterruptedException {
        ScrollToElement scrollToElement = new ScrollToElement();
        Waiter waiter = new Waiter();

        driver.switchTo().frame(0);

        scrollToElement.scrollTo(driver, driver.findElement(By.id("content-new-action")));
        driver.findElement(By.xpath("//*[@id=\"manual-steps-table\"]/tbody[1]/tr[1]/td[4]")).click();
        /*//driver.findElement(By.xpath("//*[@id=\"manual-steps-table\"]/tbody[1]/tr[1]/td[4]")).sendKeys(preconditions);
        //driver.findElement(By.id("content-1-data")).click();*/
        driver.findElement(By.id("content-1-data")).sendKeys(preconditions);
        driver.findElement(By.id("content-new-action")).click();
        driver.findElement(By.id("content-new-action")).sendKeys(steps[0]);
        driver.findElement(By.id("content-new-result")).click();
        driver.findElement(By.id("content-new-result")).sendKeys(expectedResults[0]);
        driver.findElement(By.xpath("//button[@title='Update']")).click();

        for (int j = 1; j < numberOfSteps; j++) {

            for (int c = 0; c < 10; c++) {
                if (driver.findElements(By.xpath("//div[@class='index']")).size() == j)
                    break;
                else Thread.sleep(300);
            }

            scrollToElement.scrollTo(driver, driver.findElement(By.id("content-new-action")));

            driver.findElement(By.id("content-new-action")).click();
            driver.findElement(By.id("content-new-action")).sendKeys(steps[j]);

            driver.findElement(By.id("content-new-result")).click();
            driver.findElement(By.id("content-new-result")).sendKeys(expectedResults[j]);

            driver.findElement(By.xpath("//button[@title='Add']")).click();
        }

    }

    @Test
    public void addTestCases() throws InterruptedException {
        int firstNumber = 1174;      //763
        int lastNumber = 1349;      //1171

        String expectedResult = "";
        String testSteps = "";

        int numberOfSteps = 0;

        String URL = "https://dynamicdog.atlassian.net/browse/HTM-";

        String email = "sergey.kelnik@digitech7.by";

        driver.get("https://dynamicdog.atlassian.net/secure/RapidBoard.jspa?rapidView=1&projectKey=HE&view=planning.nodetail&selectedIssue=HE-5653");

        driver.findElement(By.id("username")).sendKeys(email);
        driver.findElement(By.id("username")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("password")).sendKeys(""); // remove
        driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

        Thread.sleep(10000);

        for (int i = firstNumber; i <= lastNumber; i++) {
            try {
                driver.get(URL + Integer.toString(i));

                expectedResult = driver.findElements(By.xpath("//div[@class='flooded']")).get(0).getText();
                testSteps = driver.findElements(By.xpath("//div[@class='flooded']")).get(2).getText();

//            numberOfSteps = numberOfSteps(expectedResult);

                testSteps = removePreconditionFromTestSteps(testSteps);

                String expectedResultArray[] = expectedResult.split("\\r?\\n");     //Split expected results by
                String testStepsArray[] = testSteps.split("\\r?\\n");

                numberOfSteps = expectedResultArray.length;

                for (int j = 0; j < expectedResultArray.length; j++) {
                    expectedResultArray[j] = expectedResultArray[j].replaceAll("[0-9]{1,2}[.] ", "");
                }

                for (int j = 0; j < expectedResultArray.length; j++) {
                    testStepsArray[j] = testStepsArray[j].replaceAll("[0-9]{1,2}[.] ", "");
                }

                moveToXrayFields(numberOfSteps, testStepsArray, expectedResultArray);
            } catch (Exception e) {
                System.out.println(i);
            }
        }

    }
}