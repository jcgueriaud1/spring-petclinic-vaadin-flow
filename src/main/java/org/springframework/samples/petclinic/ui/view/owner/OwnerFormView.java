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
        binder.forField(firstNameTextField).asRequired("First name is required").bind(Owner::getFirstName,
                Owner::setFirstName);
        binder.forField(lastNameTextField).asRequired("Last name is required").bind(Owner::getLastName,
                Owner::setLastName);
        binder.forField(addressTextField).asRequired("Address is required").bind(Owner::getAddress, Owner::setAddress);
        binder.forField(cityTextField).asRequired("City is required").bind(Owner::getCity, Owner::setCity);
        binder.forField(telephoneTextField)
                .asRequired("Phone is required")
                .withValidator(new RegexpValidator(getTranslation("telephoneError"), "\\d{1,10}"))
                .bind(Owner::getTelephone, Owner::setTelephone);

		telephoneTextField.setHelperText("Telephone format is only digits without space or separator (ex. 3582303039)");
        Button updateOwnerButton = new Button(actionButtonLabel());
        updateOwnerButton.addClickListener(this::actionButtonListener);

		FormLayout formLayout = new FormLayout(firstNameTextField, lastNameTextField, addressTextField, cityTextField, telephoneTextField);

		add(new H2(getTranslation("owner")), formLayout, updateOwnerButton);

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
