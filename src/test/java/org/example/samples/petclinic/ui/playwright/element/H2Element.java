package org.example.samples.petclinic.ui.playwright.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

@PlaywrightElement("H2")
public class H2Element extends VaadinElement {

	public H2Element(Locator locator, Page page) {
		super(locator, page);
	}
}
