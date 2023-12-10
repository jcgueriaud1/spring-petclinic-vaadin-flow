package org.springframework.samples.petclinic.ui;

import java.util.Optional;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import org.springframework.samples.petclinic.ui.view.ErrorView;
import org.springframework.samples.petclinic.ui.view.WelcomeView;
import org.springframework.samples.petclinic.ui.view.owner.OwnersFindView;
import org.springframework.samples.petclinic.ui.view.vet.VetsView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

	private final Nav menu;

	public MainLayout() {
		menu = createMenu();
		addToNavbar(createHeaderContent());
	}

	private Component createHeaderContent() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.getThemeList().add("dark");
		layout.setPadding(true);
		layout.setSpacing(false);
		layout.setWidthFull();
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.setJustifyContentMode(JustifyContentMode.BETWEEN);

		final Span brand = new Span();
		final Anchor brandLink = new Anchor("/", brand);
		//<accessibility-plugin-aria-label>
		brandLink.setAriaLabel("Go to the homepage");
		brandLink.addClassName("navbar-brand");
		layout.add(brandLink);

		layout.add(menu);

		Header header = new Header(layout);
		header.setId("header");
		return header;
	}


	private Nav createMenu() {
		Nav nav = new Nav();
		nav.addClassName("nav-top");
		nav.add(
			createRouterLink("home", VaadinIcon.HOME, WelcomeView.class),
			createRouterLink("findOwners", VaadinIcon.SEARCH, OwnersFindView.class),
			createRouterLink("veterinarians", VaadinIcon.LIST, VetsView.class),
			createRouterLink("error", VaadinIcon.WARNING, ErrorView.class)
		);
		return nav;
	}

	private Tab[] createMenuItems() {
		return new Tab[] {
				new Tab(createRouterLink("home", VaadinIcon.HOME, WelcomeView.class)),
				new Tab(createRouterLink("findOwners", VaadinIcon.SEARCH, OwnersFindView.class)),
				new Tab(createRouterLink("veterinarians", VaadinIcon.LIST, VetsView.class)),
				new Tab(createRouterLink("error", VaadinIcon.WARNING, ErrorView.class))
		};
	}

	private RouterLink createRouterLink(String translationKey, VaadinIcon viewIcon,
			Class<? extends Component> navigationTarget) {
		final RouterLink routerLink =
				new RouterLink(getTranslation(translationKey), navigationTarget);
		routerLink.addComponentAsFirst(viewIcon.create());
		routerLink.addClassNames("flex", "gap-s", "uppercase");
		return routerLink;
	}


}
