package com.dms.dmsapplication.parcels.repository;

import com.dms.dmsapplication.parcels.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Optional<Parcel> findByStudentId(long studentId);
}
