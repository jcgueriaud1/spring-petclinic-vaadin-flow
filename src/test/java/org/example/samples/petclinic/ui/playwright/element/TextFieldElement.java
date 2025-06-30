package org.example.samples.petclinic.ui.playwright.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

@PlaywrightElement("vaadin-text-field")
public class TextFieldElement extends VaadinElement {

	public TextFieldElement(Locator locator, Page page) {
		super(locator, page);
	}
	/** Set value via JavaScript (ensures the `value-changed` event is triggered) */
	public void setValue(String value) {
		locator.evaluate("(el, val) => el.value = val", value);
		locator.dispatchEvent("change");
	}

	public String getValue() {
		return locator.evaluate("el => el.value").toString();
	}

	/** Clear the input field */
	public void clear() {
		locator.fill("");
		locator.dispatchEvent("change");
	}

	/** Focus the field */
	public void focus() {
		locator.focus();
	}

	/** Blur the field */
	public void blur() {
		locator.evaluate("el => el.blur()");
	}
}
