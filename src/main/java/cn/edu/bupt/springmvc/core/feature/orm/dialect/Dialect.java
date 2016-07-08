package cn.edu.bupt.springmvc.core.feature.orm.dialect;

/**
 * 数据库方言抽象类
 *
 * Created by FirenzesEagle on 2016/4/7 0007.
 */
public abstract class Dialect {

    /**
     * 得到分页sql
     *
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public abstract String getLimitString(String sql,int offset,int limit);

    /**
     * 得到查询总数的SQL
     *
     * @param sql
     * @return
     */
    public abstract String getCountString(String sql);
}
