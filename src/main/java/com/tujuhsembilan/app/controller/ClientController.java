package com.tujuhsembilan.app.controller;


import com.tujuhsembilan.app.dto.response.ClientListResponse;
import com.tujuhsembilan.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-management")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<ClientListResponse> getClient() {
        return clientService.getClient();
    }

}
