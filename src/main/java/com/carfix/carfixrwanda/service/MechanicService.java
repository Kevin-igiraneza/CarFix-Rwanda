package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.repository.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAllWithExistingUser();
    }

    public List<Mechanic> getVerifiedMechanics() {
        return mechanicRepository.findByVerificationStatusWithExistingUser(VerificationStatus.APPROVED);
    }

    /**
     * Public discovery: only approved mechanics, filtered by specialization / model / free-text keyword.
     */
    public List<Mechanic> searchVerifiedMechanics(String specialization, String vehicleModel, String keyword) {
        String spec = blankToNull(specialization);
        String model = blankToNull(vehicleModel);
        String key = blankToNull(keyword);

        return getVerifiedMechanics().stream()
                .filter(m -> spec == null || containsIgnoreCase(m.getSpecialization(), spec))
                .filter(m -> model == null || containsIgnoreCase(m.getSupportedVehicleModel(), model))
                .filter(m -> key == null || matchesKeyword(m, key))
                .toList();
    }

    public Optional<Mechanic> findByUserId(Long userId) {
        return mechanicRepository.findByUserIdWithExistingUser(userId);
    }

    public Optional<Mechanic> findById(Long id) {
        return mechanicRepository.findByIdWithExistingUser(id);
    }

    public Mechanic saveMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public void updateVerificationStatus(Long mechanicId, VerificationStatus status) {
        Mechanic mechanic = findById(mechanicId)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
        mechanic.setVerificationStatus(status);
        mechanicRepository.save(mechanic);
    }

    private static String blankToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static boolean containsIgnoreCase(String haystack, String needle) {
        if (haystack == null) {
            return false;
        }
        return haystack.toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT));
    }

    private static boolean matchesKeyword(Mechanic m, String key) {
        if (containsIgnoreCase(m.getSpecialization(), key)) {
            return true;
        }
        if (containsIgnoreCase(m.getSupportedVehicleModel(), key)) {
            return true;
        }
        if (containsIgnoreCase(m.getGarageLocation(), key)) {
            return true;
        }
        if (m.getUser() != null && m.getUser().getFullName() != null) {
            return containsIgnoreCase(m.getUser().getFullName(), key);
        }
        return false;
    }
}
