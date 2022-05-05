package com.zc.mapper;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

/**
 * 构建对应Sql
 * <p>类中实现 ProviderMethodResolver 接口，默认实现中，会将映射器方法的调用解析到实现的同名方法上！！！</p>
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class UserMapperBuilder implements ProviderMethodResolver {

    public static String selectList(final String sex) {
        return new SQL(){{
            SELECT("*");
            FROM("user");
            if (sex != null) {
                WHERE("sex = #{value} ");
            }
            ORDER_BY("id");
        }}.toString();
    }

}
