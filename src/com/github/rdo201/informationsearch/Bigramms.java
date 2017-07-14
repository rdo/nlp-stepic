package com.github.rdo201.informationsearch;

public class Bigramms {
    public static void main(String[] args) {
        System.out.println(logsum(21,11500,1,1600,1,1501,301,6500,16,4500,6,1550));
    }

    public static double log(double a, double b) {
        return Math.log(a/b);
    }

    public static double logsum(double ...a) {
        double result = 0;
        for (int i=0; i<a.length; i+=2) {
            result +=log(a[i],a[i+1]);
        }
        return result;
    }
}
