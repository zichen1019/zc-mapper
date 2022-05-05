package com.zc.function;

import com.zc.fn.Fn;
import com.zc.jdbc.SqlSymbol;

/**
 * 基础符号处理构建工具类
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SqlFunctionSymbol {

    private static final String SPACE = " ";

    /**
     * 相等
     * <p>数据库字段与实体类字段相同时</p>
     *
     * @param column    字段
     * @param <T>       对应实体类
     * @return          column.toColumn() = #{column.toField()}
     */
    public static <T> String equal(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.EQ);
    }

    /**
     * 相等
     * <p>数据库字段与mapper方法传入的参数字段</p>
     *
     * @param column    字段
     * @param field     mapper方法传入的参数
     * @param <T>       对应实体类
     * @return          column.toString() = #{column.toField()}
     */
    public static <T> String equal(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.EQ);
    }

    /**
     * 相等
     * <p>数据库字段与mapper方法传入的参数字段</p>
     *
     * @param column    字段
     * @param field     mapper方法传入的参数
     * @return          column = #{field}
     */
    public static String equal(String column, String field) {
        return toCondtion(column, field, SqlSymbol.EQ);
    }

    public static <T> String notEqual(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.NOT_EQ);
    }

    public static <T> String notEqual(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.NOT_EQ);
    }

    public static String notEqual(String column, String field) {
        return toCondtion(column, field, SqlSymbol.NOT_EQ);
    }

    public static <T> String gt(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.GT);
    }

    public static <T> String gt(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.GT);
    }

    public static String gt(String column, String field) {
        return toCondtion(column, field, SqlSymbol.GT);
    }

    public static <T> String gte(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.GTE);
    }

    public static <T> String gte(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.GTE);
    }

    public static String gte(String column, String field) {
        return toCondtion(column, field, SqlSymbol.GTE);
    }

    public static <T> String lt(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.LT);
    }

    public static <T> String lt(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.LT);
    }

    public static String lt(String column, String field) {
        return toCondtion(column, field, SqlSymbol.LT);
    }

    public static <T> String lte(Fn<T, Object> column) {
        return toCondtion(column, SqlSymbol.LTE);
    }

    public static <T> String lte(SqlFunction<T> column, String field) {
        return toCondtion(column.toString(), field, SqlSymbol.LTE);
    }

    public static String lte(String column, String field) {
        return toCondtion(column, field, SqlSymbol.LTE);
    }

    public static <T> String like(Fn<T, Object> column) {
        return like(column.toColumn(), column.toField());
    }

    public static <T> String like(SqlFunction<T> column, String field) {
        return like(column.toString(), field);
    }

    public static String like(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.LIKE + " concat(%, #{" + field + "}, %)";
    }

    public static <T> String leftLike(Fn<T, Object> column) {
        return leftLike(column.toColumn(), column.toField());
    }

    public static <T> String leftLike(SqlFunction<T> column, String field) {
        return leftLike(column.toString(), field);
    }

    public static String leftLike(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.LEFT_LIKE + " concat(%, #{" + field + "})";
    }

    public static <T> String rightLike(Fn<T, Object> column) {
        return rightLike(column.toColumn(), column.toField());
    }

    public static <T> String rightLike(SqlFunction<T> column, String field) {
        return rightLike(column.toString(), field);
    }

    public static String rightLike(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.LIKE + " concat(#{" + field + "}, %)";
    }

    public static <T> String notLike(Fn<T, Object> column) {
        return notLike(column.toColumn(), column.toField());
    }

    public static <T> String notLike(SqlFunction<T> column, String field) {
        return notLike(column.toString(), field);
    }

    public static String notLike(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.NOT + SqlSymbol.LIKE + " concat(%, #{" + field + "}, %)";
    }

    public static <T> String notLeftLike(Fn<T, Object> column) {
        return notLeftLike(column.toColumn(), column.toField());
    }

    public static <T> String notLeftLike(SqlFunction<T> column, String field) {
        return notLeftLike(column.toString(), field);
    }

    public static String notLeftLike(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.NOT + SqlSymbol.LEFT_LIKE + " concat(%, #{" + field + "})";
    }

    public static <T> String notRightLike(Fn<T, Object> column) {
        return notRightLike(column.toColumn(), column.toField());
    }

    public static <T> String notRightLike(SqlFunction<T> column, String field) {
        return notRightLike(column.toString(), field);
    }

    public static String notRightLike(String column, String field) {
        return SPACE + column + SPACE + SqlSymbol.NOT + SqlSymbol.LIKE + " concat(#{" + field + "}, %)";
    }

    public static <T> String on(Fn<T, Object> column1, Fn<T, Object> column2) {
        return on(column1.toColumn(), column2.toColumn());
    }

    public static <T> String on(SqlFunction<T> column1, SqlFunction<T> column2) {
        return on(column1.toString(), column2.toString());
    }

    public static String on(String column1, String column2) {
        return SPACE + SqlSymbol.ON + SPACE + column1 + " = " + column2;
    }

    public static <T> String between(Fn<T, Object> column1, Fn<T, Object> column2) {
        return between(column1.toColumn(), column2.toColumn());
    }

    public static <T> String between(SqlFunction<T> column1, SqlFunction<T> column2) {
        return between(column1.toString(), column2.toString());
    }

    public static String between(String column1, String column2) {
        return SPACE + SqlSymbol.BETWEEN + SPACE + column1 + " AND " + column2;
    }

    /**
     * 根据字段、查询条件类型、将其转换为查询条件
     *
     * @param column    字段
     * @param symbol    查询条件类型
     * @return  将其转换为查询条件
     */
    public static <T> String toCondtion(Fn<T, Object> column, String symbol) {
        return toCondtion(column.toColumn(), column.toField(), symbol);
    }

    /**
     * 根据数据库字段、实体类字段、查询条件类型、将其转换为查询条件
     *
     * @param column    数据库字段
     * @param field     实体类字段
     * @param symbol    查询条件类型
     * @return  将其转换为查询条件
     */
    public static String toCondtion(String column, String field, String symbol) {
        return SPACE + column + SPACE + symbol + " #{" + field + "}";
    }

}
