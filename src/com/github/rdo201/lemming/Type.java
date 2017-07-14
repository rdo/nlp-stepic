package com.github.rdo201.lemming;

import java.util.Arrays;
import java.util.List;

public enum Type {
    S("мн","мо","жо","#1","с", "м", "ж", "мо-жо"),
    A("п"),
    V("св/нсв", "св", "нсв", "св-нсв", "_повел"),
    PR("предл"),
    CONJ("союз"),
    ADV("");

    private List<String> markers;

    Type(String... markers) {
        this.markers = Arrays.asList(markers);
    }

    public static Type getTypeByCode(String code) {
        if(S.markers.contains(code)) {
            return S;
        } else if(A.markers.contains(code)) {
            return A;
        }
        else if(V.markers.contains(code)) {
            return V;
        }
        else if(PR.markers.contains(code)) {
            return PR;
        }
        else if(CONJ.markers.contains(code)) {
            return CONJ;
        } else {
            return ADV;
        }
    }
}
