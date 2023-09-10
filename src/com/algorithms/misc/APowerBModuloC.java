package com.algorithms.misc;

public class APowerBModuloC {
    public static void main(String[] args) {
        System.out.println(pow(-1, 1, 20));
//        System.out.println(pow(2, 5, 7));
    }

    //This is the solution(O(logb)) instead of (O(b)) where you just keep multiplying the number itself 'b' times
    private static int pow(int number, int exponent, int mod) {
        if (exponent == 0) {
            return 0;
        } else if (exponent == 1) {
            return number % mod;
        }
        int x = pow(number, exponent / 2, mod);
        int evenResult = (x * x) % mod;
        if (exponent % 2 == 0) { // e.g. when exponent is even => a^b = (a^(b/2) * a^(b/2)) => 2^4 = 2^2 * 2^2 = 16
            return evenResult;
        }
        //else e.g.  when exponent is odd => a^b = a * (a^(b-1)) ...which makes it even
        // hence, a * evenResult = a * (a^(b/2) * a^(b/2)) => 2^5 = 2 * 2^2 * 2^2 = 32
        return (number * evenResult) % mod;
    }
}
