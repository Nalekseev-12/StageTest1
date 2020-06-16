import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.proxy.auth.AuthType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MaasStage {




    @Test
    public void proxy() throws IOException {
        String maasUser = "test";
        String maasPassword = "test";

        String phone = "test";
        String password = "test";

        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.autoAuthorization("https://stage.marketolog.mts.ru", maasUser, maasPassword, AuthType.BASIC);
        proxy.start(4444);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nalekseev\\Downloads\\chromedriver1.exe");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("proxy", seleniumProxy);
        options.addArguments("--start-maximized");


        WebDriver driver = new ChromeDriver(options);



        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar("stage");

        URL url = new URL("https://stage.marketolog.mts.ru");
        String connectionUrl = url.getProtocol() + "://" + "maas" + ":" + "ahRoo4ah" + "@" + url.getAuthority() + url.getPath();
        driver.get(connectionUrl);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement closeButton = driver.findElement(By.xpath("//*[@class='modal__close-button ng-tns-c29-1 ng-star-inserted']"));
        closeButton.click();
        WebElement login = driver.findElement(By.xpath("//*[text()[contains(.,' Войти ')]]"));
        login.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement inputPhone = driver.findElement(By.xpath("//*[@placeholder='Номер телефона']"));
        inputPhone.sendKeys(phone);
        WebElement login1 = driver.findElement(By.xpath("//*[@class='button__content']"));
        login1.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement inputPassword = driver.findElement(By.xpath("//*[@placeholder='Введите пароль']"));
        inputPassword.sendKeys(password);
        WebElement login2 = driver.findElement(By.xpath("//*[@class='button__content']"));
        login2.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement buttonAccept = driver.findElement(By.xpath("//*[@id='Accept']"));
        buttonAccept.click();



        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        WebElement buttonCreateCampaign = driver.findElement(By.xpath("//*[@class='button button-full-width button-primary-blue button-small']"));
        buttonCreateCampaign.click();
        WebElement buttonCreateScale = driver.findElement(By.xpath("//*[@data-uniq-name='Запуск кампании: Рассылки, баннеры, заявка на рекламу в соцсетях']"));
        buttonCreateScale.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement myAuditory = driver.findElement(By.xpath("//*[text()='Выберите аудиторию']"));
        myAuditory.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement auditoryAutotest = driver.findElement(By.xpath("//*[@data-uniq-name='ЛК - Мои аудитории: Автотест']"));
        auditoryAutotest.click();
        WebDriverWait wait = new WebDriverWait(driver, 10L, 125);
        WebElement coverage = driver.findElement(By.xpath("//*[@class='coverage-widget-value ng-star-inserted']"));
        wait.until(ExpectedConditions.textToBePresentInElement( coverage, "1"));
        WebElement nextStep = driver.findElement(By.xpath("//*[@data-uniq-name='Круглая кнопка Вперед при создании кампании']"));
        nextStep.click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement buttonSMS = driver.findElement(By.xpath("//*[@data-uniq-name='Кнопка для выбора флайта: SMS']"));
        buttonSMS.click();





        Har har = proxy.getHar();
        proxy.getHar().writeTo(new FileWriter("Stage.har", true));

        proxy.stop();


    }

}
