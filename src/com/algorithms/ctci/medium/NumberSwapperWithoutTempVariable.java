package com.algorithms.ctci.medium;

public class NumberSwapperWithoutTempVariable {
    /*
        a=9, b=4; a>b
        a = a - b; 9 - 4 = 5 // a = difference between a and b
        b = b + a; 4 + 5 = 9 // b = b + difference between a and b
        a = b - a; 9 - 5 = 4 // a = b - difference between a and b

        a=4, b=9; a<b
        a = a - b; 4 - 9 = -5
        b = b + a; 9 + (-5) = 4
        a = b - a; 4 - (-5) = 9;
    */
    public void swap(int a, int b) {
        a = a - b;
        b = b + a;
        a = b - a;

        System.out.println(a + " : " + b);
    }

    /**
     * XOR is shorthand for “exclusive OR.” It is an operation that applies to two bits. If the bits are the same
     * (both 0 or both 1), then the result of the XOR operation is 0. If the bits differ (one is 0 and the other is 1),
     * then the result of the XOR operation is 1.
     * <p>
     * In statement 1:
     * x = x ^ y
     * This line essentially checks if x and y have different values. It will result in 1 if and only if x ! = y.
     * <p>
     * In statement 2:
     * y = x ^ y
     * Or:y = {0 if originally same, 1 if different} ^ {original y}
     * Observe that XORing a bit with 1 always flips the bit, whereas XORing with 0 will never change it.
     * Therefore, if we do y = 1 ^ {original y} when x != y, then y will be flipped and therefore have x's original value.
     * Otherwise, if x == y, then we do y = 0 ^ {original y} and the value of y does not change.
     * Either way, y will be equal to the original value of x.
     * <p>
     * In statement 3:
     * x = x ^ y
     * Or:x = {0 if originally same, 1 if different} ^ {original x}
     * At this point, y is equal to the original value of X. This line is essentially equivalent to the line above it,
     * but for different variables.
     * If we do x = 1 ^ { original x} when the values are different, x will be flipped.
     * If we do x = 0 ^ {original x} when the values are the same, x will not be changed.
     * This operation happens for each bit. Since it correctly swaps each bit, it wilt correctly swap the entire number.
     */
    public void swapBitwise(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a + " : " + b);
    }
}
