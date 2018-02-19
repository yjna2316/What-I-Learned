package DP;
import java.util.*;
/*
 * 백준 2293번 동전1
 * https://www.acmicpc.net/problem/2293
 */
public class Coin1 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int sortOfCoins = sc.nextInt();
        int amount = sc.nextInt();
        int[] coins = new int[sortOfCoins];
        for (int i = 0; i < sortOfCoins; i++) {
            coins[i] = sc.nextInt();
        }
        getHowManyWays(coins, amount);
    }

    public static void getHowManyWays(int[] coins, int totalAmount) {
        int[] waysOfDoingNCoins = new int[totalAmount + 1];
        waysOfDoingNCoins[0] = 1;
        for (int coin : coins) {
            for (int amount = coin; amount <= totalAmount; ++amount) {
                waysOfDoingNCoins[amount] += waysOfDoingNCoins[amount - coin];
            }
        }
        System.out.println(waysOfDoingNCoins[totalAmount]);
    }
}
