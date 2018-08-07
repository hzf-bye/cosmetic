package com.cos.common.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

/**
 * @description:
 * @author: hzf
 * @date: 2018/1/22 下午5:46
 */
public class PriceUtil {

        /**
         * 12345 -- >123.45
         *
         * @param cent
         * @return
         */
        public static String formatCNY(Long cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            return format.format(cent / 100.00);
        }

        public static String formatCNYR(Long cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            double price = 0.0;
            try {
                price = cent / 100.00;
            } catch (Exception e) {
            }
            return Long.toString(Math.round(price));
        }

        public static String formatCNY(Object cent) {
            if (cent == null) {
                return null;
            }
            Long c = Long.valueOf(String.valueOf(cent));
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            return format.format(c / 100.00);
        }

        public static String format(Long cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            return format.format(cent / 100.00);
        }

        /**
         * 将分变成元
         * 12345678L  -> 123456.78
         *
         * @param cent
         * @return
         */
        public static String formatNoGroup(Long cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setGroupingUsed(false);
            return format.format(cent / 100.00);
        }

        public static String formatYuan(Double yuan) {
            if (yuan == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.DOWN);
            format.setGroupingUsed(true);
            return format.format(yuan);
        }

        public static String formatWan(Long cent) {
            if (cent == null) {
                return null;
            }
            BigDecimal bigDecimal = new BigDecimal(cent).divide(new BigDecimal(1000000));
            return bigDecimal.toString();
        }

