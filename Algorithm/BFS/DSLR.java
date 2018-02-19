/**
 *  Baekjoon 9019 DSLR
 *  BFS using queue
 */
package BFS;
import java.util.*;
/* Solution 1 */
public class DSLR {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int t = sc.nextInt();
    while (t-- > 0) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      boolean[] visited = new boolean[10001];
      String[] d = new String[10001];
      visited[n] = true;
      d[n] = "";
      Queue<Integer> q = new LinkedList<Integer>();
      q.add(n);
      while (!q.isEmpty()) {
        int now = q.remove();
        int next = (now*2) % 10000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now] + "D";
        }
        next = now-1;
        if (next == -1) next = 9999;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now] + "S";
        }
        next = (now%1000)*10 + now/1000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now] + "L";
        }
        next = (now/10) + (now%10)*1000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now] + "R";
        }
      }
      System.out.println(d[m]);
    }
  }
}

/**
 * Solution 2
 * �迭 2���� �̿��� � ������ ���ƴ��� �����Ѵ�.
 * from[i] = i�� � ������ ���������
 * how[i] = i�� ��� ���������
 **/
class OtherSolution {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int t = sc.nextInt();
    while (t-- > 0) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      boolean[] visited = new boolean[10001];
      int[] d = new int[10001];  // distance from root to the node
      char[] w = new char[10001]; // how
      int[] v = new int[10001];  // from
      visited[n] = true;
      d[n] = 0;
      v[n] = -1;
      Queue<Integer> q = new LinkedList<Integer>();
      q.add(n);
      while (!q.isEmpty()) {
        int now = q.remove();
        int next = (now*2) % 10000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now]+1;
          v[next] = now;
          w[next] = 'D';
        }
        next = now-1;
        if (next == -1) next = 9999;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now]+1;
          v[next] = now;
          w[next] = 'S';
        }
        next = (now%1000)*10 + now/1000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now]+1;
          v[next] = now;
          w[next] = 'L';
        }
        next = (now/10) + (now%10)*1000;
        if (visited[next] == false) {
          q.add(next);
          visited[next] = true;
          d[next] = d[now]+1;
          v[next] = now;
          w[next] = 'R';
        }
      }
      StringBuilder ans = new StringBuilder();
      while (m != n) {
        ans.append(w[m]);
        m = v[m];
      }
      System.out.println(ans.reverse().toString());
    }
  }
}