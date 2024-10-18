package com.crayon.refactoring;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author sea
 */

public class StatementCalculator {

    private Play playFor(Performance perf, Map<String, Play> plays) {
        return plays.get(perf.playID);
    }

    private int calculateCredits(Performance perf, Map<String, Play> plays) {
        int result = 0;
        result = Math.max(perf.audience - 30, 0);
        if ("comedy".equals(playFor(perf, plays).type)) {
            result += Math.floorDiv(perf.audience, 5);
        }
        return result;
    }

    private NumberFormat numberFormatFor() {
        return NumberFormat.getNumberInstance(Locale.US);
    }

    private int calVolumeCredits(Map<String, Play> plays, Invoice invoice) {
        int volumeCredits = 0;
        for (Performance perf : invoice.performances) {
            volumeCredits += calculateCredits(perf, plays);
        }
        return volumeCredits;
    }

    private int calTotalAmount(Map<String, Play> plays, Invoice invoice) {
        int totalAmount = 0;
        for (Performance perf : invoice.performances) {
            int thisAmount = calculateFor(perf, plays);
            totalAmount += thisAmount;
        }
        return totalAmount;
    }

    public String statement(Invoice invoice, Map<String, Play> plays) {
        StringBuilder result = new StringBuilder("Statement for " + invoice.customer + "\n");

        for (Performance perf : invoice.performances) {
            result.append(String.format(" %s: %s (%d seats)\n", playFor(perf, plays).name,
                            numberFormatFor().format(calculateFor(perf, plays) / 100.0),
                            perf.audience));
        }

        result.append("Amount owed is " + numberFormatFor().format(calTotalAmount(plays, invoice) / 100.0) + "\n");
        result.append("You earned " + calVolumeCredits(plays, invoice) + " credits\n");
        return result.toString();
    }

    private int calculateFor (Performance perf, Map<String, Play> plays) {
        int thisAmount = 0;

        switch (playFor(perf, plays).type) {
            case "tragedy":
                thisAmount = 40000;
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30);
                }
                break;
            case "comedy":
                thisAmount = 30000;
                if (perf.audience > 20) {
                    thisAmount += 10000 + 500 * (perf.audience - 20);
                }
                thisAmount += 300 * perf.audience;
                break;
            default:
                throw new RuntimeException("unknown type: " + playFor(perf, plays).type);
        }
        return thisAmount;
    }

    public static void main(String[] args) {
        // 假设我们有一个发票对象和一个剧目映射
        Invoice invoice = new Invoice();
        // 设置发票的客户名称
        invoice.customer = "John Doe";
        invoice.performances = new ArrayList<>();

        Map<String, Play> plays = new HashMap<>();
        // 添加一些剧目到映射中
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));

        // 添加一些演出到发票中
        invoice.performances.add(new Performance("hamlet", 55));
        invoice.performances.add(new Performance("as-like", 35));

        StatementCalculator statementCalculator = new StatementCalculator();
        // 调用 statement 方法并打印结果
        String statement = statementCalculator.statement(invoice, plays);
        System.out.println(statement);
    }
}