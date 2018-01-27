package DP;
/*
 * Baekjoon 1509 팰린드롬 분할
 * D[i] = i번째 문자열까지 팰린드롬 분할 했을 때, 분할의 최소 개수
 * D[i] = min(D[j - 1]) + 1(i ~ j는 팰린드롬)
 */
import java.util.Scanner;
public class PalindromeMinNum {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    input = " " + input;
    int length = input.length();
    boolean[][] isPalindrome = new boolean[length + 1][length + 1];

    /* If the length of the input is one, always palindrome */
    for (int i = 1; i <= length; ++i) {
      isPalindrome[i][i] = true;
    }
    /* If the length of the input is two and two characters are equal, then palindrome */
    for (int i = 1; i < length - 1; ++i) {
      isPalindrome[i][i + 1] = (input.charAt(i) == input.charAt(i + 1));
    }
    /* If the length of the input is more than two */
    for (int interval = 2; interval < length; ++interval) {
      for (int i = 1; i < length - interval;  ++i) {
        int j = i + interval;
        if (input.charAt(i) == input.charAt(j)) {
          isPalindrome[i][j] = isPalindrome[i + 1][j - 1];
        }
      }
    }
    /* Get the minimum number of palindrome partitions */
    int[] minNumDivision = new int[length + 1];
    minNumDivision[0] = 0;
    for (int i = 1; i < length; ++i) {
      minNumDivision[i] = -1;
      for (int j = 1; j <= i; ++j) {
        if(isPalindrome[j][i]) {
          if(minNumDivision[i] == -1 || minNumDivision[i] > minNumDivision[j - 1] + 1) {
            minNumDivision[i] = minNumDivision[j - 1] + 1;
          }
        }
      }
    }
    System.out.println(minNumDivision[length - 1]);
  }
}