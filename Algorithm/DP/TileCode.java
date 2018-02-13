/**
 * Baekjoon 1720 타일코드
 * b: 대칭만 있는 경우 (좌우 대칭 없음)
 */
package DP;

import java.util.*;

public class TileCode {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    long[] a = new long[31]; // 좌우대칭 포함
    a[1] = 1;
    a[2] = 3;
    for (int i = 3; i <= 30; i++) {
      a[i] = a[i - 1] + a[i - 2] * 2L;
    }

    int n = sc.nextInt();
    long answer = 0;
    long b = 0;
    if (n == 1) {
      answer = 1;
    } else if (n == 2) {
      answer = 3;
    } else {
      if (n % 2 == 1) {
        b = a[(n - 1) / 2];
      } else {
        b = a[n / 2] + 2 * a[(n - 2) / 2];
      }
      answer = (a[n] + b) / 2;
    }
    System.out.println(answer);
  }
}

