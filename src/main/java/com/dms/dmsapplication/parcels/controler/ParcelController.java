package com.dms.dmsapplication.parcels.controler;

import com.dms.dmsapplication.parcels.model.Parcel;
import com.dms.dmsapplication.parcels.payload.request.NewParcelMessageCreationRequest;
import com.dms.dmsapplication.parcels.payload.response.ParcelInfo;
import com.dms.dmsapplication.parcels.service.ParcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping("/parcels")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ParcelInfo> getAllParcels() {
        return parcelService.getAllParcels();
    }

    @PostMapping("/parcels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewParcelMessage(@RequestBody NewParcelMessageCreationRequest request) {
        parcelService.createNewParcelMessage(request.getStudentId(), request.getMessage(), request.getMessageTitle());
        return ResponseEntity.ok().build();
    }

}
