package cn.minsin.core.constant;

/**
 * spring中定时器的cron表达式
 *
 * @Author: minton.zhang
 * @Date: 2019/9/25 16:13
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
	 * 每一分钟执行一次
	 */
	String EVERY_MINUTE = "0 */1 * * * ?";
	/**
	 * 每五分钟执行一次
	 */
	String EVERY_FIVE_MINUTE = "0 */5 * * * ?";

	/**
	 * 每月一号零点执行
	 */
	String EVERY_MONTH_FIRST_DAY = "0 0 0 1 * ?";

	/**
	 * 每年 1月1日执行
	 */
	String EVERY_YEAR = "0 0 1 1 * ?";

	/**
	 * 每天零点执行一次
	 */
	String EVERY_DAY = "0 0 0 */1 * ?";
	/**
	 * 每个周日零点执行一次
	 */
	String EVERY_SUNDAY = "0 0 0 */1 0 ?";
	/**
	 * 每天3点执行一次
	 */
	String EVERY_EARLY_MORNING_3 = "0 0 0 */3 * ?";
	/**
	 * 每天4点执行一次
	 */
	String EVERY_EARLY_MORNING_4 = "0 0 0 */4 * ?";
}
