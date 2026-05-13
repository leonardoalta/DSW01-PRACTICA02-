package com.ejercicio3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_access_config")
public class MasterAccessConfig {

    @Id
    @Column(nullable = false, updatable = false, length = 64)
    private String id;

    @Column(nullable = false, length = 150)
    private String masterPrincipal;

    @Column(nullable = false)
    private boolean enabled = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterPrincipal() {
        return masterPrincipal;
    }

    public void setMasterPrincipal(String masterPrincipal) {
        this.masterPrincipal = masterPrincipal;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}