package com.ems.repository;

import com.ems.entity.Equipment;
import com.ems.enums.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByActiveTrue();
    Optional<Equipment> findByIdAndActiveTrue(Long id);
    List<Equipment> findByStatusAndActiveTrue(EquipmentStatus status);
}
