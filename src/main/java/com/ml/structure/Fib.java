package com.ml.structure;

/**
 * @author liuwenyi
 * @date 2019/12/22
 */
public class Fib {

    private static long fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    private static long fib2(int n) {
        if (n <= 1) {
            return n;
        }
        long first = 0;
        long second = 1;
        for (int i = 0; i < n - 1; i++) {
            long sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

    public static void main(String[] args) {
        System.out.println(fib2(64));
    }
}
