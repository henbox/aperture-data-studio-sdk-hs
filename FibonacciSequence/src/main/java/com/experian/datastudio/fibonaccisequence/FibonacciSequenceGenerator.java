package com.experian.datastudio.fibonaccisequence;

import java.math.BigInteger;

public class FibonacciSequenceGenerator {
    private BigInteger[] fibonacciNumbersToN;

    public FibonacciSequenceGenerator(int sequenceLength) {

        fibonacciNumbersToN = new BigInteger[sequenceLength];

        BigInteger previous = BigInteger.valueOf(0);
        BigInteger next = BigInteger.valueOf(1);
        BigInteger result = BigInteger.valueOf(0);
        fibonacciNumbersToN[0] = result;
        fibonacciNumbersToN[1] = next;
        for (int i = 2; i < sequenceLength; i++) {
            result=previous.add(next);
            fibonacciNumbersToN[i] = result;
            previous=next;
            next=result;
        }
    }

    public String getFibonacciNumber(long rowIndex) {
        return fibonacciNumbersToN[(int)rowIndex].toString();
    }
}
