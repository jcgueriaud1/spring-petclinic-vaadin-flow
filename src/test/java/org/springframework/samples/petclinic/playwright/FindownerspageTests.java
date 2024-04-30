package org.springframework.samples.petclinic.playwright;

import com.deque.html.axecore.results.AxeResults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("playwright")
public class FindownerspageTests extends AxeTestFixtures {

	@Test
	void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() throws Exception {
		AxeResults accessibilityScanResults = runA11yTest("owners_find", "http://localhost:" + port + "/owners/find");
		assertTrue(accessibilityScanResults.violationFree());
	}
}
