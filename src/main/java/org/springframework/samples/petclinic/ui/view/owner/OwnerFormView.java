package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.ui.util.FormUtil;

public abstract class OwnerFormView extends VerticalLayout {

    protected final Binder<Owner> binder = new Binder<>(Owner.class);

    OwnerFormView(OwnerFormPresenter<? extends OwnerFormView> presenter) {
        setWidthFull();

        TextField firstNameTextField = new TextField(getTranslation("firstName"));
		TextField lastNameTextField = new TextField(getTranslation("lastName"));
		TextField addressTextField = new TextField(getTranslation("address"));
		TextField cityTextField = new TextField(getTranslation("city"));
		TextField telephoneTextField = new TextField(getTranslation("telephone"));
        binder.forField(firstNameTextField).asRequired().bind(Owner::getFirstName,
                Owner::setFirstName);
        binder.forField(lastNameTextField).asRequired().bind(Owner::getLastName,
                Owner::setLastName);
        binder.forField(addressTextField).asRequired().bind(Owner::getAddress, Owner::setAddress);
        binder.forField(cityTextField).asRequired().bind(Owner::getCity, Owner::setCity);
        binder.forField(telephoneTextField)
                .asRequired()
                .withValidator(new RegexpValidator(getTranslation("telephoneError"), "\\d{1,10}"))
                .bind(Owner::getTelephone, Owner::setTelephone);

		VerticalLayout ownerFormCol1 = new VerticalLayout();
		ownerFormCol1.setSpacing(false);
		ownerFormCol1.setPadding(false);
        ownerFormCol1.add(firstNameTextField, addressTextField, telephoneTextField);

		VerticalLayout ownerFormCol2 = new VerticalLayout();
		ownerFormCol2.setSpacing(false);
		ownerFormCol2.setPadding(false);
		ownerFormCol2.add(lastNameTextField, cityTextField);

        Button updateOwnerButton = new Button(actionButtonLabel());
        updateOwnerButton.addClickListener(this::actionButtonListener);

		HorizontalLayout horizontalLayout = new HorizontalLayout(ownerFormCol1, ownerFormCol2);
		add(new H2(getTranslation("owner")), horizontalLayout, updateOwnerButton);

        firstNameTextField.focus();
    }

    protected abstract String actionButtonLabel();

    protected abstract void actionButtonListener(ClickEvent<Button> event);

    public void init(Owner owner) {
        binder.readBean(owner);
    }

    public void navigateToOwnerDetails(Integer ownerId) {
        UI.getCurrent().navigate(OwnerDetailsView.class,
                new RouteParameters("ownerId", ownerId.toString()));
    }

}
