package cn.minsin.core.algorithm.dfa;

import cn.minsin.core.constant.SuppressWarningsTypeConstant;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 使用dfa算法 实现敏感词过滤
 *
 * @author minton.zhang
 * @date: 2020/4/19 21:09
 * @description:
 */
@SuppressWarnings({SuppressWarningsTypeConstant.RAW_TYPES, SuppressWarningsTypeConstant.UNCHECKED})
public class SensitiveWordsFilterUtil {


    /**
     * 敏感词集合
     */
    protected Map sensitiveWordMap;

    /**
     * 现在的关键字合集
     */
    protected Collection<String> nowSensitiveWord;

    /**
     * 敏感词size
     */
    protected int size;

    /**
     * 敏感词类型
     */
    protected SensitiveType sensitiveType;

    /**
     * 不允许外部初始化 只允许继承修改
     */
    protected SensitiveWordsFilterUtil() {

    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */
    public static synchronized SensitiveWordsFilterUtil init(@NonNull Collection<String> sensitiveWordSet) {
        return init(sensitiveWordSet, SensitiveType.ALL);
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */
    public static synchronized SensitiveWordsFilterUtil init(@NonNull Collection<String> sensitiveWordSet, @NonNull SensitiveType sensitiveType) {
        SensitiveWordsFilterUtil sensitiveWordsFilterUtil = new SensitiveWordsFilterUtil();
        sensitiveWordsFilterUtil.initSensitiveWordMap(sensitiveWordSet);
        sensitiveWordsFilterUtil.sensitiveType = sensitiveType;
        return sensitiveWordsFilterUtil;
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */

    private void initSensitiveWordMap(Collection<String> sensitiveWordSet) {
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap<>(sensitiveWordSet.size());
        String key;
        Map nowMap;
        Map newWorMap;
        //迭代sensitiveWordSet

        for (String s : sensitiveWordSet) {
            //关键字
            key = s;
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //库中获取关键字
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值，用于下一个循环获取
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }

    }

    /**
     * 重新初始化初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */
    public void reinitialize(@NonNull Collection<String> sensitiveWordSet) {
        if (!sensitiveWordSet.isEmpty()) {
            this.nowSensitiveWord.addAll(sensitiveWordSet);
            this.size = this.nowSensitiveWord.size();
            this.sensitiveWordMap.clear();
            this.initSensitiveWordMap(this.nowSensitiveWord);
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     */
    public boolean contains(String txt, MatchType matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.checkSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
                //判断是否判断匹配第一个
                if (MatchType.FIRST_MATCH.equals(matchType)) {
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    public boolean contains(String txt) {
        return contains(txt, MatchType.FIRST_MATCH);
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     */
    public Set<String> getSensitiveWord(String txt, MatchType matchType) {
        Set<String> sensitiveWordList = new HashSet<>();

        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int length = this.checkSensitiveWord(txt, i, matchType);
            if (length > 0) {//存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                //判断是否判断匹配第一个
                if (MatchType.FIRST_MATCH.equals(matchType)) {
                    break;
                }
                i = i + length - 1;//减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt 文字
     * @return
     */
    public Set<String> getSensitiveWord(String txt) {
        return getSensitiveWord(txt, MatchType.ALL_MATCH);
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @param matchType   敏感词匹配规则
     * @return
     */
    public String replaceSensitiveWord(String txt, char replaceChar, MatchType matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = this.getSensitiveWord(txt, matchType);
        String replaceString;
        for (String word : set) {
            replaceString =this.getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @return
     */
    public String replaceSensitiveWord(String txt, char replaceChar) {
        return replaceSensitiveWord(txt, replaceChar, MatchType.ALL_MATCH);
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @param matchType  敏感词匹配规则
     * @return
     */
    public String replaceSensitiveWord(String txt, String replaceStr, MatchType matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = this.getSensitiveWord(txt, matchType);
        for (String word : set) {
            resultTxt = resultTxt.replaceAll(word, replaceStr);

        }
        return resultTxt;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @return
     */
    public String replaceSensitiveWord(String txt, String replaceStr) {
        return this.replaceSensitiveWord(txt, replaceStr, MatchType.ALL_MATCH);
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     */
    protected String getReplaceChars(char replaceChar, int length) {
        StringBuilder resultReplace = new StringBuilder(String.valueOf(replaceChar));
        for (int i = 1; i < length; i++) {
            resultReplace.append(replaceChar);
        }

        return resultReplace.toString();
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    protected int checkSensitiveWord(String txt, int beginIndex, MatchType matchType) {
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            if (nowMap == null) {
                break;
            } else {
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (MatchType.FIRST_MATCH.equals(matchType)) {
                        break;
                    }
                }
            }
        }
        //2字以上算词 此时只替换词 单字跳过
        if (this.sensitiveType.equals(SensitiveType.WORDS)) {
            if (matchFlag < 2 || !flag) {//长度必须大于等于1，为词
                matchFlag = 0;
            }
        }

        return matchFlag;
    }

}
