package org.springframework.samples.petclinic.endpoint;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import com.vaadin.hilla.crud.CrudRepositoryService;
import com.vaadin.hilla.crud.ListRepositoryService;

@BrowserCallable
@AnonymousAllowed
public class OwnerService
        extends CrudRepositoryService<Owner, Integer, OwnerRepository> {

	public @Nonnull Collection<@Nonnull Owner> findAllOwners() {
		return getRepository().findAll();
	}
	public @Nonnull Page<@Nonnull Owner> findByLastName(String lastName, Pageable pageable) {
		return getRepository().findByLastName(lastName, pageable);
	}
	public @Nonnull int countByLastName(@Nonnull String lastName) {
		return getRepository().countByLastName(lastName);
	}
}
