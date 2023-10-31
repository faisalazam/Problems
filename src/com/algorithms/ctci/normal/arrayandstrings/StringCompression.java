package com.algorithms.ctci.normal.arrayandstrings;

public class StringCompression {
    /**
     * String Compression: Implement a method to perform basic string compression using the counts of repeated characters.
     * For example, the string aabcccccaaa would become a2blc5a3. If the "compressed "string would not become smaller
     * than the original string, your method should return the original string. You can assume the string has only
     * uppercase and lowercase letters (a - z),
     */
    private static int setChar(char[] array, char c, int index, int count) {
        array[index] = c;
        index++;
        char[] cnt = String.valueOf(count).toCharArray();
        for (char x : cnt) {
            array[index] = x;
            index++;
        }
        return index;
    }

    private static int countCompression(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int size = 0;
        int count = 1;
        char last = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                count = 1;
                last = str.charAt(i);
                // Converting count to String to count the number of chars instead of simple int addition
                // e.g. if count = 100 => 1 + 100 = 101, but 1 + "100".length() = 1 + 3 = 4 chars
                size += 1 + String.valueOf(count).length();
            }
        }
        size += 1 + String.valueOf(count).length();
        return size;
    }

    /**
     * The runtime is 0(p + k²), where p is the size of the original string and k is the number of character sequences.
     * For example, if the string is aabccdeeaa, then there are six character sequences.
     * It's slow because string concatenation operates in 0(n²) time
     */
    public static String compressBad(String str) {
        final int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        String compressedStr = "";
        int count = 1;
        char last = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                compressedStr += last + "" + count;
                last = str.charAt(i);
                count = 1;
            }
        }
        return compressedStr + last + count;
    }

    /**
     * Improved by using StringBuilder
     */
    private static String compressBetter(String str) {
        final int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        int count = 1;
        char last = str.charAt(0);
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                stringBuilder.append(last).append(count);
                last = str.charAt(i);
                count = 1;
            }
        }
        return stringBuilder.append(last).append(count).toString();
    }

    private static String compressAlternate(String str) {
        final int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        int index = 0;
        int count = 1;
        char last = str.charAt(0);
        final char[] array = new char[size];
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                index = setChar(array, last, index, count);
                last = str.charAt(i);
                count = 1;
            }
        }
        setChar(array, last, index, count);
        return String.valueOf(array);
    }

    public static void main(String[] args) {
        String str = "abbccccccde";
        int c = countCompression(str);
        String str2 = compressAlternate(str);
        String t = compressBetter("");
        System.out.println("Compression: " + t);
        System.out.println("Old String (len = " + str.length() + "): " + str);
        System.out.println("New String (len = " + str2.length() + "): " + str2);
        System.out.println("Potential Compression: " + c);
    }
}
