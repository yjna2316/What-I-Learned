/**
 * 거리 순(오름차순) 같으면 쿠폰수(내림차순) 이것도 같으면 이름순(오름차순) 정렬
 */
import java.io.*;
import java.util.*;

public class p2_test {
  static class Shop implements Comparable<Shop> {
    int shopX;
    int shopY;
    String shopName;
    int couponNum;
    int distance;

    public Shop(int userX, int userY, int shopX, int shopY, String name, int coupon) {
      this.shopX = shopX;
      this.shopY = shopY;
      shopName = name;
      couponNum = coupon;
      distance = getDistance(userX, userY, shopX, shopY);
    }

    public int getDistance(int userX, int userY, int shopX, int shopY) {
      shopX = (shopX / 100) * 100;
      shopY = (shopY / 100) * 100;
      userX = (userX / 100) * 100;
      userY = (userY / 100) * 100;
      return (shopX - userX) * (shopX - userX) + (shopY - userY) * (shopY - userY);
    }

    @Override
    public int compareTo(Shop s) {
      if (this.distance == s.distance) {
        if (this.couponNum == s.couponNum) {
          return (this.shopName).compareTo(s.shopName);
        }
        return s.couponNum - this.couponNum;
      }
      return this.distance - s.distance;
    }

    public String toString() {
      return shopX + " " + shopY + " " + shopName + " " + couponNum + " " + distance;
    }
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
        shops[i] = new Shop(userX, userY, shopX, shopY, name, coupon);
      }
      Arrays.sort(shops);
      StringBuilder sb = new StringBuilder();
      for (Shop s : shops) {
        sb.append(s.toString() + "\n");
      }
      System.out.println(sb);
    }
  }
}



