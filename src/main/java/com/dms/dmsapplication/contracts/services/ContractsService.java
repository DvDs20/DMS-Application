package com.dms.dmsapplication.contracts.services;

import com.dms.dmsapplication.contracts.models.Contract;
import com.dms.dmsapplication.contracts.payload.response.ResponseForContractInfo;
import com.dms.dmsapplication.contracts.payload.response.ResponseForContractInfoForUser;
import com.dms.dmsapplication.contracts.payload.response.ResponseForUserWithoutContract;
import com.dms.dmsapplication.contracts.repository.ContractsRepository;
import com.dms.dmsapplication.exception.ResourceNotFoundException;
import com.dms.dmsapplication.models.User;
import com.dms.dmsapplication.repository.UserRepository;
import com.dms.dmsapplication.repository.UserRoomRepository;
import com.dms.dmsapplication.rooms.models.Room;
import com.dms.dmsapplication.rooms.repository.RoomRepository;
import com.dms.dmsapplication.security.services.UserDetailsServiceImpl;
import com.dms.dmsapplication.service.UserRoomsService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;

@Service
public class ContractsService {

    private final static Integer DEFAULT_CONTRACT_STATUS = 0;
    private final static Integer USER_STATUS_AFTER_ADDING_CONTRACT = 3;
    private final static Integer USER_STATUS_AFTER_DELETING_CONTRACT = 2;
    private final static Integer ROOM_STATUS_AFTER_THERE_ARE_NO_SPACE = 0;
    private final static Integer ROOM_STATUS_AFTER_THERE_ARE_FULL_SPACE = 1;
    private final static Integer ROOM_STATUS_AFTER_THERE_ARE_SPACE = 2;
    private final static String PREFIX_OF_CONTRACT_NUMBER = "SUT-";

    private final ContractsRepository contractsRepository;

    private final UserRoomsService userRoomsService;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    public ContractsService(ContractsRepository contractsRepository, UserRoomsService userRoomsService,
            UserDetailsServiceImpl userDetailsService, UserRepository userRepository, RoomRepository roomRepository,
            UserRoomRepository userRoomRepository) {
        this.contractsRepository = contractsRepository;
        this.userRoomsService = userRoomsService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
    }

    public List<Contract> getAllContracts() {
        return contractsRepository.findAll();
    }

