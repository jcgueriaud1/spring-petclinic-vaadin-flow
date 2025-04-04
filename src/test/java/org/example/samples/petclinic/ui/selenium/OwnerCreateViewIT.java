package org.example.samples.petclinic.ui.selenium;

import java.time.Duration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("selenium")
public class OwnerCreateViewIT extends BaseSeleniumIT {

	@Override
	public String getView() {
		return "owners/new";
	}

	@Test
	public void testH2Title() {
		var title = driver.findElement(By.tagName("h2"));
		assertEquals("Owner", title.getText());
	}

	@Test
	public void testCreateOwner() {
		fill(getTextFieldByLabel("Last Name"), "Doe");
		fill(getTextFieldByLabel("First Name"), "John");
		fill(getTextFieldByLabel("Address"), "Address");
		fill(getTextFieldByLabel("City"), "Turku");
		fill(getTextFieldByLabel("Telephone"), "044444444");

		click(getButtonByText("Add Owner"));

		By locator = By.xpath(String.format("//h2[normalize-space(text())='%s']", "Owner Information"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement title = wait.until(
			ExpectedConditions.presenceOfElementLocated(locator));

		assertEquals("Owner Information", title.getText());
	}

	@Test
	public void testInvalidTelephone() {
		fill(getTextFieldByLabel("Last Name"), "Doe");
		fill(getTextFieldByLabel("First Name"), "John");
		fill(getTextFieldByLabel("Address"), "Address");
		fill(getTextFieldByLabel("City"), "Turku");
		WebElement telephoneField = getTextFieldByLabel("Telephone");
		fill(telephoneField, "0444444444444");

		click(getButtonByText("Add Owner"));

		By locator = By.xpath(String.format("//div[normalize-space(text())='%s']", "Telephone must have 1 to 10 digits"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorMessage = wait.until(
			ExpectedConditions.presenceOfElementLocated(locator));
		assertEquals("", telephoneField.getDomAttribute("invalid"));
	}
}
