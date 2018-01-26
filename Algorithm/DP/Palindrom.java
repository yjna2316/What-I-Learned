package DP;
import java.util.*;
/*
 * Solution1: two pointers
 * Time: O(n*m) space: O(n*n)
 */
public class Palindrom {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[] numbers = new int[n + 1];
    for (int i = 1; i <= n; ++i) {
      numbers[i] = sc.nextInt();
    }

    int m = sc.nextInt();
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= m; ++i) {
      sb.append(isPalindrome(sc.nextInt(), sc.nextInt(), numbers));
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }

  private static int isPalindrome(int left, int right, int[] numbers) {
    while (left < right && numbers[left] == numbers[right]) {
      ++left;
      --right;
    }
    return (left < right) ? 0 : 1;
  }
}

/*
 * Solution2: Bottom-up
 * Time: O(n*n + m) Space: O(n*n)
 */
class Palindrom1 {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[] numbers = new int[n + 1];
    for (int i = 1; i <= n; ++i) {
      numbers[i] = sc.nextInt();
    }

    int[][] d = new int[n + 1][n + 1];
    isPalindrome(n, numbers, d);

    int m = sc.nextInt();
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= m; ++i) {
      int StartIndex = sc.nextInt();
      int EndIndex = sc.nextInt();
      sb.append(d[StartIndex][EndIndex]);
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }

  private static void isPalindrome(int n, int[] numbers, int[][] d) {
    for(int i = 1; i < n + 1; ++i) {
      d[i][i] = 1;
    }

    for(int i = 1; i < n; ++i) {
      if(numbers[i] == numbers[i + 1]) {
        d[i][i + 1] = 1;
      }
    }

    for (int k = 2; k < n; ++k) {
      for (int i = 1; i < n - k + 1; ++i) {
          int j = i + k;
          if (numbers[i] == numbers[j] && d[i + 1][j - 1] == 1) {
            d[i][j] = 1;
          }
      }
    }
  }
}

/*
 * Solution2: Top-down
 * Time: O(n*n + m) Space: O(n*n)
 */
class Palindrom2 {
  static private int[] numbers;
  static private int[][] d;
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    numbers = new int[n + 1];
    d = new int[n + 1][n + 1];

    for (int i = 1; i <= n; ++i) {
      numbers[i] = sc.nextInt();
      Arrays.fill(d[i], - 1);
    }

    int m = sc.nextInt();
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= m; ++i) {
      int startIndex = sc.nextInt();
      int endIndex = sc.nextInt();
      sb.append(isPalindrome(startIndex, endIndex));
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }

  private static int isPalindrome(int starIndex, int endIndex) {
    if (d[starIndex][endIndex] >= 0)
      return d[starIndex][endIndex];

    if (starIndex == endIndex) {
      d[starIndex][endIndex] = 1;
    } else if (starIndex + 1 == endIndex) {
      d[starIndex][endIndex] = (numbers[starIndex] == numbers[endIndex]) ? 1 : 0;
    } else {
      d[starIndex][endIndex] = (numbers[starIndex] == numbers[endIndex]) ? isPalindrome(starIndex + 1, endIndex - 1) : 0;
    }
    return d[starIndex][endIndex];
  }
}