    public void createNewContract(Long studentId, Long roomId, Date expireDate, String priceForStudent) {
        String modifiedDate = getCurrentDate().replace("-", "");
        String contractNumber = PREFIX_OF_CONTRACT_NUMBER + modifiedDate + getGeneratedSuffixOfContractNumber();
        Contract contract = new Contract();

        //adding new contract info
        contract.setStudentId(studentId);
        contract.setRoomId(roomId);
        contract.setContractNumber(contractNumber);
        contract.setExpireDate(expireDate);
        contract.setStatus(DEFAULT_CONTRACT_STATUS);
        contract.setPriceForStudent(priceForStudent);
        contractsRepository.save(contract);

        //adding userId and roomId to UserRoom entity
        userRoomsService.saveRoomIdAndUserIds(studentId, roomId);

        //changing user status after adding contract
        User user = userDetailsService.getUserDetailsByUserId(studentId);
        user.setUserStatus(USER_STATUS_AFTER_ADDING_CONTRACT);
        userRepository.save(user);

        //changing room status and room left space after adding contract
        Room room = roomRepository.findById(roomId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Room not found with id: " + roomId));
        if (room.getLeftRoomCapacity() != 0) {
            room.setLeftRoomCapacity(room.getLeftRoomCapacity() - 1);
            if (room.getLeftRoomCapacity() == 0) {
                room.setRoomStatus(ROOM_STATUS_AFTER_THERE_ARE_NO_SPACE);
            } else {
                room.setRoomStatus(ROOM_STATUS_AFTER_THERE_ARE_SPACE);
            }
        }
        roomRepository.save(room);
    }

    @Transactional
    public void deleteContract(Long contractId) {
        Contract contract = new Contract();
        contract = contractsRepository.findById(contractId)
                                      .orElseThrow(() -> new ResourceNotFoundException(
                                              "Contract not found with id: " + contractId));
        Long studentId = contract.getStudentId();
        Long roomId = contract.getRoomId();

        contractsRepository.delete(contract);

        //changing user status after deleting contract
        User user;
        if (userRepository.findById(studentId).isPresent()) {
            user = userDetailsService.getUserDetailsByUserId(studentId);
            user.setUserStatus(USER_STATUS_AFTER_DELETING_CONTRACT);
            userRepository.save(user);
        }

        //changing room status and room left space after deleting contract
        Room room = roomRepository.findById(roomId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Room not found with id: " + roomId));
        room.setLeftRoomCapacity(room.getLeftRoomCapacity() + 1);
        if (Objects.equals(room.getRoomCapacity(), room.getLeftRoomCapacity())) {
            room.setRoomStatus(ROOM_STATUS_AFTER_THERE_ARE_FULL_SPACE);
        } else {
            room.setRoomStatus(ROOM_STATUS_AFTER_THERE_ARE_SPACE);
        }

        //removing info related with studentId from userRoom entity
        if (userRoomRepository.findByUserId(studentId).isPresent()) {
            userRoomRepository.deleteByUserId(studentId);
        }
    }

    public ResponseForContractInfo getContractInfo(Long contractId) {
        Contract contract;
        contract = contractsRepository.findById(contractId)
                                      .orElseThrow(() -> new ResourceNotFoundException(
                                              "Contract not found with id: " + contractId));
        User user;
        Contract finalContract = contract;
        user = userRepository.findById(contract.getStudentId())
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "Student not found with id: " + finalContract.getStudentId()));
        Room room;
        Contract finalContract1 = contract;
        room = roomRepository.findById(contract.getRoomId())
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "Room not found with id: " + finalContract1.getRoomId()));

        ResponseForContractInfo responseForContractInfo = new ResponseForContractInfo();
        responseForContractInfo.setId(contract.getId());
        responseForContractInfo.setFirstName(user.getFirstName());
        responseForContractInfo.setLastName(user.getLastName());
        responseForContractInfo.setRoomNumber(room.getRoomNumber());
        responseForContractInfo.setExpireDate(contract.getExpireDate());
        responseForContractInfo.setContractNumber(contract.getContractNumber());

        return responseForContractInfo;
    }

    public ResponseForContractInfoForUser getContractInfoForStudent(Long studentId) {
        Contract contract;
        contract = contractsRepository.findByStudentId(studentId)
                                      .orElseThrow(() -> new ResourceNotFoundException(
                                              "Contract not found with student id: " + studentId));
        Room room;
        room = roomRepository.findById(contract.getRoomId())
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "Room not found with id: " + contract.getRoomId()));

        User user = new User();
        User finalUser = user;
        user = userRepository.findById(studentId)
                             .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + finalUser.getId()));

        ResponseForContractInfoForUser response = new ResponseForContractInfoForUser();
        response.setId(contract.getId());
        response.setContractStatus(contract.getStatus());
        response.setContractNumber(contract.getContractNumber());
        response.setExpireDate(contract.getExpireDate());
        response.setRoomNumber(room.getRoomNumber());
        response.setPriceForStudent(contract.getPriceForStudent());
        response.setUserStatus(user.getUserStatus());
        return response;
    }

    public ResponseForUserWithoutContract getEmptyResponseForContractInfo() {
        ResponseForUserWithoutContract response = new ResponseForUserWithoutContract();
        response.setMessage("Student do not have contract");

        return response;
    }

    public void changeContractStatusAfterAssign(Long studentId) {
        Contract contract;
        contract = contractsRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with student id: " + studentId));
        User user = new User();
        User finalUser = user;
        user = userRepository.findById(studentId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + finalUser.getId()));
        user.setUserStatus(1);
        contract.setStatus(1);
        contractsRepository.save(contract);
        userRepository.save(user);
    }

    public String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public String getGeneratedSuffixOfContractNumber() {
        return String.valueOf((int) (Math.random() * (999 - 100 + 1) + 100));
    }

}
