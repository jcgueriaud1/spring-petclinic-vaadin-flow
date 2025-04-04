package org.example.samples.petclinic.ui.view;

import org.example.samples.petclinic.ui.MainLayout;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Uses(MainLayout.class)
@Route(value = "", layout = MainContentLayout.class)
public class WelcomeView extends VerticalLayout implements HasDynamicTitle {

	public WelcomeView() {
		H2 h2 = new H2(getTranslation("welcome"));
		Image image = new Image("./images/pets.png", getTranslation("pets"));

		add(h2, image);

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	@Override
	public String getPageTitle() {
		return getTranslation("welcomeTitle");
	}

}
