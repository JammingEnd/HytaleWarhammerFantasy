package com.jammingmods.plugin.Helpers;

import java.util.Random;

public class Whf_LogicHelpers {
    public static boolean Roll(double chance){
        if(chance >= 1){
            return true;
        } else if (chance <= 0) {
            return false;
        } else {
            Random random = new Random();
            int n = random.nextInt(100);
            double dn = n * 0.01;
            if(dn >= chance) {
                return true;
            } else {
                return false;
            }
        }
    }
}
