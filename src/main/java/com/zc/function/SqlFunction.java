package com.zc.function;

import com.zc.fn.Fn;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 函数处理构建工具类
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SqlFunction<T> {

    private final StringBuilder sql = new StringBuilder();
    private Fn<T, Object> column;

    public static <T> SqlFunction<T> builder(Fn<T, Object> column) {
        SqlFunction<T> sqlFunction = new SqlFunction<>();
        sqlFunction.column = column;
        return sqlFunction;
    }

    public SqlFunction<T> length() {
        return builder(() -> length(sql.toString()), () -> length(column.toColumn()));
    }

    public String length(String functionValue) {
        return "length(" + functionValue + ")";
    }

    public SqlFunction<T> findInSet() {
        return builder(() -> findInSet(sql.toString()), () -> findInSet(column.toColumn()));
    }

    public String findInSet(String functionValue) {
        return "find_in_set(" + functionValue + ", #{" + column.toField() + "})";
    }

    public SqlFunction<T> count() {
        return builder(() -> findInSet(sql.toString()), () -> findInSet(column.toColumn()));
    }

    public String count(String functionValue) {
        return "count(" + functionValue + ")";
    }

    public SqlFunction<T> sum() {
        return builder(() -> findInSet(sql.toString()), () -> findInSet(column.toColumn()));
    }

    public String sum(String functionValue) {
        return "count(" + functionValue + ")";
    }

    /**
     * 输出当前字段的构建sql
     *
     * @return  如果当前未有函数进行处理，则返回当前字段
     */
    @Override
    public String toString() {
        return sql.toString().isBlank() ? column.toColumn() : sql.toString();
    }

    /**
     * 执行构建器
     *
     * @param success       验证成功时执行，执行sql语句构建处理
     * @param error         验证失败时执行
     * @return  ZcSql构建器
     */
    public SqlFunction<T> builder(Supplier<String> success, Supplier<String> error) {
        return builder(sql, (value) -> !value.isEmpty(), success, error);
    }

    /**
     * 执行构建器
     *
     * @param value         字段值
     * @param validation    字段值验证处理
     * @param success       验证成功时执行，执行sql语句构建处理
     * @param error         验证失败时执行
     * @return  ZcSql构建器
     */
    public SqlFunction<T> builder(StringBuilder value, Function<StringBuilder, Boolean> validation, Supplier<String> success, Supplier<String> error) {
        // 1. 如果字段检查器存在
        if (validation != null) {
            // 1.1 执行字段检查，并且检查未通过
            if (!validation.apply(value)) {
                // 1.1.1 是否配置了失败的处理
                if (error != null) {
                    sql.append(error.get());
                    return this;
                }
            }
        }
        // 2. 如果执行sql语句构建处理不存在
        if (success == null) {
            return this;
        }
        // 3. 执行sql语句构建处理
        sql.append(success.get());
        return this;
    }

}
