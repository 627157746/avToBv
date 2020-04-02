package com.zhb;

import cn.hutool.core.util.NumberUtil;

/**
 * Hello world!
 */
public class App {
    private static String table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    private static int[] s = {11, 10, 3, 8, 4, 6};
    private static long xor = 177451812L;
    private static long add = 8728348608L;

    private static String avToBv(String av) throws Exception {

        long formatAv = 0L;
        try {
            if (NumberUtil.isNumber(av)) {
                formatAv = Long.parseLong(av);
            } else {
                formatAv = Long.parseLong(av.replace("av", "").replace("AV", ""));
            }
        } catch (NumberFormatException e) {
            throw new Exception("不是正确的av号");
        }

        String[] result = {"B", "V", "1", "", "", "4", "", "1", "", "7", "", ""};
        StringBuilder stringBuilder = new StringBuilder();
        long num = (formatAv ^ xor) + add;
        for (int i = 0; i < 6; i++) {
            Long round = Math.round(Math.floor(num / Math.pow(58, i)) % 58);
            result[s[i]] = String.valueOf(table.charAt(round.intValue()));
        }
        for (String s1 : result) {
            stringBuilder.append(s1);
        }
        return stringBuilder.toString();
    }

    private static String bvToAv(String bv) throws Exception {
        String formatBv = "";
        if (bv.length() == 12) {
            formatBv = bv;
        } else if (bv.length() == 10) {
            formatBv = "BV" + bv;
        } else {
            throw new Exception("不是正确的bv号");
        }
        if (formatBv.matches("/[Bb][Vv][fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF]{10}/gu")) {
            throw new Exception("不是正确的bv号");
        }
        long result = 0;
        for (int i = 0; i < 6; i++) {
            result += Math.round(table.indexOf(String.valueOf(formatBv.charAt(s[i]))) * Math.pow(58, i));
        }
        return "AV" + (result - add ^ xor);
    }
}
