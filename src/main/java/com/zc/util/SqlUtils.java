package com.zc.util;

import com.zc.fn.Fn;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SqlUtils {

    private static final Function<String, String> BUILDER_FIELD_DEFAULT = (value) -> "#{" + value + "}";

    public static String tableName(Class<?> clazz) {
        if (clazz.isEnum()) {
            return null;
        }
        Table table = clazz.getAnnotation(Table.class);
        if (table != null) {
            return "`" + table.name() + "`";
        }
        return "`" + Utils.convertEntityClass(clazz) + "`";
    }

    public static String columnName(Class<?> clazz, String tableName, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            String table = tableName;
            if (column.table() != null && !column.table().isBlank()) {
                table = "`" + column.table() + "`";
            }
            return table + ".`" + column.name() + "`";
        }

        return tableName + "." + Utils.convert(fieldName);
    }

    /**
     * 构建字段变量
     *
     * @param column     实体类字段
     * @return  字段变量
     */
    public static <T> String builderField(Fn<T, Object> column) {
        return builderField(column.toField());
    }

    /**
     * 构建字段变量
     *
     * @param column     实体类字段
     * @return  字段变量
     */
    public static String builderField(String column) {
        return builderField(column, BUILDER_FIELD_DEFAULT);
    }

    /**
     * 构建字段变量
     *
     * @param field     实体类字段值
     * @param function  自定义处理机制
     * @return  字段变量
     */
    public static String builderField(String field, Function<String, String> function) {
        return function.apply(field);
    }

}
