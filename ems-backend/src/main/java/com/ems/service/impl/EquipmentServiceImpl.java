package com.ems.service.impl;

import com.ems.dto.request.EquipmentRequest;
import com.ems.dto.response.EquipmentResponse;
import com.ems.entity.Equipment;
import com.ems.entity.User;
import com.ems.exception.ResourceNotFoundException;
import com.ems.repository.EquipmentRepository;
import com.ems.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Override
    @Transactional
    public EquipmentResponse create(EquipmentRequest request, User currentUser) {
        Equipment equipment = Equipment.builder()
                .name(request.getName())
                .type(request.getType())
                .status(request.getStatus())
                .purchaseDate(request.getPurchaseDate())
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();
        return toResponse(equipmentRepository.save(equipment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentResponse> findAll() {
        return equipmentRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EquipmentResponse findById(Long id) {
        return toResponse(getEquipmentOrThrow(id));
    }

    @Override
    @Transactional
    public EquipmentResponse update(Long id, EquipmentRequest request, User currentUser) {
        Equipment equipment = getEquipmentOrThrow(id);
        equipment.setName(request.getName());
        equipment.setType(request.getType());
        equipment.setStatus(request.getStatus());
        equipment.setPurchaseDate(request.getPurchaseDate());
        equipment.setUpdatedBy(currentUser);
        return toResponse(equipmentRepository.save(equipment));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Equipment equipment = getEquipmentOrThrow(id);
        equipment.setActive(false);
        equipmentRepository.save(equipment);
    }

    private Equipment getEquipmentOrThrow(Long id) {
        return equipmentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));
    }

    private EquipmentResponse toResponse(Equipment equipment) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .type(equipment.getType())
                .status(equipment.getStatus())
                .purchaseDate(equipment.getPurchaseDate())
                .createdAt(equipment.getCreatedAt())
                .updatedAt(equipment.getUpdatedAt())
                .createdBy(equipment.getCreatedBy().getFullName())
                .updatedBy(equipment.getUpdatedBy().getFullName())
                .active(equipment.getActive())
                .build();
    }
}
