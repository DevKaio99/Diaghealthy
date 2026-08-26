package com.fiap.diaghealthy.domain.entities;

import com.fiap.diaghealthy.domain.enuns.Role;

public class Doctor extends User {

    protected String CRM;
    protected String speciality;


    public Doctor(String name, String email, String password, String CRM, String speciality) {
        super(name, email, password, Role.DOCTOR);
        this.CRM = CRM;
        this.speciality = speciality;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
