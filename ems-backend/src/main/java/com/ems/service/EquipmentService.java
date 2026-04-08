package com.ems.service;

import com.ems.dto.request.EquipmentRequest;
import com.ems.dto.response.EquipmentResponse;
import com.ems.entity.User;

import java.util.List;

public interface EquipmentService {
    EquipmentResponse create(EquipmentRequest request, User currentUser);
    List<EquipmentResponse> findAll();
    EquipmentResponse findById(Long id);
    EquipmentResponse update(Long id, EquipmentRequest request, User currentUser);
    void delete(Long id);
}
