package com.k2.custom.cview;

import java.util.Random;

/**
 * Created by WYB on 2017/12/20 22:27.
 */

public class RandomMath {
    public static int math(int min,int max){
        Random random = new Random();
        int i = random.nextInt(max) % (max - min + 1) + min;
        return i;
    }
}
