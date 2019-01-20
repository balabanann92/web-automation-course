import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertThat;

public class GoogleTest {

    public static ChromeDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testSearchSeleniumWebDriver(){
        //Given
        driver.get("https://google.com");
        driver.findElementByName("q").sendKeys("Selenium WebDriver" +  "\n");

        //When
        ArrayList<String> titles = new ArrayList();
        int maxElementsNumber = 5;
        for (int position=1; position < maxElementsNumber; position++){
            titles.add(findSearchResultTitleByPostion(position, driver));
        }

        //Then
        Assert.assertEquals(titles.size() + 1, maxElementsNumber);
        for (String title : titles) {
            assertThat(title, anyOf(containsString("selenium"), containsString("Selenium")));
        }
    }

    private String findSearchResultTitleByPostion(int position, ChromeDriver driver) {
        String elementSelector = "#rso > div > div > div:nth-child(" + position + ") > div > div > div.r > a:nth-child(1) > h3";
        return driver.findElementByCssSelector(elementSelector).getText();
    }
}
