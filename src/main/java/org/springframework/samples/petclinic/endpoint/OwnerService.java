package org.springframework.samples.petclinic.endpoint;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerName;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;
import org.springframework.samples.petclinic.backend.owner.Pet;
import org.springframework.samples.petclinic.backend.visit.VisitRepository;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Nonnull;
import com.vaadin.hilla.Nullable;

@BrowserCallable
@AnonymousAllowed
public class OwnerService {

	private final OwnerRepository ownerRepository;
	private final VisitRepository visitRepository;

	public OwnerService(OwnerRepository ownerRepository, VisitRepository visitRepository) {
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
	}

	private OwnerRepository getRepository() {
		return ownerRepository;
	}

	public @Nonnull Page<@Nonnull Owner> findByLastName(String lastName, Pageable pageable) {
		return getRepository().findByLastName(lastName, pageable);
	}
	public @Nonnull int countByLastName(@Nonnull String lastName) {
		return getRepository().countByLastName(lastName);
	}

	public @Nonnull OwnerName findPersonById(@Nonnull Integer id) {
		Optional<OwnerName> personById = getRepository().findPersonById(id);
		return personById.get();
	}

	public @Nonnull Owner findOwner(@Nonnull Integer ownerId) {
		Owner owner = getRepository().findById(ownerId).get();
		for (Pet pet : owner.getPets()) {
			visitRepository.findByPetId(pet.getId()).forEach(pet::addVisit);
		}

		return owner;
	}

	public @Nullable Owner save(@Nonnull Owner value) {
		return getRepository().save(value);
	}

	public Optional<Owner> get(Integer petId) {
		return getRepository().findById(petId);
	}
}