        public static String formatCNYW(Object cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.DOWN);
            format.setGroupingUsed(true);
            return format.format(Long.parseLong(String.valueOf(cent)) / 1000000.00);
        }

        /**
         * 1234567800L  -> 123,456.7
         * 123000000L  -> 12,300
         *
         * @param cent
         * @return
         */
        public static String formatCNYW(Long cent) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.DOWN);
            format.setGroupingUsed(true);
            return format.format(cent / 1000000.00);
        }

        /**
         * 元转万
         *
         * @param yuan
         * @return
         */
        public static String formatYToW(Long yuan) {
            if (yuan == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.DOWN);
            format.setGroupingUsed(true);
            return format.format(yuan / 10000.00);
        }

        public static Double formatYToW(Double yuan) {
            if (yuan == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.DOWN);
            format.setGroupingUsed(true);
            return Double.valueOf(format.format(yuan / 10000.00));
        }

        /**
         * @param price
         * @return
         */
        public static Long convertPrice(String price) {
            if (StringUtils.isEmpty(price)) {
                return null;
            }
            BigDecimal bigDecimal = new BigDecimal(price);
            return bigDecimal.multiply(new BigDecimal(1000000)).longValue();
        }

        public static Double filterCommaPrice(String price) {
            if (price == null || price.equals("")) {
                return null;
            }
            String[] splitStr = price.split(",");
            if (splitStr.length == 0) {
                return null;
            }
            StringBuffer sbf = new StringBuffer();
            for (String s : splitStr) {
                sbf.append(s);
            }
            return Double.valueOf(sbf.toString());
        }

        /**
         * 将元变成分
         * 123456.78  ->12345678L
         * 元转换进入数据库
         *
         * @param price
         * @return
         */
        public static Long convertPriceYuan(String price) {
            if (StringUtils.isEmpty(price)) {
                return null;
            }
            BigDecimal bigDecimal = new BigDecimal(price);
            return bigDecimal.multiply(new BigDecimal(100)).longValue();
        }

        /**
         * 将带有逗号的元转换成分
         *
         * @param price
         * @return
         */
        public static Long convertPriceYuanWithComma(String price) {
            if (StringUtils.isEmpty(price))
                return null;
            String _price = price.replace(",", "");
            BigDecimal decimal = new BigDecimal(_price);
            return decimal.multiply(new BigDecimal(100)).longValue();
        }

        public static long parseCentFromW(String w) {
            if (w == null) {
                return 0;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            try {
                return (format.parse(w)).longValue() * 1000000;
            } catch (Exception e) {

            }
            return -1;
        }

        public static long parseCentFromY(String w) {
            if (w == null) {
                return 0;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            try {
                return Math.round(format.parse(w).doubleValue() * 100);
            } catch (Exception e) {
                return -1;
            }
        }

        public static String formatYuan(Long cent, boolean groupingUsed, int maximumFractionDigits) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setGroupingUsed(groupingUsed);
            format.setMaximumFractionDigits(maximumFractionDigits);
            return format.format(cent / 100.00);
        }

        /**
         * @param cent
         * @param groupingUsed          是否分组
         * @param maximumFractionDigits 最大小数位
         * @param minimumFractionDigits 最小小数位
         * @return
         */
        public static String formatYuan(Long cent, boolean groupingUsed, int maximumFractionDigits, int minimumFractionDigits) {
            if (cent == null) {
                return null;
            }
            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setGroupingUsed(groupingUsed);
            format.setMaximumFractionDigits(maximumFractionDigits);
            format.setMinimumFractionDigits(minimumFractionDigits);
            return format.format(cent / 100.00);
        }


        public static String formatWYuan(Long cent) {
            if (cent == null) {
                return null;
            }

            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setGroupingUsed(false);
            return format.format(cent / 1000000.00);
        }

        public static String formatWYuanWithPreTen(Long cent) {
            if (cent == null) {
                return null;
            }

            DecimalFormat format = new DecimalFormat();
            format.setCurrency(Currency.getInstance(Locale.CHINA));
            format.setGroupingUsed(false);
            return format.format(cent / 1000000.000);
        }


        private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾元角分";

        private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

        private static final double MAX_VALUE = 9999999999999.99D;

        /**
         * 1200 -->壹拾贰元整
         *
         * @param cent
         * @return
         */
        public static String toRMB(long cent) {
            if (cent < 0 || cent > MAX_VALUE) {
                return "数值位数过大!";
            }
            if (cent == 0) {
                return "零元整";
            }
            String strValue = cent + "";
            StringBuilder sbd = new StringBuilder();
            // i用来控制数(位)
            int i = 0;
            int len = strValue.length();
            // u用来控制单位(三位)
            int u = UNIT.length() - len;
            boolean isZero = false;
            for (; i < len; i++, u++) {
                char ch = strValue.charAt(i);
                if (ch == '0') {
                    isZero = true;
                    if (UNIT.charAt(u) == '亿' || UNIT.charAt(u) == '万' || UNIT.charAt(u) == '元') {
                        sbd.append(UNIT.charAt(u));
                        isZero = false;
                    }
                } else {
                    if (isZero) {
                        sbd.append("零");
                        isZero = false;
                    }
                    sbd.append(DIGIT.charAt(ch - '0')).append(UNIT.charAt(u));
                }
            }

            String result = sbd.toString();
            if (!result.endsWith("分")) {
                result = result + "整";
            }
            result = result.replaceAll("亿万", "亿");
            return result;
        }

        /**
         * 格式化保留2位小数的百分比
         *
         * @param decimal
         * @return
         */
        public static String getPercent(Double decimal) {
            try {
                if (decimal == null) {
                    return "0.00%";
                }
                NumberFormat nt = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                nt.setMinimumFractionDigits(2);
                return nt.format(decimal);
            } catch (Exception e) {
                return "0.00%";
            }
        }

        public static String format2(double decimal) {
            try (Formatter formatter = new Formatter()) {
                return formatter.format("%.2f", decimal).toString();
            }
        }

        public static Double div(double v1, double v2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException(
                        "The scale must be a positive integer or zero");
            }
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }


        public static Double round(double v, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The scale must be a positive integer or zero");
            }
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }


        public static String formatNoGroupWithTwoDigit(Long cent) {
            String c = formatNoGroup(cent);
            return String.format("%.2f", Double.valueOf(c));
        }

    public static void main(String[] args) {
        System.out.println(PriceUtil.convertPriceYuan("23.3232"));
    }

}
