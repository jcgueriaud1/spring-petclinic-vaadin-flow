package org.springframework.samples.petclinic.ui.view;

import java.util.Objects;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.samples.petclinic.ui.MainLayout;

@ParentLayout(MainLayout.class)
@Route
public class MainContentLayout extends VerticalLayout implements RouterLayout {

	private final Main content = new Main();

	public MainContentLayout() {
		final Footer footer = new Footer(
				new Image("./images/vaadin.png", "Vaadin"),
				new Image("./images/spring-logo.svg", "Spring"));
		footer.addClassName("footer");

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		content.setSizeFull();
		content.setId("main-content");

		add(content, footer);
	}

	@Override
	public void showRouterLayoutContent(HasElement hasElement) {
		Objects.requireNonNull(hasElement);
		Objects.requireNonNull(hasElement.getElement());
		content.removeAll();
		content.getElement().appendChild(hasElement.getElement());
	}

}
