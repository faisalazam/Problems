package com.algorithms.templates;

import com.algorithms.ctci.normal.recursionanddp.StaircaseThreeStepHopping;

public class RecursionTemplate {
    /**
     * First step: Brute Force Solution:
     *   => Come up with basic recursive solution, which will consist of some base condition/s and recursive call/s
     *   => e.g. Like the Fibonacci problem, if each recursive call branches out to more calls, the runtime of such
     *        algorithm will be exponential, i.e. (roughly 0(noOfCalls^n)). If you draw a tree for Fibonacci problem,
     *        you'll notice that the nodes at each level of the tree will be doubling up because it branches out twice,
     *        i.e. 1, 2, 4, 8, 16, and so on. So, basically, you gonna end up multiplying noOfCalls with itself n times,
     *        and hence the time complexity is 0(noOfCalls^n). For detailed explanation, watch the following video
     *        after around 8 minutes:
     *        https://www.youtube.com/watch?v=oBt53YbR9Kk&list=RDCMUC8butISFwT-Wl7EV0hUK0BQ&index=3
     *   => Space Complexity: O(N). Auxiliary Space required by the recursive call stack is O(depth of recursion tree).
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
