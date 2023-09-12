package com.algorithms.templates;

import com.algorithms.ctci.normal.recursionanddp.StaircaseThreeStepHopping;

public class RecursionTemplate {
    /**
     * First step: Brute Force Solution:
     *   => Come up with basic recursive solution, which will consist of some base condition/s and recursive call/s
     *   => e.g. Like the Fibonacci problem, if each recursive call branches out to more calls, the runtime of such
     *        algorithm will be exponential, i.e. (roughly 0(noOfCalls^n))
     *   => Space Complexity: O(N).Auxiliary Space required by the recursive call stack is O(depth of recursion tree).
     *
     * Next: DP using Memoization (Top-down approach)
     *   => We can avoid the repeated work done in method 1(recursion) by storing the number of ways calculated so far.
     *   => If the recursive function is called many times for the same values (which is unnecessary), then we can fix 
     *        this through memoization by just storing all the values in an array.
     *   => Time Complexity: more likely will reduce to O(n) as more likely only one traversal of the array/dataset
     *        will be needed.
     *   => Space Complexity: O(n). To store the values in a DP, n extra space is needed. Also, stack space for 
     *        recursion is needed which is again O(n)
     *
     * Bottom-Up Approach: Another way is to take an extra space of size n and start computing values without recursive
     * calls through base conditions
     *   => Time Complexity: more likely will reduce to O(n) as more likely only one traversal of the array/dataset
     *        will be needed.
     *   => Space Complexity: O(n). To store the values in a DP, n extra space is needed.
     *
     * Next: Use variables instead of the dp array used in the Bottom-Up Approach to hold just the values we are more 
     * interested in
     *   => Time Complexity: more likely will reduce to O(n) as more likely only one traversal of the array/dataset
     *        will be needed.
     *   => Auxiliary Space: O(1), since no extra space has been taken.
     *
     * See {@link StaircaseThreeStepHopping} for implementation details
     */
}
