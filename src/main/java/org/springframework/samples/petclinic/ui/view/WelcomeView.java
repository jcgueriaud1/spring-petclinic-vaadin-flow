package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route(value = "flow/welcome")
public class WelcomeView extends VerticalLayout implements HasDynamicTitle {

	public WelcomeView() {
		H2 h2 = new H2(getTranslation("welcome"));
		Image image = new Image("./images/pets.png", getTranslation("pets"));
		Anchor navigateToHilla = new Anchor("counter", "Navigate to a Hilla view");
		add(h2, image, navigateToHilla);

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	@Override
	public String getPageTitle() {
		return getTranslation("welcomeTitle");
	}

}
