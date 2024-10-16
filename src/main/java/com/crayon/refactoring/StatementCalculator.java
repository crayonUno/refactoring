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

    public String statement(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder("Statement for " + invoice.customer + "\n");

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.performances) {
            int thisAmount = calculateFor(perf, plays);

            volumeCredits += Math.max(perf.audience - 30, 0);
            if ("comedy".equals(playFor(perf, plays).type)) {
                volumeCredits += Math.floorDiv(perf.audience, 5);
            }

            result.append(
                    String.format(
                            " %s: %s (%d seats)\n",
                            playFor(perf, plays).name,
                            format.format(thisAmount / 100.0),
                            perf.audience));
            totalAmount += thisAmount;
        }

        result.append("Amount owed is " + format.format(totalAmount / 100.0) + "\n");
        result.append("You earned " + volumeCredits + " credits\n");
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