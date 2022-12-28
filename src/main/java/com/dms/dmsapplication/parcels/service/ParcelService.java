package com.dms.dmsapplication.parcels.service;

import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.parcels.model.Parcel;
import com.dms.dmsapplication.parcels.payload.response.ParcelInfo;
import com.dms.dmsapplication.parcels.payload.response.ParcelInfoForStudent;
import com.dms.dmsapplication.parcels.repository.ParcelRepository;
import com.dms.dmsapplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    private final UserRepository userRepository;

    private static final Integer DEFAULT_VIEW_STATUS = 1;

    public ParcelService(ParcelRepository parcelRepository, UserRepository userRepository) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
    }

    public List<ParcelInfo> getAllParcels() {
        List<Parcel> parcelList = new ArrayList<>();
        List<ParcelInfo> parcelInfoList = new ArrayList<>();
        parcelList = parcelRepository.findAll();




        for (Parcel parcel : parcelList) {
            ParcelInfo parcelInfo = new ParcelInfo();
            User user = new User();

            user = userRepository.findById(parcel.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + parcel.getStudentId()));



            parcelInfo.setId(parcel.getId());
            parcelInfo.setFirstName(user.getFirstName());
            parcelInfo.setLastName(user.getLastName());
            parcelInfo.setMessage(parcel.getMessage());
            parcelInfo.setMessageTime(parcel.getMessageTime());
            parcelInfo.setMessageTitle(parcel.getMessageTitle());
            parcelInfo.setViewStatus(parcel.getViewStatus());
            parcelInfo.setEmail(user.getEmail());
            parcelInfo.setNumber(user.getNumber());
            parcelInfoList.add(parcelInfo);
        }

        return parcelInfoList;
    }

    public void createNewParcelMessage(Long studentId, String message, String messageTitle) {
        Date creationDate = Date.valueOf(getCurrentDate());
        Parcel parcel = new Parcel();

        parcel.setMessage(message);
        parcel.setMessageTime(creationDate);
        parcel.setStudentId(studentId);
        parcel.setMessageTitle(messageTitle);
        parcel.setViewStatus(DEFAULT_VIEW_STATUS);

        parcelRepository.save(parcel);
    }

    public void deleteParcelMessage(Long parcelId) {
        parcelRepository.findById(parcelId)
                                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found with id: " + parcelId));
        parcelRepository.deleteById(parcelId);
    }

    public ParcelInfoForStudent getParcelMessageByStudentId(Long studentId) {
        Parcel parcel;
        parcel = parcelRepository.findByStudentId(studentId).
                orElseThrow(() -> new ResourceNotFoundException("Parcel not found with studentId:" + studentId));

        ParcelInfoForStudent response = new ParcelInfoForStudent();

            response.setId(parcel.getId());
            response.setMessageTitle(parcel.getMessageTitle());
            response.setMessage(parcel.getMessage());
            response.setMessageTime(parcel.getMessageTime());
            response.setViewStatus(parcel.getViewStatus());

        return response;
    }

    public void changeParcelMessageViewStatusByStudentId(Long studentId) {
        Parcel parcel;
        parcel = parcelRepository.findByStudentId(studentId).
                                 orElseThrow(() -> new ResourceNotFoundException("Parcel not found with studentId:" + studentId));
        parcel.setViewStatus(0);
        parcelRepository.save(parcel);
    }

    public String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

}
