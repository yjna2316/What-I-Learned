/**
 * Baekjoon 1328 고층빌딩
 * D[N][L][R] = 높이가 1~N인 빌딩 N개, 왼쪽에서 L개 보임, 오른쪽에서 R개 보이는 빌딩 배치의 수
 * D[N][L][R] = D[N - 1][L - 1][R] + D[N - 1][L][R - 1] + D[N - 1][L][R] * (N - 2)
 */
package DP;
import java.util.*;
public class HighBuilding {

  public static long mod = 1000000007L;

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int l = sc.nextInt();
    int r = sc.nextInt();
    long[][][] d = new long[n + 1][n + 1][n + 1];
    d[1][1][1] = 1L;
    for (int i = 2; i <= n; i++) {
      for (int j = 1; j <= l; j++) {
        for (int k = 1; k <= r; k++) {
          d[i][j][k] = d[i - 1][j - 1][k] + d[i - 1][j][k - 1] + d[i - 1][j][k] * (i - 2);
          d[i][j][k] %= mod;
        }
      }
    }
    System.out.println(d[n][l][r]);
  }
}

