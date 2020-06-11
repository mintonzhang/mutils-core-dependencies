package cn.minsin.core.algorithm.dfa;

/**
 * @author: minton.zhang
 * @date: 2020/4/19 21:54
 * @description: {@link SensitiveWordsFilterUtil}
 */
public enum MatchType {

    /**
     * 全词匹配 检索词库中最完整匹配项 一般用于全局查找
     * example:敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国人]
     */
    ALL_MATCH,
    /**
     * 第一个词匹配 检索词库中第一个匹配到的项 一般用于是否存在敏感词
     * example:敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国]人
     */
    FIRST_MATCH



}
