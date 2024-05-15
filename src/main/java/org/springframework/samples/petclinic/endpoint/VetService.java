package org.springframework.samples.petclinic.endpoint;

import java.util.Collection;
import java.util.List;

import org.springframework.samples.petclinic.backend.vet.Vet;
import org.springframework.samples.petclinic.backend.vet.VetRepository;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Nonnull;
import com.vaadin.hilla.crud.ListRepositoryService;

@BrowserCallable
@AnonymousAllowed
public class VetService
        extends ListRepositoryService<Vet, Integer, VetRepository> {

	public @Nonnull Collection<@Nonnull Vet> findAllVets() {
		return getRepository().findAll();
	}
}
