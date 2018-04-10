/**
 * 배열이 주어졌을 때 
 * 빈도수 순으로 정렬하되 빈도가 같으면 non-decreasing 정렬
 * {4,3,3,2,2,1} -> {1,4,2,2,3,3}
 */
import java.util.*;
class sortByFrequencyUsingMap {
  private static void customSort(int[] arr){
    // 숫자별 빈도수 카운트 (number, frequency)
    Map<Integer, Integer> countFreq = new HashMap<>();
    for (int num : arr) {
      // countFreq.put(num, countFreq.getOrDefault(num, 0) + 1);
      if ( countFreq.containsKey(num)) {
       countFreq.put(num, 1);
      } else {
        int freq = countFreq.get(num);
       countFreq.put(num, freq + 1);
      }
    }

    // sort map by values(frequency) in another map: (freq, numbers) 
    Map<Integer, List<Integer>> sortByFreq = new HashMap<>();
    for (int keyNum : frequency.keySet()) {
      int freq = frequency.get(keyNum);
      if (!sortByFreq.containsKey(freq)) {
        sortByFreq.put(freq, new ArrayList<>());
      }
      sortByFreq.get(freq).add(keyNum);
    }

    // 방법1: keySet으로 iterate
    StringBuilder out = new StringBuilder();
    for (int freqKey : sortByFreq.keySet()) {
      List<Integer> sortedList = sortByFreq.get(freqKey);
      // sort in ascending order
      Collections.sort(sortedList);
      // freq수 만큼 append
      for (int num : sortedList) {
        for (int i = 1; i <= freqKey; ++i) {
          out.append(num + "\n");
        }
      }
    }

    // 방법2: Map Entry로 iterate
    for (Map.Entry<Integer, List<Integer>> e : sortByFreq.entrySet()) {
      int freq = e.getKey();
      List<Integer> sortedList = e.getValue();
      // sort in ascending order 
      Collections.sort(sortedList);
      // freq수 만큼 append
      for (int num : sortedList) {
        for (int i = 1; i <= freq; ++i) {
          ans.append(num + "\n");
        }
      }
    }
}