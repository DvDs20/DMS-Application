package com.dms.dmsapplication.contracts.controllers;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.payload.request.NewContractCreationRequest;
import com.dms.dmsapplication.contracts.payload.response.ResponseForContractInfo;
import com.dms.dmsapplication.contracts.payload.response.ResponseForContractInfoForUser;
import com.dms.dmsapplication.contracts.repository.ContractsRepository;
import com.dms.dmsapplication.contracts.services.ContractsService;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ContractsController {

    private final ContractsService contractsService;

    private final ContractsRepository contractsRepository;

    public ContractsController(ContractsService contractsService, ContractsRepository contractsRepository) {
        this.contractsService = contractsService;
        this.contractsRepository = contractsRepository;
    }

    @GetMapping("/contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Contract> getAllContracts() {
        return contractsService.getAllContracts();
    }

    @PostMapping("/contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewContract(@RequestBody NewContractCreationRequest request) {
        contractsService.createNewContract(request.getStudentId(), request.getRoomId(), request.getExpireDate(),
                request.getPriceForStudent());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contracts/info/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseForContractInfo getContractInfo(@PathVariable(value = "id") long contractId) {
        return contractsService.getContractInfo(contractId);
    }

    @DeleteMapping("/contracts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteContract(@PathVariable(value = "id") long contractId) throws
            ResourceNotFoundException {
        contractsService.deleteContract(contractId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contracts/student-contract/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getContractInfoForStudent(@PathVariable(value = "id") long studentId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(contractsService.getContractInfoForStudent(studentId));
    }
}
