package org.springframework.samples.petclinic.playwright;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.samples.petclinic.service.DataService;
import org.springframework.samples.petclinic.service.FileService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteParameters;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AxeTestFixtures extends TestFixtures {
	// This will be injected with the random free port
	// number that was allocated
	@LocalServerPort
	int port;

	final DataService dataService = new FileService();
  AxeBuilder makeAxeBuilder() {
    return new AxeBuilder(page)
		.withTags(List.of("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
	  .exclude("vaadin-dev-tools");
  }

	public <T extends Component> void navigate(Class<T> navigationTarget,
													  RouteParameters parameters) {
		RouteConfiguration configuration = RouteConfiguration
			.forRegistry(UI.getCurrent().getInternals().getRouter().getRegistry());
		String url = configuration.getUrl(navigationTarget, parameters);
		page.navigate("http://localhost:" + port + "/" + url);
	}

	AxeResults runA11yTest(String pageId, String url) {
		page.navigate(url).finished();
		// find a different workaround
		assertThat(page.locator("//h1")).isVisible();

		AxeResults accessibilityScanResults = new AxeBuilder(page)
			.exclude("vaadin-dev-tools")
			.exclude("vaadin-connection-indicator")
			.analyze();
		dataService.saveA11yResults(pageId, accessibilityScanResults);
		return accessibilityScanResults;
	}
}
