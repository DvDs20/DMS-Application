package com.dms.dmsapplication.contracts.services;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.repository.ContractsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractsService {

    private final ContractsRepository contractsRepository;

    public ContractsService(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    public List<Contract> getAllContracts() {
        return contractsRepository.findAll();
    }

}
