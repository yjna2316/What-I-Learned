package DP;
/**
 * ���� 11048�� �̵��ϱ�
 * ���1. Bottom up (i,j)�� ���� ���
 */
import java.util.*;

public class Move {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int row = sc.nextInt();
    int col = sc.nextInt();
    int[][] matrix = new int[row + 1][col + 1];
    for (int i = 1; i < row; ++i) {
      for (int j = 1; j < col; ++j) {
        matrix[i][j] = sc.nextInt();
      }
    }
    getMaxCandies(row, col, matrix);
  }

  private static void getMaxCandies(int row, int col, int[][] matrix) {
    /*
     * d[i][j] = (1,1)���� (i,j)���� ���� ��� �� �ִ� ���� ����
     * (i,j)�� '����' ����� (i - 1, j), (i - 1, j - 1), (i, j - 1)�� �ִ�
     * d[i][j] = Max(d[i - 1][j], d[i - 1][j - 1], d[i][j - 1]) + A[i][j]
     */
    int[][] d = new int[row + 1][col + 1];

    /* *
     * (1,1)���� �ʱ�ȭ�ϸ� i = 1 �Ǵ� j = 1�� ��, ���� üũ ���� �����ϴ�
     */
    for (int i = 1; i <= row; ++i) {
      for (int j = 1; j <= col; ++j) {
        d[i][j] = (d[i][j - 1] > d[i - 1][j - 1]) ? d[i][j - 1] : d[i - 1][j - 1];
        d[i][j] = (d[i][j] > d[i - 1][j - 1]) ? d[i][j] : d[i - 1][j - 1];
        d[i][j] += matrix[i][j];
      }
    }
    System.out.println(d[row - 1][col - 1]);
  }
}
