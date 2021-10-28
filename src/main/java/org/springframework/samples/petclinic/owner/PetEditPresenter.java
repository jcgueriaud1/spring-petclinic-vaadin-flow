package org.springframework.samples.petclinic.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

@RouteScope
@SpringComponent
public class PetEditPresenter extends PetFormPresenter<PetEditView> {

    PetEditPresenter(OwnerRepository ownerRepository, PetRepository petRepository) {
        super(ownerRepository, petRepository);
    }

    @Override
    public void initModel(Integer ownerId, Integer petId) {
        Owner owner = ownerRepository.findById(ownerId);
        Pet pet = petRepository.findById(petId);

        model = new PetFormDto(ownerId, owner.getFirstName() + " " + owner.getLastName());
        model.setPetId(petId);
        model.setPetName(pet.getName());
        model.setPetBirthDate(pet.getBirthDate());
        model.setPetType(pet.getType());

        view.show(model);
    }

    @Override
    public void save() {
        Pet pet = petRepository.findById(model.getPetId());
        pet.setName(model.getPetName());
        pet.setBirthDate(model.getPetBirthDate());
        pet.setType(model.getPetType());

        petRepository.save(pet);

        view.navigateToOwnerDetails(model.getOwnerId());
    }

}
