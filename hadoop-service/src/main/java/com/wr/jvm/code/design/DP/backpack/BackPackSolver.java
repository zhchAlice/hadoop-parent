package com.wr.jvm.code.design.DP.backpack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Alice
 * @date: 2018/7/31.
 * @since: 1.0.0
 */
public class BackPackSolver {
    private List<BackPack> backPacks;
    private int totalWeight;
    private int n; //背包个数
    private int bestValue;
    private int[][] bestValues;  //bestValues[i][j]表示已有i个背包，当前可承重为j的情况下的最大价值
    private List<BackPack> bestBackPacks;

    public BackPackSolver(List<BackPack> backPacks, int totalWeight) {
        this.backPacks = backPacks;
        this.totalWeight = totalWeight;
        this.n = backPacks.size();
        if (null == bestValues) {
            bestValues = new int[n+1][totalWeight+1];
        }
        bestBackPacks = new ArrayList<>();
    }

    public void solve(){
        for (int j=0; j<=totalWeight; j++) {
            for (int i=0; i<=n; i++) {
                if (i == 0 || j == 0) {
                    bestValues[i][j] = 0;
                } else {
                    if (backPacks.get(i-1).getWight() > j) {  //如果第i个背包的重量超过当下可承重j，则最大价值存在于前i-1个背包中
                        bestValues[i][j] = bestValues[i-1][j];
                    } else {
                        int iweight = backPacks.get(i-1).getWight();
                        int ivalue = backPacks.get(i-1).getValue();
                        bestValues[i][j] = Math.max(bestValues[i-1][j], bestValues[i][j-iweight] +ivalue);
                    }
                }
            }
        }
        int tempWeight = totalWeight;
        for (int i=n; i>1; i--) {
            if (bestValues[i][tempWeight] == bestValues[i-1][tempWeight]){
            } else {
                int iweight = backPacks.get(i-1).getWight();
                int ivalue = backPacks.get(i-1).getValue();
                while (bestValues[i][tempWeight] == bestValues[i][tempWeight - iweight]+ivalue) {
                    bestBackPacks.add(backPacks.get(i-1));
                    tempWeight -= backPacks.get(i-1).getWight();
                    if (tempWeight < iweight) {
                        break;
                    }
                }

            }

        }
        bestValue = bestValues[n][totalWeight];
    }

    public int getBestValue() {
        return bestValue;
    }

    public int[][] getBestValues() {
        return bestValues;
    }

    public List<BackPack> getBestBackPacks() {
        return bestBackPacks;
    }

    public static void main(String[] args) {
        List<BackPack> packs = new ArrayList<>();
        packs.add(new BackPack(2,13));
        packs.add(new BackPack(1,7));
        packs.add(new BackPack(3,24));
        packs.add(new BackPack(2,15));
        packs.add(new BackPack(4,28));
        packs.add(new BackPack(5,33));
        packs.add(new BackPack(3,20));

        int totalWeight=10;
        BackPackSolver solver = new BackPackSolver(packs,totalWeight);
        solver.solve();
        System.out.println(" -------- 该背包问题实例的解: --------- ");
        System.out.println("最优值：" + solver.getBestValue());
        System.out.println("最优解【选取的背包】: ");
        System.out.println(solver.getBestBackPacks());
        System.out.println("最优决策矩阵表：");
        int[][] bestValues = solver.getBestValues();
        for (int i=0; i < bestValues.length; i++) {
            for (int j=0; j < bestValues[i].length; j++) {
                System.out.printf("%-5d", bestValues[i][j]);
            }
            System.out.println();
        }

    }
}
