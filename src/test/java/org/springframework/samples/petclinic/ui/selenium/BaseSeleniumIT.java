package org.springframework.samples.petclinic.ui.selenium;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.samples.petclinic.ui.HasTestView;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BaseSeleniumIT implements HasTestView {

    @LocalServerPort
    private int port;

	@Override
    public String getUrl() {
        return String.format("http://localhost:%d/", port);
    }

	protected static WebDriver driver;

    @BeforeEach
    public void setupTest() throws Exception {
		driver = createDriver();
		driver.get(getUrl() + getView());
		new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
			.until(visibilityOfElementLocated(By.xpath("//h2")));
    }

    @AfterEach
    public void cleanupTest() {
		driver.close();
    }

    @AfterAll
    public static void cleanup() {
		driver.quit();
    }

    @BeforeAll
    public static void setup() {
		//WebDriverManager.chromedriver().clearDriverCache().setup();
		WebDriverManager.chromedriver().setup();
	}

	public WebDriver createDriver() {
		ChromeOptions options = new ChromeOptions();
		/*if (Boolean.getBoolean("com.vaadin.testbench.Parameters.headless")) {
			options.addArguments("--headless=new");
		}*/
		options.addArguments("--headless=new");
		return new ChromeDriver(options);
	}

	protected WebElement getTextFieldByLabel(String label) {
		WebElement element = driver.findElement(By.xpath(String.format("//vaadin-text-field[label[normalize-space(text())='%s']]", label)));
		assertTrue(element.isDisplayed());
		return element;
	}
	protected WebElement getButtonByText(String text) {
		WebElement element = driver.findElement(By.xpath(String.format("//vaadin-button[normalize-space(text())='%s']", text)));
		assertTrue(element.isDisplayed());
		return element;
	}
	protected void click(WebElement element) {
		element.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-indicator")));
	}

	protected void fill(WebElement element, String value) {
		element.sendKeys(value);
		//blur
		driver.findElement(By.tagName("body")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-indicator")));
	}
}
