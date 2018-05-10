package com.syl.tb.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class Z{
    int K;
    int A;
    public Z(int K,int A){
        this.K = K;
        this.A = A;
    }
}
public class A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int num2 =0;
        List<List<Z>> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            num2 = sc.nextInt();
            List<Z> l = new ArrayList<>();
            for (int j = 0; j < num2; j++) {
                l.add(new Z(sc.nextInt(),sc.nextInt()));
            }
            list.add(l);
        }
        print(list);
    }
    public static void print(List<List<Z>>  list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.println(22);
            }
        }
}

    public static boolean judge(int num){
        if (num%2 == 0)
            return true;
        if (num <=3) return false;
        num-=2;
        if (num<=3) return true;
        int a = (int) (Math.log(num) / Math.log(2));
        num-=Math.pow(2,a);
        if (num<=0){
            return false;
        }else {
            if (num<=3){
                return true;
            }
        }
        return false;
    }
//    public static char[] E = {'L','S','B','Q','W'};
//    public static void print(int num) {
//        if (num<10) {
//            if (num == 0)
//                System.out.println("L");
//            else
//                System.out.println(num);
//            return;
//        }
//
//        StringBuffer sb = new StringBuffer();
//        int t=0;
//        int count = 0;
//        boolean b = false;
//        while (num!=0){
//            t = num%10;
//            num=num/10;
//            if (t != 0&&count!=5){
//                if (count!=0) sb.append(E[count]);
//                if (count ==0 ) b = true;
//                sb.append(t);
//            }else if (t==0&&count!=5){
//                sb.append(E[0]);
//            }
//            count++;
//        }
//        char[] chars = sb.toString().toCharArray();
//
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < chars.length-1; i++) {
//            if (chars[i]==chars[i+1]){
//                list.add(i);
//            }
//        }
//
//        for (Integer i:list) {
//            sb.replace(i,i+1," ");
//        }
//        String str = sb.reverse().toString().replace(" ","");
//        if (b)
//            System.out.println(str);
//        else
//            System.out.println(str.substring(0,str.length()-1));
//    }
}
