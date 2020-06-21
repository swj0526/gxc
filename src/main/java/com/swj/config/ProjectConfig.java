/*
package com.swj.config;



import net.atomarrow.configs.Config;
import net.atomarrow.db.orm.OrmContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author swj
 * @create 2019-11-07 9:52
 *//*

@Component
public class ProjectConfig extends Config {
    */
/**
     * 是否开启打印日志
     *//*

    @Override
    public boolean configServiceLog() {
        return true;
    }

    */
/**
     * 通过前缀来判断是否起开启事物
     *
     * @return
     *//*

    @Override
    public List<String> configAutoTxPrefix() {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("modify");
        list.add("delete");
        list.add("update");
        list.add("do");
        list.add("execute");
        return list;
    }

    */
/**
     * 数据库的名字
     *
     * @return
     *//*

    @Override
    public String configDbName() {
        return "warehouse";
    }

    */
/**
     * 数据库的类型,mysql oracle
     *
     * @return
     *//*

    @Override
    public String configDbType() {
        return "mysql";
    }

    */
/**
     * 数据库的关系映射,里面一般都是多表的时候用到的bean
     *
     * @param ormContext
     *//*

    @Override
    public void configOrmBean(OrmContext ormContext) {
        */
/*ormContext.addBean(RemindBean.class);*//*

       */
/* ormContext.addBean(InternshipBean.class);*//*

       */
/* ormContext.addBean(PageBean.class);
        ormContext.addBean(ResultBean.class);
        ormContext.addBean(StudentScoreBean.class);*//*


    }
}*/
