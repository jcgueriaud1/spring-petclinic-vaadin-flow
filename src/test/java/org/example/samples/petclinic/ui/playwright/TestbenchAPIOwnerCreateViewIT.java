package org.example.samples.petclinic.ui.playwright;

import org.example.samples.petclinic.ui.playwright.element.ButtonElement;
import org.example.samples.petclinic.ui.playwright.element.H2Element;
import org.example.samples.petclinic.ui.playwright.element.TextFieldElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.vaadin.testbench.BrowserTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag("playwright")
public class TestbenchAPIOwnerCreateViewIT extends BasePlayWrightIT {

	@Override
	public String getView() {
		return "owners/new";
	}

	@Test
    public void testH2Title() {
		Assertions.assertEquals("Owner", vaadinQuery.$(H2Element.class).first().getText());
    }

	@Test
    public void testCreateOwner() {
		vaadinQuery.$(TextFieldElement.class).withLabel("First Name").single().setValue("John");
		vaadinQuery.$(TextFieldElement.class).withLabel("Last Name").single().setValue("Doe");
		vaadinQuery.$(TextFieldElement.class).withLabel("Address").single().setValue("Address");
		vaadinQuery.$(TextFieldElement.class).withLabel("City").single().setValue("Turku");
		vaadinQuery.$(TextFieldElement.class).withLabel("Telephone").single().setValue("044444444");
		vaadinQuery.$(ButtonElement.class).withText("Add Owner").single().click();

		assertThat(vaadinQuery.$(H2Element.class).first().getLocator()).hasText("Owner Information");

		Assertions.assertEquals("Owner Information", vaadinQuery.$(H2Element.class).first().getText());
	}

	@BrowserTest
	public void testInvalidTelephone() {
		vaadinQuery.$(TextFieldElement.class).withLabel("First Name").single().setValue("John");
		vaadinQuery.$(TextFieldElement.class).withLabel("Last Name").single().setValue("Doe");
		vaadinQuery.$(TextFieldElement.class).withLabel("Address").single().setValue("Address");
		vaadinQuery.$(TextFieldElement.class).withLabel("City").single().setValue("Turku");
		TextFieldElement telephoneField = vaadinQuery.$(TextFieldElement.class).withLabel("Telephone").single();
		telephoneField.setValue("0444444444444");
		vaadinQuery.$(ButtonElement.class).withText("Add Owner").single().click();
		Assertions.assertEquals("", telephoneField.getDomAttribute("invalid"));
		Assertions.assertEquals("Telephone must have 1 to 10 digits",
			telephoneField.getPropertyString("errorMessage"));
	}

}
