import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
import java.time.Duration


class NeoBankTest {

    private lateinit var driver: WebDriver

    @BeforeTest
    fun setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Андрій\\IdeaProjects\\NEOB_test\\src\\test\\chromedriver.exe")
        driver = ChromeDriver()
    }


    @Test
    fun verifyPageTitle() {
        driver.get("https://web.neobank.one/")
        val pageTitle = driver.title
        Assert.assertEquals(pageTitle, "NEOBANK для бізнесу")


        val waitTillLoaderGone = WebDriverWait(driver, Duration.ofSeconds(10))
        waitTillLoaderGone.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gal")))
        val phoneNumberInput = driver.findElement(By.id("login"))
        phoneNumberInput.sendKeys("380630000000")


        val submitButton = driver.findElement(By.id("btnNext"))
        submitButton.submit()

        // З невідомих причин браузер розшириться на весь екран з 2го разу
        driver.manage().window().fullscreen()


        val textChange = WebDriverWait(driver, Duration.ofSeconds(10))
        textChange.until(ExpectedConditions.textToBe(By.className("auth-form-title"), "Відкриття бізнес-рахунку можливе тільки через NEOBANK для бізнесу"))
        // Цікава пасхалка, приречена на фейл тесту. У завданні відсутнє слово "додаток" =)

    }

    @AfterTest
    fun teardown() {
        driver.quit()
    }
}
