/**
 * 763. Partition Labels
 * https://leetcode.com/problems/partition-labels/description/
 * Tag: greedy, two pointers, array, ascii
 */

class Solution {
    public List<Integer> partitionLabels(String S) {
        List<Integer> list = new ArrayList<>();
        int[] lastIndex = new int[26];
        // get last index of each alphabets
        for (int i = 0; i < S.length(); ++i) {
            lastIndex[S.charAt(i) - 'a'] = i; 
        }
        // get partition 
        int start = 0;
        for (int j = 0, last = 0; j < S.length(); ++j) {
            last = Math.max(last, lastIndex[S.charAt(j) - 'a']);
            if (last == j) {
                list.add(last - start + 1);
                start = last + 1;
            }           
        }
        return list;
    }
}