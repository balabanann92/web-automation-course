import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;

public class GoogleTestWithAnnotations {

    public static ChromeDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();;
        driver = new ChromeDriver();
    }

    @AfterTest
    public void closeDriver(){
            driver.close();
            driver.quit();
        }
    @Test
    public void testSearchSeleniumWebDriver() {
         //Given
         driver.get("https://google.com");
         driver.findElementByName("q").sendKeys("Selenium WebDriver" + "\n") ;
     //    driver.findElementByName("q").sendKeys(Keys.ENTER);

         //When
         ArrayList<String> titles = new ArrayList();
         int maxElementsNumber = 5;
         for (int position=1; position < maxElementsNumber; position++){
             titles.add(findSearchResultTitleByPosition(position, driver));
         }

         //Then
        Assert.assertEquals(titles.size() + 1, maxElementsNumber);
        for (String title : titles) {
            MatcherAssert.assertThat(title, containsString("Selenium"));
        }
     }

    private String findSearchResultTitleByPosition(int position, ChromeDriver driver) {
        String elementSelector = "div.g:nth-child(" + position + ")> div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > h3:nth-child(1)";
        return driver.findElementByCssSelector(elementSelector).getText();
    }
}
