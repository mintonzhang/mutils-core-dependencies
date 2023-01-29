package cn.minsin.core.constant;

/**
 * spring中定时器的cron表达式
 *
 * @author minton.zhang
 * @since 2019/9/25 16:13
 */
public interface CronConstant {

	/**
	 * 每秒执行
	 */
	String EVERY_SECOND = "0/1 * * * * ?";
	/**
	 * 每五秒执行
	 */
	String EVERY_FIVE_SECOND = "0/5 * * * * ?";

	/**
	 * 每小时执行
	 */
	String EVERY_HOUR = "0 0 0/1 * * ?";

	/**
	 * 每2小时执行
	 */
	String EVERY_TWO_HOUR = "0 0 0/2 * * ?";

	/**
	 * 每一分钟执行一次
	 */
	String EVERY_MINUTE = "0 */1 * * * ?";
	/**
	 * 每五分钟执行一次
	 */
	String EVERY_FIVE_MINUTE = "0 */5 * * * ?";
	/**
	 * 每十分钟执行一次
	 */
	String EVERY_TEN_MINUTE = "0 */10 * * * ?";
	/**
	 * 每30分钟执行一次
	 */
	String EVERY_THIRTY_MINUTE = "0 */30 * * * ?";

	/**
	 * 每月一号零点执行
	 */
	String EVERY_MONTH_FIRST_DAY = "0 0 0 1 * ?";

    /**
     * 每年 1月1日执行
     */
    String EVERY_YEAR = "0 0 0 1 1 ? *";

    /**
     * 每天零点执行一次
     */
    String EVERY_DAY = "0 0 0 */1 * ?";
    /**
     * 每天n点执行一次
     */
    String EVERY_EARLY_MORNING_0 = "0 0 0 * * ?";
    String EVERY_EARLY_MORNING_1 = "0 0 1 * * ?";
    String EVERY_EARLY_MORNING_2 = "0 0 2 * * ?";
    String EVERY_EARLY_MORNING_3 = "0 0 3 * * ?";
    String EVERY_EARLY_MORNING_4 = "0 0 4 * * ?";
    String EVERY_EARLY_MORNING_5 = "0 0 5 * * ?";
    String EVERY_EARLY_MORNING_6 = "0 0 6 * * ?";
    String EVERY_EARLY_MORNING_7 = "0 0 7 * * ?";
    String EVERY_EARLY_MORNING_8 = "0 0 8 * * ?";
    String EVERY_EARLY_MORNING_9 = "0 0 9 * * ?";
    String EVERY_EARLY_MORNING_10 = "0 0 10 * * ?";
    String EVERY_EARLY_MORNING_11 = "0 0 11 * * ?";
    String EVERY_EARLY_MORNING_12 = "0 0 12 * * ?";
    String EVERY_EARLY_MORNING_13 = "0 0 13 * * ?";
    String EVERY_EARLY_MORNING_14 = "0 0 14 * * ?";
    String EVERY_EARLY_MORNING_15 = "0 0 15 * * ?";
    String EVERY_EARLY_MORNING_16 = "0 0 16 * * ?";
    String EVERY_EARLY_MORNING_17 = "0 0 17 * * ?";
    String EVERY_EARLY_MORNING_18 = "0 0 18 * * ?";
    String EVERY_EARLY_MORNING_19 = "0 0 19 * * ?";
    String EVERY_EARLY_MORNING_20 = "0 0 20 * * ?";
    String EVERY_EARLY_MORNING_21 = "0 0 21 * * ?";
    String EVERY_EARLY_MORNING_22 = "0 0 22 * * ?";
    String EVERY_EARLY_MORNING_23 = "0 0 23 * * ?";
}
