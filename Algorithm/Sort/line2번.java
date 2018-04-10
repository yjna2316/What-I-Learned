package Line2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Time complexity: O(nlogn)
 * Space complexity:
 */

class Shop implements Comparable<Shop> {

  public int shopX;
  public int shopY;
  public double dist;
  public String name;
  public int coupNum;

  public Shop(int shopX, int shopY, double dist, String name, int coupNum) {
    this.shopX = shopX;
    this.shopY = shopY;
    this.dist = dist;
    this.name = name;
    this.coupNum = coupNum;
  }

  public int compareTo(Shop s) {
    // 거리 같고 쿠폰수도 같으면 상점 이름순 (숫자,알파벳순) = 오름차순
    if (this.dist == s.dist && this.coupNum == s.coupNum) {
      //return this.name.charAt(0) - s.name.charAt(0); (x)
      return (this.name).compareTo(s.name);
    }
    // 거리만 같으면 쿠폰 많은순 = 내림차순
    if (this.dist == s.dist && this.coupNum != s.coupNum) {
      return s.coupNum - this.coupNum;
    }
    // 거리 가까운 순 = 오름차순
    return (int)(this.dist - s.dist);
  }
}

public class p2 {

  static double getDistance(int userX, int userY, int shopX, int shopY) {
    shopX = (shopX / 100) * 100;
    shopY = (shopY / 100) * 100;
    return  Math.sqrt((userX - shopX) * (userX - shopX) + (userY - shopY) * (userY - shopY));
   // return  (userX - shopX) * (userX - shopX) + (userY - shopY) * (userY - shopY);
  }

  public static void main(String[] args) throws Exception {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      StringTokenizer tokens = new StringTokenizer(br.readLine().trim());
      int userX = Integer.parseInt(tokens.nextToken());
      int userY = Integer.parseInt(tokens.nextToken());
      int shopCount = Integer.parseInt(tokens.nextToken());

      Shop[] shops = new Shop[shopCount];
      for (int i = 0; i < shopCount; i++) {
        tokens = new StringTokenizer(br.readLine().trim());
        int shopX = Integer.parseInt(tokens.nextToken());
        int shopY = Integer.parseInt(tokens.nextToken());
        String name = tokens.nextToken();
        int coupon = Integer.parseInt(tokens.nextToken());

        double dist = getDistance(userX, userY, shopX, shopY);
        shops[i] = new Shop(shopX, shopY, dist, name, coupon);
      }
      Arrays.sort(shops);

      StringBuilder sb = new StringBuilder();
      for(Shop s: shops){
        sb.append(s.shopX + " " + s.shopY + " " + s.name + " " + s.coupNum + " "+ s.dist + "\n");
      }
      System.out.println(sb);
    }
  }
}



