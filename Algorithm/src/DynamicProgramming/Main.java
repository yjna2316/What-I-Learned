package DynamicProgramming;

import java.util.Scanner;
class Main {
    public boolean selectWeight(int[][] data, int[] result, int N, int W) {
        int[] d = new int[W + 1];
        int[][] remainNums = new int[W + 1][N];
        for (int w = 1; w <= W; ++w) {
            int min = Integer.MAX_VALUE;
            int minIndex = min;
            for (int i = 0; i < N; ++i) {
                int selected = data[i][0];
                if (selected > w) {
                    break; // 다음 무게 d[n] , n 증가
                }
                if (remainNums[w - selected][i] >= data[i][1]) { // 만들수 없음. 다음 무게 selected
                    continue;
                }

                if (d[w - selected] + 1 < min) {
                    d[w] = d[w - selected] + 1;
                    min = d[w];
                    minIndex = i;
                    //remainNums[w][i] = remainNums[w - selected][i] + 1;// 이전 상태에서 1더해야함
                    remainNums[w] = remainNums[w - selected];

                }
            }
            if (min != Integer.MAX_VALUE) {
                ++remainNums[w][minIndex];// 이전 상태에서 1더해야함 최종
            }
        }

        for (int i = 0; i < N; ++i) {
            result[i] = remainNums[W][i];
        }
        return d[W] == 0;
    }


    public static void main(String[] args) throws Exception {
        Main main = new Main();
        try (Scanner scan = new Scanner(System.in)) {
            int TC = scan.nextInt();
            for (int tc = 1; tc <= TC; tc++) {
                int W = scan.nextInt();
                int N = scan.nextInt();
                int[][] data = new int[N][2];
                int[] result = new int[N];
                for (int i = 0; i < N; ++i) {
                    data[i][0] = scan.nextInt();
                    data[i][1] = scan.nextInt();
                }
                System.out.println("#" + tc);
                if (!main.selectWeight(data, result, N, W)) {
                    System.out.println("FAIL");
                } else {
                    for (int i = 0; i < N; i++) {
                        System.out.println(data[i][0] + " " + result[i]);
                    }
                }
            }
        }
    }
}