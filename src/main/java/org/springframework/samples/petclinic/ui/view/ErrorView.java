package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Oops - PetClinic")
@Route(value = "oups", layout = MainContentLayout.class)
public class ErrorView extends VerticalLayout {

	public ErrorView() {
		// Throw a server exception, so as ErrorHandlerView is displayed.
		throw new RuntimeException("Expected: controller used to showcase what happens when an exception is thrown");
	}

}
