package com.ifhc.demo;

import com.ifhc.util.BCUtil;
import com.ifhc.util.JBUtil;
import org.junit.Test;

import java.util.List;

public class BCUtilTest {
    @Test
    public void testBC(){
        Object o1 = true ? new Integer(1) : new Integer(2);
        Object o2;
        if (true) {
            o2 = new Integer(1);
        } else {
            o2 = new Double(2.0);
        }
        System.out.print(o1);
        System.out.print(" ");
        System.out.print(o2);

    }

    @Test
    public void testJB(){
        String str="教育 公平陈浩         ";
        List<String> token= JBUtil.toWord(str.replace(" ",""));
        System.out.println(token);
    }
}
