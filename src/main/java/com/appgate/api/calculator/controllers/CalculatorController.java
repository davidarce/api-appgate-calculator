package com.appgate.api.calculator.controllers;

import com.appgate.api.calculator.dtos.request.OperandRequest;
import com.appgate.api.calculator.dtos.response.ResultResponseDTO;
import com.appgate.api.calculator.dtos.response.SessionResponseDTO;
import com.appgate.api.calculator.models.Operators;
import com.appgate.api.calculator.services.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Operation(summary = "Create a new session")
    @ApiResponse(responseCode = "201", description = "Session created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SessionResponseDTO.class))})
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDTO> createSession() {
        String newSession = calculatorService.createSession();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SessionResponseDTO(newSession));
    }

    @Operation(summary = "Add a new operand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Operand accepted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User session id not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid operand format",
                    content = @Content)})
    @PostMapping("/{sessionId}/operands")
    public ResponseEntity<?> addOperand(@Parameter(description = "User session id") @PathVariable String sessionId,
                                        @Valid @RequestBody OperandRequest request) {
        calculatorService.addOperand(sessionId, request.getNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(summary = "Get result operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User session id not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Operation not allowed e.g division by zero, operands not found",
                    content = @Content)
    })
    @GetMapping("/{sessionId}/result")
    public ResponseEntity<ResultResponseDTO> calculate(
            @Parameter(description = "User session id") @PathVariable String sessionId,
            @Parameter(description = "Operator to calculate the operation")
            @PathParam("operator") Operators operator) {
        String result = calculatorService.calculate(sessionId, operator);
        return ResponseEntity.ok(new ResultResponseDTO(result));
    }

}
