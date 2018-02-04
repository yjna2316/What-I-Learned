/**
 * ���� 1520 ��������
 * d[i][j] = (1,1) ���� (i,j)�� ���� ��� ��
 * (i,j)���� �� �� �ִ� 4���⿡ ���� ��ΰ� �ִ��� ��ͷ� ����
 * d[i][j] += go(x,y) (x, y�� ������ ��ǥ)
 */
package DP;
import java.util.Scanner;
public class GoDown {
  public static int[] dx = {1, -1, 0, 0};
  public static int[] dy = {0, 0, 1, -1};
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    int[][] a = new int[n + 1][m + 1];
    int[][] d = new int[n + 1][m + 1];
    /**
     * dp���� -1�� �ʱ�ȭ���ִ� ����
     * dp���� 0���� �ʱ�ȭ�ϸ�, �湮 �� �� ���¿� ���� ��ġ���� ���������� ��ΰ� ���� ��� ���
     * d[i][j] = 0���� ǥ�õǾ� ������ �Ұ��ϴ�. �� ���, ��ΰ� ���� �� ��͸� �ݺ������� ȣ���ϰ�
     * �Ǿ� �ð��ʰ��� �߻��ȴ�. �̸� �����ϱ� ���� -1�� �ʱ�ȭ ���ش�.
     */
    for (int i = 1; i <= n; ++i) {
      for (int j = 1; j <= m; ++j) {
        a[i][j] = sc.nextInt();
        d[i][j] = -1;
      }
    }
    System.out.println(getNumberOfPaths(a, d, 1, 1));
  }

  public static int getNumberOfPaths(int[][] a, int[][] d, int x, int y) {
    int n = a.length - 1;
    int m = a[0].length - 1;
    if (x == n && y == m) {
      return 1;
    }
    // memoization
    if (d[x][y] >= 0) {
      return d[x][y];
    }
    // �湮�ߴٴ� ǥ�÷�
    d[x][y] = 0;
    for (int i = 0; i < 4; ++i) {
      int nx = x + dx[i];
      int ny = y + dy[i];
      if (1 <= nx && nx <= n && 1 <= ny && ny <= m) {
        if (a[x][y] > a[nx][ny]) {
          d[x][y] += getNumberOfPaths(a, d, nx, ny);
        }
      }
    }
    return d[x][y];
  }
}