package com.ems.controller;

import com.ems.dto.request.EquipmentRequest;
import com.ems.dto.response.ApiResponse;
import com.ems.dto.response.EquipmentResponse;
import com.ems.entity.User;
import com.ems.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EquipmentResponse>> create(
            @Valid @RequestBody EquipmentRequest request,
            @AuthenticationPrincipal User currentUser) {
        EquipmentResponse response = equipmentService.create(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Equipment created successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<EquipmentResponse>>> findAll() {
        List<EquipmentResponse> response = equipmentService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Equipment list retrieved", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<EquipmentResponse>> findById(@PathVariable Long id) {
        EquipmentResponse response = equipmentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Equipment retrieved", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EquipmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentRequest request,
            @AuthenticationPrincipal User currentUser) {
        EquipmentResponse response = equipmentService.update(id, request, currentUser);
        return ResponseEntity.ok(ApiResponse.success("Equipment updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Equipment deleted successfully"));
    }
}
