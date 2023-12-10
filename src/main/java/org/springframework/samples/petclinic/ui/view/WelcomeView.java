package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.PageTitle;

@RouteAlias(value = "", layout = MainContentLayout.class)
@Route(value = "home", layout = MainContentLayout.class)
@PageTitle("Home - Petclinic")
public class WelcomeView extends VerticalLayout {

	public WelcomeView() {
		H2 h2 = new H2(getTranslation("welcome"));
		Image image = new Image("./images/pets.png", getTranslation("pets"));

		add(h2, image);

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

}
