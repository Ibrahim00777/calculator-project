package com.example.calculator;

public class CalculationResponse {
    private final double result;
    private final String expression;

    public CalculationResponse(double result, String expression) { this.result = result; this.expression = expression; }
    public double getResult() { return result; }
    public String getExpression() { return expression; }
}
