import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class UpdateMailsCms {

    public static void search() {
        switchTo().frame("AdminMenu");
        $(By.xpath("//a[contains(text(),'Search User/Group')]")).click();
        switchTo().defaultContent();
        switchTo().frame("FullRegion_InfoFrame");
        $(By.xpath("//input[@id='FullRegion_MainRegion_Email']")).setValue("testeu01_haha@yopmail.com");
        $(By.xpath("//input[@id='FullRegion_MainRegion_PagingSize']")).setValue("1000").pressEnter();
    }

    @Test
    public static void main() {
        WebDriverManager.chromedriver().version("73");
        Configuration.startMaximized = true;
        open("http://dev.haldexcommerce.ddcloud.se");
        $("#openLoginPopup").click();
        $("#UserName").setValue("DDadminEU");
        $("#UserPassword").setValue("maddogs2019");
        $("#login-popup-submit").click();
        sleep(1000);
        open("http://dev.haldexcommerce.ddcloud.se/EPiServer/CMS/Admin/Default.aspx");
        sleep(1000);
        search();
        int size = $$(By.tagName("tr")).size();
        System.out.println(size);
        for (int i = 0; i < 81; i++) {
            $(By.xpath("/html[1]/body[1]/form[1]/div[3]/div[3]/table[1]/tbody[1]/tr[2]/td[2]/a[1]")).click();
            $(By.xpath("//input[@id='FullRegion_MainRegion_UserMembership_InnerControl_Email']")).setValue("haldextest@yopmail.com");
            $(By.xpath("//input[@id='FullRegion_MainRegion_UserMembership_InnerControl_ApplyButton']")).click();
            switchTo().defaultContent();
            search();
        }
    }
}
