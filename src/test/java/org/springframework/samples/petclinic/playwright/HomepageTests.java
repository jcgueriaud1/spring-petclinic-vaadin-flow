package org.springframework.samples.petclinic.playwright;

import java.util.Collections;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("playwright")
public class HomepageTests extends AxeTestFixtures{

	@Test
	void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() throws Exception {
		page.navigate("http://localhost:" + port + "/");
		assertThat(page.locator("//h1")).containsText("Welcome");
		assertEquals("A Spring Framework demonstration - PetClinic", page.title());

		AxeResults accessibilityScanResults = new AxeBuilder(page)
			.exclude("vaadin-dev-tools")
			.exclude("vaadin-connection-indicator")
			.analyze();

		dataService.saveA11yResults("homepage", accessibilityScanResults);
		assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
	}
}
