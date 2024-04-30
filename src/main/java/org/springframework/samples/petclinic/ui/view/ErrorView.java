package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "error",icon = "vaadin:warning")
@Route(value = "oups")
public class ErrorView extends VerticalLayout {

	public ErrorView() {
		// Throw a server exception, so as ErrorHandlerView is displayed.
		throw new RuntimeException("Expected: controller used to showcase what happens when an exception is thrown");
	}

}
