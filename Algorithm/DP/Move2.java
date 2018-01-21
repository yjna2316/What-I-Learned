package DP;
/**
 * ���� 11048�� �̵��ϱ�
 * ���2. Bottom up (i,j)���� �� �� �ִ� ���
 */
import java.util.*;

public class Move2 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int row = sc.nextInt();
    int col = sc.nextInt();
    int[][] matrix = new int[row + 1][col + 1];
    for (int i = 1; i <= row; ++i) {
      for (int j = 1; j <= col; ++j) {
        matrix[i][j] = sc.nextInt();
      }
    }
    getMaxCandies(row, col, matrix);
  }

  private static void getMaxCandies(int row, int col, int[][] matrix) {
    /*
     * d[i][j] = (1,1)���� (i,j)���� ���� ��� �� �ִ� ���� ����
     * (i,j)���� '����' ����� (i + 1, j), (i + 1, j + 1), (i, j + 1)�� �ִ�
     *
     */
    int[][] d = new int[row + 1][col + 1];

    d[0][0] = matrix[1][1];
    for (int i = 1; i <= row; ++i) {
      for (int j = 1; j <= col; ++j) {
        if (j + 1 <= col && d[i][j + 1] < d[i][j] + matrix[i][j + 1]) {
          d[i][j + 1] = d[i][j] + matrix[i][j + 1];
        }
        if (i + 1 <= row && d[i + 1][j] < d[i][j] + matrix[i + 1][j]) {
          d[i + 1][j] = d[i][j] + matrix[i + 1][j];
        }
        if (j + 1 <= col && i + 1 <= row && d[i + 1][j + 1] < d[i][j] + matrix[i + 1][j + 1]) {
          d[i + 1][j + 1] = d[i][j] + matrix[i + 1][j + 1];
        }
      }
    }
    System.out.println(d[row][col]);
  }
}
