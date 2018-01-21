package DP;
/**
 * 백준 11048번 이동하기
 * 방법1. Bottom up (i,j)로 오는 방법
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
     * d[i][j] = (1,1)에서 (i,j)까지 가는 방법 중 최대 사탕 개수
     * (i,j)로 '오는' 방법은 (i - 1, j), (i - 1, j - 1), (i, j - 1)이 있다
     * d[i][j] = Max(d[i - 1][j], d[i - 1][j - 1], d[i][j - 1]) + A[i][j]
     */
    int[][] d = new int[row + 1][col + 1];

    /* *
     * (1,1)부터 초기화하면 i = 1 또는 j = 1일 때, 범위 체크 생략 가능하다
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
