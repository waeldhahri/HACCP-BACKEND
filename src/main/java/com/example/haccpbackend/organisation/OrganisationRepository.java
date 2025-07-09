package com.example.haccpbackend.organisation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {



    Optional<List<Organisation>> findByNameIgnoreCase(String name);




}
