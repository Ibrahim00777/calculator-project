package com.example.calculator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Calculator", description = "Arithmetic operations for the Calcly application")
public class CalculatorController {
    @PostMapping("/calculate")
    @Operation(summary = "Calculate two numbers", description = "Supports addition, subtraction, multiplication, and division.", responses = {
        @ApiResponse(responseCode = "200", description = "Calculation completed"),
        @ApiResponse(responseCode = "400", description = "Invalid operation or division by zero")
    })
    public ResponseEntity<?> calculate(@RequestBody CalculationRequest request) {
        double a = request.getFirstNumber();
        double b = request.getSecondNumber();
        String operation = request.getOperation();
        if (operation == null) return ResponseEntity.badRequest().body("Operation is required.");
        double result;
        switch (operation) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "×": case "*": result = a * b; break;
            case "÷": case "/":
                if (b == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot divide by zero.");
                result = a / b; break;
            default: return ResponseEntity.badRequest().body("Unsupported operation.");
        }
        return ResponseEntity.ok(new CalculationResponse(result, a + " " + operation + " " + b));
    }
}
