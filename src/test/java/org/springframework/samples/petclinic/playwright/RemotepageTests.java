package org.springframework.samples.petclinic.playwright;

import java.util.Collections;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("playwright")
public class RemotepageTests extends AxeTestFixtures{

	@Test
	void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() throws Exception {
		page.navigate("https://test.anleggsregisteret.no/login");

		assertThat(page.locator("vaadin-login-overlay-wrapper")).containsText("Anleggsregisteret");

		AxeResults accessibilityScanResults = new AxeBuilder(page)
			.exclude("vaadin-dev-tools")
			.exclude("vaadin-connection-indicator")
			.analyze();

		dataService.saveA11yResults("backoffice-home", accessibilityScanResults);
		assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
	}
}
