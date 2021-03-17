package com.ifhc.demo;

import com.ifhc.util.BCUtil;
import org.junit.Test;

public class BCUtilTest {
    @Test
    public void testBC(){
        String str="java";
        System.out.println(str);
        str= BCUtil.ToSBC(str);
        System.out.println(str);
    }
}
