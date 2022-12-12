package com.dms.dmsapplication.contracts.repository;

import com.dms.dmsapplication.contracts.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contract, Long> {

}
