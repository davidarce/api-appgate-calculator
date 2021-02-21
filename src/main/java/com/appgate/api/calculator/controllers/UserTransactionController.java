package com.appgate.api.calculator.controllers;

import com.appgate.api.calculator.models.UserTransactionTrace;
import com.appgate.api.calculator.services.TransactionTraceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserTransactionController {

    private final TransactionTraceService transactionTraceService;

    public UserTransactionController(TransactionTraceService transactionTraceService) {
        this.transactionTraceService = transactionTraceService;
    }

    @Operation(summary = "Get session transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTransactionTrace[].class))}),
            @ApiResponse(responseCode = "404", description = "User session id not found",
                    content = @Content),
    })
    @GetMapping("/{sessionId}/transactions")
    public ResponseEntity<List<UserTransactionTrace>> getTransactions(
            @Parameter(description = "User session id") @PathVariable String sessionId) {

        List<UserTransactionTrace> transactions = transactionTraceService.getUserTransactions(sessionId);
        return ResponseEntity.ok(transactions);
    }

}
