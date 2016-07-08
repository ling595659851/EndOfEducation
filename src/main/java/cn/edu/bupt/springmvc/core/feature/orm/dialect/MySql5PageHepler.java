package cn.edu.bupt.springmvc.core.feature.orm.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FirenzesEagle on 2016/4/7 0007.
 */
public class MySql5PageHepler {
    /**
     * 得到查询总数的SQL
     *
     * @param querySelect 原始查询SQL语句
     * @return
     */
    public static String getCountString(String querySelect) {
        querySelect = getLineSql(querySelect);
        int orderIndex = getLastOrderInsertPoint(querySelect);

        int formIndex = getAfterFormInsertPoint(querySelect);
        String select = querySelect.substring(0, formIndex);

        if (select.toLowerCase().indexOf("select distinct") != -1 || querySelect.toLowerCase().indexOf("group by") != -1) {
            return new StringBuffer(querySelect.length()).append("select count(1) count from (").append(querySelect.substring(0, orderIndex)).append(" ) t").toString();
        } else {
            return new StringBuffer(querySelect.length()).append("select count(1) count ").append(querySelect.substring(formIndex, orderIndex)).toString();
        }
    }

    /**
     * 得到分页的SQL
     *
     * @param querySelect 原始查询SQL语句
     * @param offset      偏移量
     * @param limit       位置
     * @return 分页的SQL
     */
    public static String getLimitString(String querySelect, int offset, int limit) {
        querySelect = getLineSql(querySelect);
        String sql = querySelect + " limit " + offset + " ," + limit;
        return sql;
    }

    /**
     * 将SQL语句变成一条语句，并且每个单词的间隔都是1个空格
     *
     * @param sql SQL语句
     * @return 如果sql是NULL返回空，否则返回转化后的SQL
     */
    public static String getLineSql(String sql) {
        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
    }

    /**
     * 得到最后一个Order By插入点的位置
     *
     * @param querySelect 原始查询SQL语句
     * @return 最后一个Order By插入点的位置
     */
    public static int getLastOrderInsertPoint(String querySelect) {
        int orderIndex = querySelect.toLowerCase().lastIndexOf("order by");
        if (orderIndex == -1) {
            orderIndex = querySelect.length();
        }
        if (!isBracketCanPartnership(querySelect.substring(orderIndex, querySelect.length()))) {
            throw new RuntimeException("My SQL 分页必须要有Order by 语句!");
        }
        return orderIndex;
    }

    /**
     * 得到SQL第一个正确的FROM的插入点
     *
     * @param querySelect 原始查询SQL语句
     * @return 插入点的位置
     */
    private static int getAfterFormInsertPoint(String querySelect) {
        String regex = "\\s+FROM\\s+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(querySelect);
        while (matcher.find()) {
            int fromStartIndex = matcher.start(0);
            String text = querySelect.substring(0, fromStartIndex);
            if (isBracketCanPartnership(text)) {
                return fromStartIndex;
            }
        }
        return 0;
    }

    /**
     * 判断字符串中括号"()"是否匹配,不会判断排列顺序是否正确
     *
     * @param text 待判断的字符串
     * @return 括号"()"是否匹配
     */
    private static boolean isBracketCanPartnership(String text) {
        if (text == null || (getIndexOfCount(text, '(') != getIndexOfCount(text, ')'))) {
            return false;
        }
        return true;
    }

    /**
     * 得到一个字符在另一个字符串中出现的次数
     *
     * @param text 字符串
     * @param ch   字符
     * @return 出现的次数
     */
    private static int getIndexOfCount(String text, char ch) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            count = (text.charAt(i) == ch) ? count + 1 : count;
        }
        return count;
    }

}
