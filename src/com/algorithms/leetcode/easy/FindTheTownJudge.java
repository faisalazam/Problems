package com.algorithms.leetcode.easy;

/**
 * In a town, there are N people labelled from 1 to N. There is a rumor that one of these people is secretly the town judge.
 * <p>
 * If the town judge exists, then:
 * <p>
 * The town judge trusts nobody.
 * Everybody (except for the town judge) trusts the town judge.
 * There is exactly one person that satisfies properties 1 and 2.
 * You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.
 * <p>
 * If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.
 * <p>
 * https://leetcode.com/problems/find-the-town-judge/
 */
public class FindTheTownJudge {
    /**
     * Think of it as a directed graph and then we'll be able to visualize the trusts as edges from person a -> b.
     * <p>
     * For example, with N = 5 and trust = [[1,3],[2,3],[4,3],[4,1],[5,3],[5,1],[5,4]], town judge will be 3.
     * <p>
     * In graph theory, we say the out-degree of a vertex (person) is the number of directed edges going out of it.
     * For this graph, the out-degree of the vertex represents the number of other people that person trusts.
     * <p>
     * Likewise, we say that the in-degree of a vertex (person) is the number of directed edges going into it. So here,
     * it represents the number of people trusted by that person.
     * <p>
     * SAME CONCEPT WE WILL USE BELOW
     *      1) decrements the trust count of the first person in the pair. This is done because if the first person
     *         trusts the second person, it means that the first person does not trust themselves.
     *      2) increments the trust count of the second person in the pair. This is done because if the first person
     *         trusts the second person, it means that the second person is trusted by the first person.
     *      3) check if there is a person who is trusted by everyone except themselves (n-1 people) and if such person
     *         exists it returns the index of that person.
     */
    public int findJudge(int N, int[][] trust) {
        if (trust.length == 0 && N == 1) { // N == 1 signifies that there is only one person in the town
            return 1;
        }
        final int[] trustedCount = new int[N + 1];
        for (int[] t : trust) {
            trustedCount[t[0]]--; // out-degree
            trustedCount[t[1]]++; // in-degree
        }
        for (int i = 0; i <= N; i++) {
            if (trustedCount[i] == N - 1) {
                return i;
            }
        }
        return -1;
    }

    public int findJudgeV0(int n, int[][] trust) {
        if (n == 1) {
            return 1;
        }

        final int[] inDegrees = new int[n + 1];  // trusted by
        final int[] outDegrees = new int[n + 1]; // trusts someone else

        for (int[] t : trust) {
            inDegrees[t[1]]++;
            outDegrees[t[0]]++;
        }

        // a town judge has inDegrees = n-1 ( as all trusts him ) and outDegrees = 0 ( as he trusts no-one )
        for (int i = 0; i < n + 1; i++) {
            if (inDegrees[i] == n - 1 && outDegrees[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}
