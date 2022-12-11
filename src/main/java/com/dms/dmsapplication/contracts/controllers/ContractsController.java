package com.dms.dmsapplication.contracts.controllers;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.services.ContractsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ContractsController {

    private final ContractsService contractsService;

    public ContractsController(ContractsService contractsService) {
        this.contractsService = contractsService;
    }

    @GetMapping("/contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Contract> getAllContracts() {
        return contractsService.getAllContracts();
    }

}
