package com.company;

public class PsdAleatorios {

    public double numsD[];

    public PsdAleatorios(int x1, int k, int c, int g){
        numsD = congLineal(x1, k, c, g);
    }

    public double[] congLineal(int xi, int k, int c, int g) {
        int[] nums = new int[(int) Math.pow(2,g)];
        int a = 1 + 4 * k;
        int m = (int) Math.pow(2,g);
        for (int i = 0; i <= nums.length - 1; i++) {
            nums[i] = xi = (a * xi + c) % m;
            //nums [i] = (double) xi / (m - 1);
        }
        return pasFraccion(nums);
    }

    public double[] pasFraccion(int nums[]){
        double numsF[] = new double[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numsF[i] = (double) nums[i] / (nums.length - 1);
        }
        return numsF;
    }
}
