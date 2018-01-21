package DP;
/**
 * 백준 11048번 이동하기
 * 방법3. Top down (i,j)에서 갈 수 있는 방법
 */

import java.util.*;

public class Move3 {
  static int[][] matrix;
  static int[][] d;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int row = sc.nextInt();
    int col = sc.nextInt();
    matrix = new int[row + 1][col + 1];
    d = new int[row + 1][col + 1];
    for (int i = 1; i <= row; ++i) {
      for (int j = 1; j <= col; ++j) {
        matrix[i][j] = sc.nextInt();
        d[i][j] = -1;
      }
    }
    System.out.println(getMaxCandies(row, col));
  }

  private static int getMaxCandies(int row, int col) {

    if (row < 1 || col < 1) {
      return 0;
    }

    // Memoization
    if (d[row][col] >= 0) {
      return d[row][col];
    }

    // 캔디 값이 양수이기 때문에 대각선 이동은 최대가 될 수 없으므로 오른쪽 이동과 아랫쪽 이동만 계산한다.
    d[row][col] = Math.max(getMaxCandies(row - 1, col), getMaxCandies(row, col - 1))  + matrix[row][col];
    return d[row][col];
  }
}
