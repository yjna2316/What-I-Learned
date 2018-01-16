/*
 * Baekjoon #2294 동전2
 * https://www.acmicpc.net/problem/2294
 */
import java.util.*;

public class Coin2 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int sortOfCoins = sc.nextInt();
        int amount = sc.nextInt();
        int[] coins = new int[sortOfCoins];
        for (int i = 0; i < sortOfCoins; i++) {
            coins[i] = sc.nextInt();
        }
        System.out.println(getWaysMinimum(coins, amount));
    }

    public static int getWaysMinimum(int[] coins, int totalAmount) {
        int[] ways = new int[totalAmount + 1];
        for (int i = 1; i <= totalAmount; ++i) {
            ways[i] = totalAmount + 1;
        }
        for (int coin : coins) {
            for (int amount = coin; amount <= totalAmount; ++ amount) {
                if (ways[amount - coin] <= totalAmount) {
                    ways[amount] = (ways[amount] > ways[amount - coin] + 1) ? ways[amount - coin] + 1 : ways[amount];
                }
            }
        }
        System.out.println(Arrays.toString(ways));
        return (ways[totalAmount] == totalAmount + 1) ? -1 : ways[totalAmount];
    }
}

