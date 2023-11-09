package org.springframework.samples.petclinic.ui.view.visit;

/**
 * @author jcgueriaud
 */
public interface VisitCreateViewInterface {
	void navigateToOwnerDetails(Integer ownerId);

	void show(VisitCreateDto visit);
}
