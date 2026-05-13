package com.ejercicio3.service;

import com.ejercicio3.repository.MasterAccessConfigRepository;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class MasterAccessService {

    private static final String MASTER_CONFIG_ID = "MASTER";

    private final MasterAccessConfigRepository masterAccessConfigRepository;

    public MasterAccessService(MasterAccessConfigRepository masterAccessConfigRepository) {
        this.masterAccessConfigRepository = masterAccessConfigRepository;
    }

    public boolean isMaster(String principal) {
        if (principal == null || principal.isBlank()) {
            return false;
        }

        return masterAccessConfigRepository.findById(MASTER_CONFIG_ID)
                .filter(config -> config.isEnabled() && config.getMasterPrincipal() != null)
                .map(config -> config.getMasterPrincipal().trim().toLowerCase(Locale.ROOT)
                        .equals(principal.trim().toLowerCase(Locale.ROOT)))
                .orElse(false);
    }
}