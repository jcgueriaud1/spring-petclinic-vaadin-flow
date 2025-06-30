package org.example.samples.petclinic.ui.playwright.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

@PlaywrightElement("vaadin-button")
public class ButtonElement extends VaadinElement {

	public ButtonElement(Locator locator, Page page) {
		super(locator, page);
	}

	/** Check if the button is disabled */
	public boolean isDisabled() {
		return locator.evaluate("el => el.disabled").toString().equals("true");
	}

	/** Enable or disable the button (set the 'disabled' property) */
	public void setDisabled(boolean disabled) {
		setProperty("disabled", disabled);
	}

	/** Returns true if the button is currently visible */
	public boolean isVisible() {
		return locator.isVisible();
	}

	/** Returns true if the button is enabled */
	public boolean isEnabled() {
		return !isDisabled();
	}
}
