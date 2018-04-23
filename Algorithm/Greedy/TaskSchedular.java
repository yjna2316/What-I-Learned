/**
 * 621. Task Scheduler
 * https://leetcode.com/problems/task-scheduler/
 * Tag: Greedy, Queue, Array
 */

public class TaskSchedular {
  public static int leastInterval(char[] tasks, int n) {
    int maxFreq = 0, maxTaskCount = 0;
    int[] numOfTheTask = new int[26];
    for (char task : tasks) {
      ++numOfTheTask[task - 'A'];
      if (numOfTheTask[task - 'A'] > maxFreq) {
        maxFreq = numOfTheTask[task - 'A'];
        maxTaskCount = 1;
      } else if (numOfTheTask[task - 'A'] == maxFreq) {
        ++maxTaskCount;
      }
    }
    return Math.max(tasks.length, (n + 1) * (maxFreq - 1) + maxTaskCount);
  }

  public static void main(String[] args) {
    char[] tasks = {'A','A','A','B','B','B'};
    System.out.println(leastInterval(tasks, 2));
  }
}
