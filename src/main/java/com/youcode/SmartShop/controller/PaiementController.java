package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import com.youcode.SmartShop.service.interfaces.IChequeService;
import com.youcode.SmartShop.service.interfaces.IEspecesService;
import com.youcode.SmartShop.service.interfaces.IVirementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paiement")
@AllArgsConstructor
public class PaiementController {

    private  final IChequeService   chequeService;
    private final IEspecesService  especesService;
    private final IVirementService virementService;

    @PostMapping("cheque")
    public ResponseEntity<ChequeResponseDto>   createChaque(@Valid @RequestBody ChequeCreateRequestDto  request){
        ChequeResponseDto   response   =  chequeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("virement")
    public ResponseEntity<VirementResponseDto>   createVirement(@RequestBody VirementCreateRequestDto request){
        VirementResponseDto response   =  virementService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("especes")
    public ResponseEntity<EspecesResponseDto>   createEspeces(@Valid @RequestBody EspecesCreateRequestDto request){
        EspecesResponseDto   response   =  especesService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
