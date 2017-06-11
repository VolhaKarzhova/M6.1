package test;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;
    protected static final String URL = "https://mail.ru";
    protected static final String LOGIN = "volhakarzhova";
    protected static final String PASSWORD = "1584624Qwe";
    protected static final String ADDRESSEE = "volhakarzhova@mail.ru";
    protected static final String SUBJECT = "mail number: " + new Random().nextInt(5000);
    protected static final String MAILBODY = "This is a new email " + new Random().nextInt(5000);

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void shutDown() {
        driver.quit();
    }
}