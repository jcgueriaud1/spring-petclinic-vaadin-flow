package org.example.samples.petclinic.ui.playwright.element;

import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.openqa.selenium.JavascriptExecutor;

import elemental.json.Json;
import elemental.json.JsonValue;

public abstract class VaadinElement {
	private boolean enableWaitForVaadin = true;
	// A hook for testing purposes
	private Runnable waitForVaadinLoopHook;
	String WAIT_FOR_VAADIN_SCRIPT =
		// @formatter:off
		"() => {"
			+ "if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.clients) {"
			+ "  var clients = window.Vaadin.Flow.clients;"
			+ "  for (var client in clients) {"
			+ "    if (clients[client].isActive()) {"
			+ "      return false;"
			+ "    }"
			+ "  }"
			+ "  return true;"
			+ "} else if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.devServerIsNotLoaded) {"
			+ "  return false;"
			+ "} else {"
			+ "  return true;"
			+ "}"
			+ "}";
	// @formatter:on
    protected final Locator locator;
	protected final Page page;

	public VaadinElement(Locator locator, Page page) {
        this.locator = locator;
		this.page = page;
    }

    public void click() {
        locator.click();
    }

    public String getText() {
		waitForVaadin(page);
        return locator.textContent();
    }

    public Locator getLocator() {
        return locator;
    }

	/** Set a DOM property (e.g. `value`, `disabled`, etc.) */
	public void setProperty(String name, Object value) {
		locator.evaluate("(el, args) => el[args.name] = args.value", Map.of("name", name, "value", value));
	}

	/** Optional: get a DOM property */
	public Object getProperty(String name) {
		return locator.evaluate("(el, args) => el[args.name]", Map.of("name", name));
	}

	public static void waitForVaadin(Page page) {
		page.waitForCondition(() -> {
			Boolean isVaadinReady = (Boolean) page.evaluate("() => {" +
				"if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.clients) {" +
				"    var clients = window.Vaadin.Flow.clients;" +
				"    for (var client in clients) {" +
				"        if (clients[client].isActive()) {" +
				"            return false;" +
				"        }" +
				"    }" +
				"    return true;" +
				"}" +
				"return false;" +
				"}");
			return Boolean.TRUE.equals(isVaadinReady);
		}, new Page.WaitForConditionOptions().setTimeout(30000)); // Adjust timeout as needed
	}
	public boolean isVisible() {
		return locator.isVisible();
	}

	public boolean isHidden() {
		return !isVisible();
	}

	public boolean isEnabled() {
		String disabled = getDomAttribute("disabled");
		return disabled == null || disabled.equals("false");
	}

	public boolean isDisabled() {
		return !isEnabled();
	}
	public String getDomAttribute(String name) {
		return locator.getAttribute(name);
	}
	public void focus() {
		locator.focus();
	}

	public void blur() {
		locator.blur();
	}

	public String getPropertyString(String propertyNames) {
		Object value = getProperty(propertyNames);
		if (value == null) {
			return null;
		}
		return createJsonValue(value).asString();
	}

	public Boolean getPropertyBoolean(String propertyNames) {
		Object value = getProperty(propertyNames);
		if (value == null) {
			return null;
		}
		return createJsonValue(value).asBoolean();
	}

	private JsonValue createJsonValue(Object value) {
		if (value == null) {
			return Json.createNull();
		} else if (value instanceof String) {
			return Json.create((String) value);
		} else if (value instanceof Number) {
			return Json.create(((Number) value).doubleValue());
		} else if (value instanceof Boolean) {
			return Json.create((Boolean) value);
		} else {
			throw new IllegalArgumentException(
				"Type of property is unsupported: "
					+ value.getClass().getName());
		}
	}

}
