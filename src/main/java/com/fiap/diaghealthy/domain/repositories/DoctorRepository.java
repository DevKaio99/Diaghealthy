package com.fiap.diaghealthy.domain.repositories;

import com.fiap.diaghealthy.domain.entities.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository {
    Doctor save (Doctor doctor);
    boolean validateEmailExists (String email);
    List <Doctor> doctorList();
    Optional <Doctor> findDoctorById (UUID id);
    Doctor updateDoctor (Doctor doctor);
    Optional <Doctor> findByEmailIgnoreCase (String email);
    boolean findDoctorByCRM(String crm);
}
