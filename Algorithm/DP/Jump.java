package DP;
/**
 * Baekjoon 1890 Jump
 * D[i][j] = (i,j)�� �� �� �ִ� ��� ��
 */
import java.util.*;
public class Jump {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[][] data = new int[n + 1][n + 1];
    /*
     * ����. ����� ���� 2^64 -1 �̹Ƿ� int�� ����� ǥ�������� �Ѿ�Ƿ� long ���
     */
    long[][] paths = new long[n + 1][n + 1];
    paths[1][1] = 1;
    for (int i = 1; i <= n; ++i) {
      for (int j = 1; j<= n; ++j) {
        data[i][j] = sc.nextInt();
        if (data[i][j] == 0) {
          continue;
        }
        // (i, j) -> (i, j + data[i][j])
        if (j + data[i][j] <= n) {
          paths[i][j + data[i][j]] += paths[i][j];
        }        
        // (i, j) -> (i + data[i][j], j)
        if (i + data[i][j] <= n) {
          paths[i + data[i][j]][j] += paths[i][j];
        }
      }
    }
    System.out.println(paths[n][n]);
  }
}