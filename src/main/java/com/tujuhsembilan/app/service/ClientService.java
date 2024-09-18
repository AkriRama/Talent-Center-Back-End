package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.response.ClientListResponse;
import com.tujuhsembilan.app.dto.response.ClientListResponseDTO;
import com.tujuhsembilan.app.model.Client;
import com.tujuhsembilan.app.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public ResponseEntity<ClientListResponse> getClient() {
        try {
            List<Client> clientList = clientRepository.findAll();

            List<ClientListResponseDTO> responseDTOS = clientList.stream().map(client ->
                    new ClientListResponseDTO(
                            client.getClientId(),
                            client.getClientName(),
                            client.getGender(),
                            client.getBirthDate(),
                            client.getEmail(),
                            client.getAgencyName(),
                            client.getAgencyAddress(),
                            client.getIsActive()
                    )).collect(Collectors.toList());

            int totalData = clientList.size();
            return ResponseEntity
                    .ok()
                    .body(new ClientListResponse(totalData, responseDTOS));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ClientListResponse(0, null));
        }
    }
}
