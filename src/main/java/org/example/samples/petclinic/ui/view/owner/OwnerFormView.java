package org.example.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.RouteParameters;
import org.example.samples.petclinic.backend.owner.Owner;

public abstract class OwnerFormView extends VerticalLayout {

    protected final Binder<Owner> binder = new Binder<>(Owner.class);

    OwnerFormView(OwnerFormPresenter<? extends OwnerFormView> presenter) {
        setWidthFull();

        TextField firstNameTextField = new TextField(getTranslation("firstName"));
        binder.forField(firstNameTextField).asRequired().bind(Owner::getFirstName,
                Owner::setFirstName);

        TextField lastNameTextField = new TextField(getTranslation("lastName"));
        binder.forField(lastNameTextField).asRequired().bind(Owner::getLastName,
                Owner::setLastName);

        TextField addressTextField = new TextField(getTranslation("address"));
        binder.forField(addressTextField).asRequired().bind(Owner::getAddress, Owner::setAddress);

        TextField cityTextField = new TextField(getTranslation("city"));
        binder.forField(cityTextField).asRequired().bind(Owner::getCity, Owner::setCity);

        TextField telephoneTextField = new TextField(getTranslation("telephone"));
        binder.forField(telephoneTextField)
                .asRequired()
                .withValidator(new RegexpValidator(getTranslation("telephoneError"), "\\d{1,10}"))
                .bind(Owner::getTelephone, Owner::setTelephone);

        VerticalLayout ownerForm = new VerticalLayout();
		ownerForm.setPadding(false);
		ownerForm.setSpacing(false);
        ownerForm.setWidthFull();
		ownerForm.add(firstNameTextField, lastNameTextField, addressTextField, cityTextField, telephoneTextField);

        Button updateOwnerButton = new Button(actionButtonLabel());
        updateOwnerButton.addClickListener(this::actionButtonListener);

        add(new H2(getTranslation("owner")), ownerForm, updateOwnerButton);

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
