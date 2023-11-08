package org.springframework.samples.petclinic.ui;

import java.util.Optional;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import org.springframework.samples.petclinic.ui.view.ErrorView;
import org.springframework.samples.petclinic.ui.view.WelcomeView;
import org.springframework.samples.petclinic.ui.view.owner.OwnersFindView;
import org.springframework.samples.petclinic.ui.view.vet.VetsView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout implements AfterNavigationObserver {

	private final SideNav menu;
	private Anchor skipToMainContent;

	public MainLayout() {
		menu = createMenu();
		addToNavbar(createHeaderContent());
	}

	private Component createHeaderContent() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.getThemeList().add("dark");
		layout.setPadding(true);
		layout.setSpacing(false);
		layout.setId("header");
		layout.setWidthFull();
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.setJustifyContentMode(JustifyContentMode.BETWEEN);

		skipToMainContent = new Anchor("", "Skip to main content");
		skipToMainContent.addClassName("link__skip");
		layout.add(skipToMainContent);
		final Span brand = new Span();
		final Anchor brandLink = new Anchor("/", brand);
		brandLink.addClassName("navbar-brand");
		brandLink.setAriaLabel("Go to the homepage");
		layout.add(brandLink);

		layout.add(menu);

		return new Header(layout);
	}

	private SideNav createMenu() {
		SideNav sideNav = new SideNav();
		sideNav.addItem(
			new SideNavItem(getTranslation("home"), WelcomeView.class, VaadinIcon.HOME.create()),
			new SideNavItem(getTranslation("findOwners"), OwnersFindView.class, VaadinIcon.SEARCH.create()),
			new SideNavItem(getTranslation("veterinarians"), VetsView.class, VaadinIcon.LIST.create()),
			new SideNavItem(getTranslation("error"), ErrorView.class, VaadinIcon.WARNING.create())
		);
		return sideNav;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		skipToMainContent.setHref(event.getLocation().getPath() + "#main-content");
	}
}
