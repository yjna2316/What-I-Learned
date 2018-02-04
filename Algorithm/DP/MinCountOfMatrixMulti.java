/**
 * Baekjoon 11049 행렬 곱셈 순서
 * D[i][j] = i번째 행렬부터 j번째 행렬까지 곱했을 때, 곱셈 연산 최소값
 * D[i][j] = Min(D[i][k] + D[k + 1][j] + A[i][0]*A[k][1]*A[j][1])
 * Time: O(n^3), space: O(n^2)
 */
package DP;
import java.util.*;
public class MinCountOfMatrixMulti {
  public static int go(int[][] a, int[][] d, int i, int j) {
    if (d[i][j] > 0) {
      return d[i][j];
    }
    if (i == j) {
      return 0;
    }
    if (i+1 == j) {
      return a[i][0]*a[i][1]*a[j][1];
    }
    for (int k = i; k < j; ++k) {
      int t1 = go(a,d,i,k);
      int t2 = go(a,d,k + 1,j);
      int t3 = a[i][0] * a[k][1] * a[j][1];
      if (d[i][j] == 0 || d[i][j] > t1 + t2 + t3) {
        d[i][j] = t1 + t2 + t3;
      }
    }
    return d[i][j];
  }
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[][] a = new int[n + 1][2];
    for (int i = 1; i <= n; ++i) {
      a[i][0] = sc.nextInt();
      a[i][1] = sc.nextInt();
    }
    int[][] d = new int[n + 1][n + 1];
    System.out.println(go(a,d,1,n));
  }
}