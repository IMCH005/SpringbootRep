package com.ifhc.util;

public class BCUtil {
    //半角
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String ToSBC(String input)
    {
        //半角转全角：
        char[] c=input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i]==32)
            {
                c[i]=(char)12288;
                continue;
            }
            if (c[i]<127)
                c[i]=(char)(c[i]+65248);
        }
        return new String(c);
    }
}
