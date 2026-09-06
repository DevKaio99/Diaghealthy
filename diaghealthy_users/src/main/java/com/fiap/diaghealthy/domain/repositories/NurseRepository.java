package com.fiap.diaghealthy.domain.repositories;

import com.fiap.diaghealthy.domain.entities.Nurse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NurseRepository {
    Nurse save (Nurse nurse);
    boolean validateEmailExists (String email);
    List<Nurse> nurseList();
    Optional<Nurse> findNurseById (UUID id);
    Nurse updateNurse (Nurse nurse);
    Optional <Nurse> findByEmailIgnoreCase (String email);
    boolean findNurseByCoren(String coren);
}
