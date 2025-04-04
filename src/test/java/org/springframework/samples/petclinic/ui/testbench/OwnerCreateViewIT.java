package org.springframework.samples.petclinic.ui.testbench;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.BrowserTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("testbench")
public class OwnerCreateViewIT extends BaseTestBenchIT {

	@Override
	public String getView() {
		return "owners/new";
	}

	@BrowserTest
	public void testH2Title() {
		Assertions.assertEquals("Owner", $(H2Element.class).first().getText());
	}

	@BrowserTest
	public void testCreateOwner() {
		$(TextFieldElement.class).withLabel("First Name").single().setValue("John");
		$(TextFieldElement.class).withLabel("Last Name").single().setValue("Doe");
		$(TextFieldElement.class).withLabel("Address").single().setValue("Address");
		$(TextFieldElement.class).withLabel("City").single().setValue("Turku");
		$(TextFieldElement.class).withLabel("Telephone").single().setValue("044444444");
		$(ButtonElement.class).withText("Add Owner").single().click();

		Assertions.assertEquals("Owner Information", $(H2Element.class).first().getText());
	}

	@BrowserTest
	public void testInvalidTelephone() {
		$(TextFieldElement.class).withLabel("First Name").single().setValue("John");
		$(TextFieldElement.class).withLabel("Last Name").single().setValue("Doe");
		$(TextFieldElement.class).withLabel("Address").single().setValue("Address");
		$(TextFieldElement.class).withLabel("City").single().setValue("Turku");
		TextFieldElement telephoneField = $(TextFieldElement.class).withLabel("Telephone").single();
		telephoneField.setValue("0444444444444");
		$(ButtonElement.class).withText("Add Owner").single().click();
		Assertions.assertEquals("", telephoneField.getDomAttribute("invalid"));
		Assertions.assertEquals("Telephone must have 1 to 10 digits",
			telephoneField.getPropertyString("errorMessage"));
	}
}
