package com.dms.dmsapplication.contracts.services;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.payload.response.ResponseForContractInfo;
import com.dms.dmsapplication.contracts.repository.ContractsRepository;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.rooms.models.Room;
import com.dms.dmsapplication.rooms.repository.RoomRepository;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import com.dms.dmsapplication.service.UserRoomsService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ContractsService {

    private final static Integer DEFAULT_CONTRACT_STATUS = 0;
    private final static Integer USER_STATUS_AFTER_ADDING_CONTRACT = 3;
    private final static Integer ROOM_STATUS_AFTER_ADDING_CONTRACT = 0;
    private final static String PREFIX_OF_CONTRACT_NUMBER = "SUT-";

    private final ContractsRepository contractsRepository;

    private final UserRoomsService userRoomsService;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    public ContractsService(ContractsRepository contractsRepository, UserRoomsService userRoomsService,
            UserDetailsServiceImpl userDetailsService, UserRepository userRepository, RoomRepository roomRepository) {
        this.contractsRepository = contractsRepository;
        this.userRoomsService = userRoomsService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public List<Contract> getAllContracts() {
        return contractsRepository.findAll();
    }

    public void createNewContract(Long studentId, Long roomId, Date expireDate) {
        String modifiedDate = getCurrentDate().replace("-", "");
        String contractNumber = PREFIX_OF_CONTRACT_NUMBER + modifiedDate + getGeneratedSuffixOfContractNumber();
        Contract contract = new Contract();

        //adding new contract info
        contract.setStudentId(studentId);
        contract.setRoomId(roomId);
        contract.setContractNumber(contractNumber);
        contract.setExpireDate(expireDate);
        contract.setStatus(DEFAULT_CONTRACT_STATUS);
        contractsRepository.save(contract);

        //adding userId and roomId to UserRoom entity
        userRoomsService.saveRoomIdAndUserIds(studentId, roomId);

        //changing user status after adding contract
        User user = userDetailsService.getUserDetailsByUserId(studentId);
        user.setUserStatus(USER_STATUS_AFTER_ADDING_CONTRACT);
        userRepository.save(user);

        //changing room status after adding contract
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
        room.setRoomStatus(ROOM_STATUS_AFTER_ADDING_CONTRACT);
        roomRepository.save(room);
    }

    public ResponseForContractInfo getContractInfo(Long contractId) {
        Contract contract;
        contract = contractsRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));
        User user;
        Contract finalContract = contract;
        user = userRepository.findById(contract.getStudentId())
                             .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + finalContract.getStudentId()));
        Room room;
        Contract finalContract1 = contract;
        room = roomRepository.findById(contract.getRoomId())
                             .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + finalContract1.getRoomId()));

        ResponseForContractInfo responseForContractInfo = new ResponseForContractInfo();
        responseForContractInfo.setId(contract.getId());
        responseForContractInfo.setFirstName(user.getFirstName());
        responseForContractInfo.setLastName(user.getLastName());
        responseForContractInfo.setRoomNumber(room.getRoomNumber());
        responseForContractInfo.setExpireDate(contract.getExpireDate());
        responseForContractInfo.setContractNumber(contract.getContractNumber());

        return responseForContractInfo;
    }

    public String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public String getGeneratedSuffixOfContractNumber() {
        return String.valueOf((int) (Math.random()*(999-100+1)+100));
    }

}
