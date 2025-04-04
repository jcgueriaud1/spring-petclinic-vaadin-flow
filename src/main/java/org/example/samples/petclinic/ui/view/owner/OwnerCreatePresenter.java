package org.example.samples.petclinic.ui.view.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.example.samples.petclinic.backend.owner.Owner;
import org.example.samples.petclinic.backend.owner.OwnerRepository;

@SpringComponent
@RouteScope
public class OwnerCreatePresenter extends OwnerFormPresenter<OwnerCreateView> {

    OwnerCreatePresenter(OwnerRepository ownerRepository) {
        super(ownerRepository);
    }

    @Override
    public void initModel(Integer ownerId) {
        model = new Owner();
        view.init(model);
    }

}
