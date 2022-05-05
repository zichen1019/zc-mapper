package com.zc.sql;

import com.zc.fn.Fn;
import com.zc.jdbc.SqlSymbol;
import com.zc.jdbc.SqlValidation;
import com.zc.util.SqlUtils;

import java.util.function.Function;

public class Sql {

    private final StringBuilder sql = new StringBuilder();

    public static Sql build() {
        return new Sql();
    }

    /**
     * 相同值查询
     * <p>1. 如果值为空，则进行 is null 查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> Sql equal(Fn<T, Object> column, Object value) {
        return build(SqlValidation.validation(value, () -> column.toColumn() + SqlSymbol.EQ + SqlUtils.builderField(column)));
    }

    /**
     * 相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     * <p>2. 自定义字段验证处理器</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @param validation    自定义字段验证处理器
     * @return  ZcSql构建器
     */
    public <T> Sql equal(Fn<T, Object> column, Object value, Function<Object, Boolean> validation) {
        return build(SqlValidation.validation(value, validation, () -> column.toColumn() + SqlSymbol.EQ + SqlUtils.builderField(column)));
    }

    public Sql build(String sql) {
        if (sql != null && !sql.isBlank()) {
            this.sql.append(sql);
        }
        return this;
    }

    @Override
    public String toString() {
        return sql.toString();
    }

}
