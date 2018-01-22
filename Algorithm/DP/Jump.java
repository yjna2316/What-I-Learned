/**
 * Baekjoon 1890 Jump
 * D[i][j] = (i,j)로 갈 수 있는 경로 수 
 * (i, j) -> (i, j + A) := D[i][j + A] += D[i][j]
 * (i, j) -> (i + A, j) := D[i + A][j] += D[i][j]
 * Time: O(n^2) Space: O(n^2)
 */
package DP;
import java.util.*;
public class Jump {
  
  public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
    
    int n = sc.nextInt();
    int[][] data = new int[n + 1][n + 1];
    /*
     * 주의. 경로의 개수 2^64 -1 이므로 int로 선언시 표현범위를 넘어가므로 long 사용
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
