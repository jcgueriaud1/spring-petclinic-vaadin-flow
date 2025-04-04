package org.example.samples.petclinic.ui.testbench;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.example.samples.petclinic.ui.HasTestView;

import com.vaadin.testbench.BrowserTestBase;
import com.vaadin.testbench.DriverSupplier;
import com.vaadin.testbench.TestBench;

public class BaseTestBenchIT  extends BrowserTestBase implements HasTestView, DriverSupplier {

	@LocalServerPort
	private int port;

	@Override
	public WebDriver createDriver() {
		ChromeOptions options = new ChromeOptions();
		/*if (Boolean.getBoolean("com.vaadin.testbench.Parameters.headless")) {
			options.addArguments("--headless=new");
		}*/
		options.addArguments("--headless=new");
		return TestBench.createDriver(new ChromeDriver(options));
	}

	@Override
	public String getUrl() {
		return String.format("http://localhost:%d/", port);
	}

	@BeforeEach
	public void setup() throws Exception {
		// Open the application
		getDriver().get(getUrl() + getView());
	}

	@AfterEach
	public void afterEach() {
		getDriver().close();
	}
}
