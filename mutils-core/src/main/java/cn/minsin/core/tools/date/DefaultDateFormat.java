package cn.minsin.core.tools.date;

public enum DefaultDateFormat implements DateFormat {

    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyyMMdd("yyyyMMdd"),
    yyyy年MM月dd日("yyyy年MM月dd日"),
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm");

    private String format;

    DefaultDateFormat(String format) {
        this.format = format;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
