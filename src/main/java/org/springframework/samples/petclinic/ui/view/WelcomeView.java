package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@RouteAlias(value = "", layout = MainContentLayout.class)
@Route(value = "home", layout = MainContentLayout.class)
public class WelcomeView extends VerticalLayout implements HasDynamicTitle {

	public WelcomeView() {
		H1 h1 = new H1(getTranslation("welcome"));
		Image image = new Image("./images/pets.png", null);
		add(h1, image);

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	@Override
	public String getPageTitle() {
		return getTranslation("welcomeTitle") + " " + getTranslation("websiteName");
	}

}
