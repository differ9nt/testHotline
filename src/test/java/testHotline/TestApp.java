package testHotline;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestApp {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://hotline.ua/");


    }

    @Test
    public void search() throws IOException, InterruptedException {

        System.out.println("Title: " + driver.getTitle());
        
        driver.findElement(By.id("searchbox")).sendKeys("iPhone 7");
        driver.findElement(By.id("doSearch")).click();
        driver.findElement(By.xpath("//a[contains(text(),'iPhone')]")).click();

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenShotIphone.png"));

        driver.findElement(By.xpath("//li[@data-id='prices']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Купить на HOTLINE')]")).click();

        WebElement countiPhone = driver.findElement(By.xpath("//span[@class='counter-plus']"));
        int count = 0;
        for (int i = 1; i < 3; i++) {
            countiPhone.click();
            count++;
        }

        driver.findElement(By.xpath("//a[@href='/login/']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Авторизуйтесь')]")).click();
        driver.findElement(By.xpath("//div[@class='active']//input[@placeholder='E-mail']")).sendKeys("differ9nt.ey9s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Пароль']")).sendKeys("ryyqj");
        driver.findElement(By.xpath("//input[@value='Войти']")).click();

        System.out.println("Browser logs: ");
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        for (LogEntry logEntry : logEntries) {
            System.out.println(logEntry.getMessage());
        }

    }

    @AfterClass
    public static void TearDown() {
        driver.quit();
    }
}
