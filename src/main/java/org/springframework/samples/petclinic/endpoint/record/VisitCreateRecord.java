package org.springframework.samples.petclinic.endpoint.record;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.samples.petclinic.backend.visit.Visit;

public record VisitCreateRecord(
	Integer ownerId,
	Integer petId,
	String petName,
	LocalDate petBirthDate,
	String petType,
	String petOwner,
	@NotNull
	LocalDate visitDate,
	@NotEmpty
	String description,
	@NotNull
	List<VisitRecord> previousVisits) {
}
