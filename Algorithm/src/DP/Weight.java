package DP;

import java.util.*;

public class Weight {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int sortOfWeights = sc.nextInt();
    int totalWeight = sc.nextInt();
    int[] weights = new int[sortOfWeights];
    for (int i = 0; i < sortOfWeights; i++) {
      weights[i] = sc.nextInt();
    }
    System.out.println(getHowManyWays(weights, sortOfWeights, totalWeight));
  }

  public static String getHowManyWays(int[] weights, int sortOfWeights, int totalWeight) {
    int[] ways = new int[totalWeight + 1];
    int[][] remainsPerWeights = new int[sortOfWeights + 1][totalWeight + 1];
    for (int i = 1; i <= totalWeight; ++i) {
      ways[i] = totalWeight + 1;
    }
    // o(n*n*m)
    int w = weights.length;
    for (int i = 1; i <= w; i ++) {
      for (int amount = weights[i]; amount <= totalWeight; ++ amount) {
        if (ways[amount - weights[i]] <= totalWeight) {
          ways[amount] = (ways[amount] > ways[amount - weights[i]] + 1) ? ways[amount - weights[i]] + 1 : ways[amount];
          Arrays.fill(remainsPerWeights,0);  // reset array o(m)
          remainsPerWeights[i][amount] ++;
          for (int j = 1; i <= w; i ++) {
            remainsPerWeights[j][amount] += remainsPerWeights[j][amount - weights[i]];
          }
        }
      }
    }
    System.out.println(Arrays.toString(ways));
    return (ways[totalWeight] == totalWeight + 1) ? "Fail" : ways[totalWeight] + " ";
  }
}