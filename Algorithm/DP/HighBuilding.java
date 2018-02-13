/**
 * Baekjoon 1328 ��������
 * D[N][L][R] = ���̰� 1~N�� ���� N��, ���ʿ��� L�� ����, �����ʿ��� R�� ���̴� ���� ��ġ�� ��
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

