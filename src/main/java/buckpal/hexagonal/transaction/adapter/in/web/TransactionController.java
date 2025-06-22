package buckpal.hexagonal.transaction.adapter.in.web;

import java.applet.*;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import buckpal.hexagonal.transaction.adapter.in.web.api.TransferResponse;
import buckpal.hexagonal.transaction.application.port.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final TransactionUseCase transactionUseCase;


    @Operation(summary = "계좌 간 금액 이체 API", // API의 짧은 요약
        description = "해당 API는 출발 계좌에서 목적 계좌로 금액을 이체하는 기능을 제공합니다.", // API의 상세 설명
        tags = {"거래(Transactions)"} // 태그 설정
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이체 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (입력 데이터 오류 등)"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferMoney(TransactionRequest tr){

        int balance = transactionUseCase.transferMoney(tr);
        return ResponseEntity.ok(new TransferResponse(balance));
    }
}
