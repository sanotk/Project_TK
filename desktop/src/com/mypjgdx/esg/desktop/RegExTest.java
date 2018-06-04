package com.mypjgdx.esg.desktop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTest {
    public static void main(String[] args) {
        String regEx = "@icon\\( *id=(\\w+) *, *space=(\\d+) *\\)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("กดsdf @icon(id=juju,space=5) เพื่อตรวจสอบ @icon(id=xxx,space=11)");

        while(matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
}
