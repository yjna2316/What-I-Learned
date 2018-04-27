/**
 * 169. Majority Element
 * https://leetcode.com/problems/majority-element/description/
 */
import java.util.*;
import org.junit.*;

public class MajorityElement {
  public  int majorityElement(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    int majorityNum = 0;
    for (int num : nums) {
      if (map.containsKey(num)) {
        map.put(num, map.get(num) + 1);
      } else {
        map.put(num, 1);
      }
      if (map.get(num) > nums.length / 2) {
        majorityNum = num;
      }
    }
    return majorityNum;
  }
  
  @Test
  public void testMajorityElement() {
    int[] nums = {3, 2, 3};
    Assert.assertEquals(3, majorityElement(nums));
  }
}
