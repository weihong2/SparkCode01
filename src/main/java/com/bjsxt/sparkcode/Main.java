package com.bjsxt.sparkcode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();
        int n = Integer.parseInt(line);
        int[][] area = new int[n][n];

        for (int i = 0; i < n; i++) {
            line = scanner.nextLine();
            String[] split = line.split(",");
            if (split.length != n) {
                throw new IllegalArgumentException("错误输入");
            }
            int j = 0;
            for (String num : split) {
                area[i][j++] = Integer.parseInt(num);
            }
        }

        int minimumTimeCost = getMinimumTimeCost(n,area);
        System.out.println(minimumTimeCost);
    }

    /** 请完成下面这个函数，实现题目要求的功能 **/
    /** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^  **/
    private static int getMinimumTimeCost(int n, int[][] area) {
        int[] arr = new int[n/2];
        for (int i = 1; i < n; i++) {
            int k=0;
            for (int j = 0; j < n; j++) {
                arr[k++] += area[i][j];
            }
            i++;
        }
        int min = arr[0];
        for (int i = 0; i < n / 2; i++) {
            if(min > arr[i])
                min = arr[i];
        }
        return min;
    }
}