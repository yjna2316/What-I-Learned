package DynamicProgramming;

import java.util.*;

/*
 * ���� ����.O(n^3) ���� ù��°�� ���ſ� �ͺ��� ����, �״����� �ι�°�� ���ſ�ͺ��� ����, .. ���������� ���ſ�ͺ��� ���� �� �� , ���� �ּ� ������ �� ���
 */
public class Weight {
    public boolean selectWeight(int[][] data, int[] result, int N, int W) {

        int min = Integer.MAX_VALUE;
        int[] nums;
        for (int startWeightIndex = N - 1; startWeightIndex >= 0 ; --startWeightIndex) {
            int remainW = W;
            int num = 0;
                nums = new int[N]; // ��ü �Ź� ���� ���ص��Ǵ�  �� ���� ����� ������ -> dp�̿�,,(���� �Ǵ� �������迭�̿�)
                for (int j = startWeightIndex; j >= 0; --j) {
                    int selectW = data[j][0];
                    while (remainW >= selectW && nums[j] < data[j][1] ) {
                    remainW = remainW - selectW;
                    nums[j] ++;
                    num ++;
                    }
                }

            if (remainW == 0 && num < min) {
                min = num;
                // result = nums assigning a new array can't affect to original array(passed parameter, result)
                for (int k = 0; k < N; k ++) {
                    result[k] = nums[k];
                }
                //
            }
        }
        return min != Integer.MAX_VALUE;
    }

    public static void main(String[] args) throws Exception {
        Weight main =  new Weight();
        try (Scanner sc = new Scanner(System.in)) {
            int TC = sc.nextInt();

            for (int tc = 1; tc <= TC; ++tc) {
                int W = sc.nextInt();
                int N = sc.nextInt();
                int[][] data = new int[N][2];
                int[] result = new int[N];
                for (int i = 0; i < N; ++i) {
                    data[i][0] = sc.nextInt();
                    data[i][1] = sc.nextInt();
                }
                System.out.println("#" + tc);
                if (!main.selectWeight(data, result, N, W)) {
                    System.out.println("FAIL");
                } else {
                    for (int i = 0; i < N; i ++) {
                        System.out.println(data[i][0] + " " + result[i]);
                    }
                }
            }
        }
    }
}