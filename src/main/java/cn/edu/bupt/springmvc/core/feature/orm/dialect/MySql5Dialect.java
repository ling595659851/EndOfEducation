package cn.edu.bupt.springmvc.core.feature.orm.dialect;

/**
 * Created by FirenzesEagle on 2016/4/7 0007.
 */
public class MySql5Dialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    /**
     * 得到分页sql
     *
     * @param sql    原始查询SQL语句
     * @param offset 偏移量
     * @param limit  位置
     * @return 分页的SQL
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }

    /**
     * 得到查询总数的SQL
     *
     * @param sql 原始查询SQL语句
     * @return
     */
    @Override
    public String getCountString(String sql) {
        return MySql5PageHepler.getCountString(sql);
    }

}
