/**
 * 백준 1520 내리막길
 * d[i][j] = (1,1) 에서 (i,j)로 가는 경로 수
 * (i,j)에서 갈 수 있는 4방향에 대해 경로가 있는지 재귀로 접근
 * d[i][j] += go(x,y) (x, y는 방향의 좌표)
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
     * dp값을 -1로 초기화해주는 이유
     * dp값을 0으로 초기화하면, 방문 안 한 상태와 현재 위치에서 목적지까지 경로가 없을 경우 모두
     * d[i][j] = 0으로 표시되어 구분이 불가하다. 그 결과, 경로가 없을 때 재귀를 반복적으로 호출하게
     * 되어 시간초과가 발생된다. 이를 방지하기 위해 -1로 초기화 해준다.
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
    // 방문했다는 표시로
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