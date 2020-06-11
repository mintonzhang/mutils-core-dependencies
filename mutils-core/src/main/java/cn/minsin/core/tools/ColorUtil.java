package cn.minsin.core.tools;

import cn.minsin.core.exception.MutilsException;

import java.awt.Color;


/**
 * @author: minton.zhang
 * @since: 2019/12/6 14:37
 * 0.1.2.RELEASE
 */
public class ColorUtil {

    private final static int RGB_BEGIN = 0;

    private final static int RGB_END = 255;

    /**
     * 16进制字符串转换成颜色
     *
     * @param binary16String 16 进制颜色字符串
     */
    public static Color binary16ToColor(String binary16String) {
        try {
            int[][] ints = parseColor(binary16String);
            MutilsException.throwException(!isColor(ints), String.format("The '%s' is not a color.", binary16String));
            return new Color(ints[0][0], ints[1][0], ints[2][0]);
        } catch (Exception e) {
            // unnecessary
        }
        return null;
    }

    public static boolean isColor(String binary16String) {
        try {
            return isColor(parseColor(binary16String));
        } catch (Exception e) {
            // unnecessary
        }
        return false;
    }

    protected static boolean isColor(int[][] colors) {
        try {
            int red = colors[0][0];
            int green = colors[1][0];
            int blue = colors[2][0];
            return (red >= RGB_BEGIN && red <= RGB_END) && (green >= RGB_BEGIN && green <= RGB_END) && (blue >= RGB_BEGIN && blue <= RGB_END);
        } catch (Exception e) {
            // unnecessary
        }
        return false;
    }

    protected static int[][] parseColor(String binary16String) {
        int length = binary16String.length();
        if (length <= 4) {
            char c1 = binary16String.charAt(1);
            char c2 = binary16String.charAt(2);
            char c3 = binary16String.charAt(3);
            binary16String = "#" + c1 + c1 + c2 + c2 + c3 + c3;
        }
        String str1 = binary16String.substring(1, 3);
        String str2 = binary16String.substring(3, 5);
        String str3 = binary16String.substring(5, 7);
        int red = Integer.parseInt(str1, 16);
        int green = Integer.parseInt(str2, 16);
        int blue = Integer.parseInt(str3, 16);
        return new int[][]{{0, red}, {1, green}, {2, blue}};
    }
}
