package com.zc.jdbc;

import com.zc.fn.Fn;
import com.zc.function.SqlFunction;
import com.zc.function.SqlFunctionSymbol;
import com.zc.util.SqlUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * ZcSql构建器
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SqlBuilder extends AbstractSQL<SqlBuilder> {

    @Override
    public SqlBuilder getSelf() {
        return this;
    }

    /**
     * 查询全部字段
     * <p>2022年4月25日 如果是联表查询且有重复字段时, 按照mybatis的处理: 如果字段为`name`, 第一个字段不变, 第二个字段为`name2`.</p>
     *
     * @return  ZcSql构建器
     */
    public static SqlBuilder build() {
        return new SqlBuilder();
    }

    /**
     * 查询全部字段
     * <p>2022年4月25日 如果是联表查询且有重复字段时, 按照mybatis的处理: 如果字段为`name`, 第一个字段不变, 第二个字段为`name2`.</p>
     *
     * @return  ZcSql构建器
     */
    public SqlBuilder selectAll() {
        return SELECT("*");
    }

    /**
     * 查询全部字段
     *
     * @param tableClasss   实体类
     * @return  ZcSql构建器
     */
    public SqlBuilder selectAll(Class<?>... tableClasss) {
        for (Class<?> tableClass : tableClasss) {
            SELECT(SqlUtils.tableName(tableClass));
        }
        return getSelf();
    }

    /**
     * 查询自定义字段
     *
     * @param columns   查询字段
     * @return  ZcSql构建器
     */
    @SafeVarargs
    public final <T> SqlBuilder select(Fn<T, Object>... columns) {
        return SELECT(Arrays.stream(columns).map(Fn::toColumn).collect(Collectors.joining(",")));
    }

    /**
     * 查询自定义字段
     *
     * @param columns   查询字段
     * @return  ZcSql构建器
     */
    @SafeVarargs
    public final <T> SqlBuilder select(SqlFunction<T>... columns) {
        return SELECT(Arrays.stream(columns).map(SqlFunction::toString).collect(Collectors.joining(",")));
    }

    /**
     * 查询字段，并赋一个别名
     *
     * @param column    查询字段
     * @param field     别名字段
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder selectAs(Fn<T, Object> column, Fn<E, Object> field) {
        return SELECT(column.toColumn() + " AS " + field.toField());
    }

    /**
     * 查询字段，并赋一个别名
     *
     * @param column    查询字段
     * @param field     别名字段
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder selectAs(SqlFunction<T> column, Fn<E, Object> field) {
        return SELECT(column + " AS " + field.toField());
    }

    /**
     * 查询的主表
     * <p>1. 表名优先取@Table值</p>
     * <p>2. 表名优先取实体类名称，并对其大写字母转斜划线的处理</p>
     *
     * @param tableClass    对应实体类
     * @return  ZcSql构建器
     */
    public SqlBuilder from(Class<?> tableClass) {
        return FROM(SqlUtils.tableName(tableClass));
    }

    /**
     * 全关联
     *
     * @param joinTableClass    关联的实体类
     * @param column1           主关联字段1
     * @param column2           从关联字段2
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder join(Class<?> joinTableClass, Fn<T, Object> column1, Fn<E, Object> column2) {
        return OUTER_JOIN(SqlUtils.tableName(joinTableClass) + SqlSymbol.ON + column1.toColumn() + " = " + column2.toColumn());
    }

    /**
     * 全关联
     *
     * @param joinTableClass    关联的实体类
     * @param column1           主关联字段1
     * @param column2           从关联字段2
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder leftJoin(Class<?> joinTableClass, Fn<T, Object> column1, Fn<E, Object> column2) {
        return LEFT_OUTER_JOIN(SqlUtils.tableName(joinTableClass) + SqlSymbol.ON + column1.toColumn() + " = " + column2.toColumn());
    }

    /**
     * 全关联
     *
     * @param joinTableClass    关联的实体类
     * @param column1           主关联字段1
     * @param column2           从关联字段2
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder rightJoin(Class<?> joinTableClass, Fn<T, Object> column1, Fn<E, Object> column2) {
        return RIGHT_OUTER_JOIN(SqlUtils.tableName(joinTableClass) + SqlSymbol.ON + column1.toColumn() + " = " + column2.toColumn());
    }

    /**
     * 相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder equal(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.equal(column));
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder equal(Fn<T, Object> column, String field, Object value) {
        return equal(SqlFunction.builder(column), field, value);
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder equal(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return equal(column, field.toField(), value);
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder equal(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.equal(column, field));
    }

    /**
     * 相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notEqual(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notEqual(column));
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notEqual(Fn<T, Object> column, String field, Object value) {
        return notEqual(SqlFunction.builder(column), field, value);
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notEqual(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return notEqual(column, field.toField(), value);
    }

    /**
     * 不相同值查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notEqual(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notEqual(column, field));
    }

    /**
     * 全模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder like(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.like(column));
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder like(Fn<T, Object> column, String field, Object value) {
        return like(SqlFunction.builder(column), field, value);
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder like(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return like(column, field.toField(), value);
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder like(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.leftLike(column, field));
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder leftLike(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.leftLike(column));
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder leftLike(Fn<T, Object> column, String field, Object value) {
        return leftLike(SqlFunction.builder(column), field, value);
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder leftLike(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return leftLike(column, field.toField(), value);
    }

    /**
     * 左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder leftLike(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.leftLike(column, field));
    }

    /**
     * 右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder rightLike(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.rightLike(column));
    }

    /**
     * 右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder rightLike(Fn<T, Object> column, String field, Object value) {
        return rightLike(SqlFunction.builder(column), field, value);
    }

    /**
     * 右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder rightLike(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return rightLike(column, field.toField(), value);
    }

    /**
     * 右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder rightLike(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.rightLike(column, field));
    }

    /**
     * 不是这个的全模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLike(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notLike(column));
    }

    /**
     * 不是这个的全模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLike(Fn<T, Object> column, String field, Object value) {
        return notLike(SqlFunction.builder(column), field, value);
    }

    /**
     * 不是这个的全模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLike(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return notLike(column, field.toField(), value);
    }

    /**
     * 不是这个的全模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLike(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notLike(column, field));
    }

    /**
     * 不是这个的左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLeftLike(Fn<T, Object> column, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notLeftLike(column));
    }

    /**
     * 不是这个的左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLeftLike(Fn<T, Object> column, String field, Object value) {
        return notLeftLike(SqlFunction.builder(column), field, value);
    }

    /**
     * 不是这个的左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLeftLike(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return notLeftLike(column, field.toField(), value);
    }

    /**
     * 不是这个的左模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notLeftLike(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notLeftLike(column, field));
    }

    /**
     * 右不是这个的模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notRightLike(Fn<T, Object> column, Object value) {
        return notRightLike(column, column.toField(), value);
    }

    /**
     * 不是这个的右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notRightLike(Fn<T, Object> column, String field, Object value) {
        return notRightLike(SqlFunction.builder(column), field, value);
    }

    /**
     * 不是这个的右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notRightLike(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return notRightLike(column, field.toField(), value);
    }

    /**
     * 不是这个的右模糊查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder notRightLike(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.notRightLike(column, field));
    }

    /**
     * 大于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gt(Fn<T, Object> column, Object value) {
        return gt(column, column.toField(), value);
    }

    /**
     * 大于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gt(Fn<T, Object> column, String field, Object value) {
        return gt(SqlFunction.builder(column), field, value);
    }

    /**
     * 大于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gt(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return gt(column, field.toField(), value);
    }

    /**
     * 大于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gt(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.gt(column, field));
    }

    /**
     * 大于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gte(Fn<T, Object> column, Object value) {
        return gte(column, column.toField(), value);
    }

    /**
     * 大于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gte(Fn<T, Object> column, String field, Object value) {
        return gte(SqlFunction.builder(column), field, value);
    }

    /**
     * 大于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column    查询字段
     * @param field     实体类字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gte(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return gte(column, field.toField(), value);
    }

    /**
     * 大于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder gte(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.gte(column, field));
    }

    /**
     * 小于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lt(Fn<T, Object> column, Object value) {
        return lt(column, column.toField(), value);
    }

    /**
     * 小于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lt(Fn<T, Object> column, String field, Object value) {
        return lt(SqlFunction.builder(column), field, value);
    }

    /**
     * 小于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lt(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return lt(column, field.toField(), value);
    }

    /**
     * 小于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lt(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.lt(column, field));
    }



    /**
     * 小于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column        查询字段
     * @param value         查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lte(Fn<T, Object> column, Object value) {
        return lte(column, column.toField(), value);
    }

    /**
     * 小于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lte(Fn<T, Object> column, String field, Object value) {
        return lte(SqlFunction.builder(column), field, value);
    }

    /**
     * 小于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    实体类字段
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lte(SqlFunction<T> column, Fn<T, Object> field, Object value) {
        return lte(column, field.toField(), value);
    }

    /**
     * 小于等于查询
     * <p>1. 如果值为空，则不进行查询</p>
     *
     * @param column   查询字段
     * @param field    mapper方法传入的参数
     * @param value    查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder lte(SqlFunction<T> column, String field, Object value) {
        return validation(value, () -> SqlFunctionSymbol.lte(column, field));
    }

    /* TODO 待移除  以此为上均使用新的处理方式，以下未处理 */

    /**
     * 区间查询
     * <p>1. 如果两个，则不进行查询</p>
     *
     * @param startColumn   查询字段
     * @param endColumn     查询字段
     * @param startValue    查询字段值
     * @param endValue      查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder between(Fn<T, Object> startColumn, Fn<T, Object> endColumn, Object startValue, Object endValue) {
        return validation(null,
                (val) -> startValue != null && endValue != null,
                () -> startColumn.toColumn() + " between " + SqlUtils.builderField(startColumn) + " AND " + SqlUtils.builderField(endColumn));
    }

    /**
     * in查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     * <p>2. </p>
     *
     * @param column        查询字段
     * @param paramName     变量名称
     * @param values        查询集合字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder in(Fn<T, Object> column, String paramName, Iterable<?> values) {
        return validation(null,
                (val) -> values != null && values.iterator().hasNext(),
                () -> "<foreach item=\"item\" index=\"index\" collection=\"" + paramName + "\" open=\"" + column.toColumn() + " in (\" separator=\",\" close=\")\" nullable=\"true\">" +
                        "#{item}" +
                        "</foreach>");
    }

    /**
     * in查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     *
     * @param column        查询字段
     * @param paramName     变量名称
     * @param values        查询集合字段值
     * @return  ZcSql构建器
     */
    public <T, E> SqlBuilder in(Fn<T, Object> column, Fn<E, Object> paramName, Iterable<?> values) {
        return validation(null,
                (val) -> values != null && values.iterator().hasNext(),
                () -> "<foreach item=\"item\" index=\"index\" collection=\"" + paramName.toField() + "\" open=\"" + column.toColumn() + " in (\" separator=\",\" close=\")\" nullable=\"true\">" +
                        "#{item}" +
                        "</foreach>");
    }

    /**
     * in查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     *
     * @param column    查询字段
     * @param values    查询集合字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder in(Fn<T, Object> column, Iterable<?> values) {
        return validation(null,
                (val) -> values != null && values.iterator().hasNext(),
                () -> "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"" + column.toColumn() + " in (\" separator=\",\" close=\")\" nullable=\"true\">" +
                        "#{item}" +
                        "</foreach>");
    }

    /**
     * in子查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     *
     * @param column        查询字段
     * @param sqlBuilder    子查询对象
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder in(Fn<T, Object> column, Supplier<SqlBuilder> sqlBuilder) {
        String sql = sqlBuilder.get().toStringNotHasScript();
        return validation(null,
                (val) -> !sql.isBlank(),
                () -> column.toColumn() + " in (" + sql + ")");
    }

    /**
     * in查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder isNull(Fn<T, Object> column, Object value) {
        return validation(value,
                () -> column.toColumn() + " is null");
    }

    /**
     * in查询
     * <p>1. 如果集合为null或空集合，则不进行查询</p>
     *
     * @param column    查询字段
     * @param value     查询字段值
     * @return  ZcSql构建器
     */
    public <T> SqlBuilder isNotNull(Fn<T, Object> column, Object value) {
        return validation(value,
                () -> column.toColumn() + " is not null");
    }

    public SqlBuilder validation(Object value, Supplier<String> execute) {
        String sql = SqlValidation.validation(value, execute);
        if (sql != null) {
            return WHERE(sql);
        }
        return getSelf();
    }

    public SqlBuilder validation(Object value, Function<Object, Boolean> validation, Supplier<String> success) {
        String sql = SqlValidation.validation(value, validation, success);
        if (sql != null) {
            return WHERE(sql);
        }
        return getSelf();
    }

    public SqlBuilder validation(Object value, Function<Object, Boolean> validation, Supplier<String> success, Supplier<String> error) {
        String sql = SqlValidation.validation(value, validation, success, error);
        if (sql != null) {
            return WHERE(sql);
        }
        return getSelf();
    }

    @Override
    public String toString() {
        return "<script>\n" + super.toString() + "\n</script>";
    }

    public String toStringNotHasScript() {
        return super.toString();
    }
}
