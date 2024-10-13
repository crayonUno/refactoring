package com.crayon.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class StatementCalculatorTest {

    private StatementCalculator calculator;
    private Invoice invoice;
    private Map<String, Play> plays;

    @BeforeEach
    void setUp() {
        calculator = new StatementCalculator();
        invoice = new Invoice();
        plays = new HashMap<>();
    }

    @Test
    void testStatementForTragedyAndComedy() {
        // 设置测试数据
        invoice.customer = "BigCo";
        invoice.performances = new ArrayList<>();
        invoice.performances.add(new Performance("hamlet", 55));
        invoice.performances.add(new Performance("as-like", 35));

        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));

        String result = calculator.statement(invoice, plays);

        // 验证结果
        String expected = "Statement for BigCo\n" +
                          " Hamlet: $650.00 (55 seats)\n" +
                          " As You Like It: $580.00 (35 seats)\n" +
                          "Amount owed is $1,230.00\n" +
                          "You earned 37 credits\n";

        assertEquals(expected, result);
    }

    @Test
    void testStatementForOnlyTragedy() {
        invoice.customer = "SmallCo";
        invoice.performances = new ArrayList<>();
        invoice.performances.add(new Performance("othello", 40));

        plays.put("othello", new Play("Othello", "tragedy"));

        String result = calculator.statement(invoice, plays);

        String expected = "Statement for SmallCo\n" +
                          " Othello: $500.00 (40 seats)\n" +
                          "Amount owed is $500.00\n" +
                          "You earned 10 credits\n";

        assertEquals(expected, result);
    }

    @Test
    void testStatementForUnknownType() {
        invoice.customer = "TestCo";
        invoice.performances = new ArrayList<>();
        invoice.performances.add(new Performance("unknown", 50));

        plays.put("unknown", new Play("Unknown Play", "unknown"));

        assertThrows(RuntimeException.class, () -> calculator.statement(invoice, plays));
    }
}