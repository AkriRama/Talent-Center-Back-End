package com.tujuhsembilan.app.controller;

import com.tujuhsembilan.app.dto.request.*;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/talent-management")
public class TalentController {

    @Autowired
    private TalentService talentService;

    @GetMapping("/talents")
    public ResponseEntity<TalentListResponse> getTalents(
            @ModelAttribute TalentFilterRequest request,
            PageRequest pageRequest) {
        return talentService.getTalents(request, pageRequest.getPageTalent());
    }

    @GetMapping("/talents/{talentId}")
    public ResponseEntity<TalentResponse> getTalentById(@PathVariable UUID talentId) {
        return talentService.getTalentByID(talentId);
    }

    @PostMapping(path = {"/talents"}, consumes = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AddTalentResponseDTO> addTalent(
            @RequestPart("request") AddTalentRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "fileDoc", required = false) MultipartFile fileDoc
            ) {
        return talentService.addTalent(request, file, fileDoc);
    }

    @PutMapping(path = {"/talents"}, consumes = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<EditTalentResponseDTO> editTalent(
            @RequestPart("request") EditTalentRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "fileDoc", required = false) MultipartFile fileDoc
    ) {
        return talentService.editTalent(requestDTO, file, fileDoc);
    }

    @GetMapping("/talent-approvals")
    public ResponseEntity<TalentRequestListResponse> getTalentRequest(
            @ModelAttribute TalentRequestRequest request,
            PageRequest pageRequest) {
        return talentService.getTalentRequest(request, pageRequest.getPage());
    }

    @PutMapping("/talent-approvals")
    public ResponseEntity<TalentRequestResponse> approveRejectTalent(@RequestBody TalentRequestRequestDTO requestDTO) {
        return talentService.approveRejectTalent(requestDTO);
    }



    //TAMBAHAN
    @PostMapping("/talent-wishlists")
    public ResponseEntity<TalentWishlistResponseDTO> addTalentWishlist(@RequestBody TalentWishlistRequestDTO requestDTO) {
        return talentService.addTalentWishlist(requestDTO);
    }

    @PostMapping("/talent-requests")
    public ResponseEntity<AddTalentRequestResponseDTO> addTalentRequest(@RequestBody AddTalentRequestRequestDTO requestDTO) {
        return talentService.addTalentRequest(requestDTO);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
