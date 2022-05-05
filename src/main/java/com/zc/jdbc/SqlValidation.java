package com.zc.jdbc;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 字段值检查器
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SqlValidation {

    /**
     * 执行构建器
     * <p>包含默认NotNull和NotBlank的检查</p>
     *
     * @param value     字段值
     * @param execute   执行处理
     * @return  ZcSql构建器
     */
    public static String validation(Object value, Supplier<String> execute) {
        return validation(value, SqlValidation.notNull(), execute, null);
    }

    /**
     * 执行构建器
     *
     * @param value         字段值
     * @param validation    字段值验证处理
     * @param success       执行sql语句构建处理
     * @return  ZcSql构建器
     */
    public static String validation(Object value, Function<Object, Boolean> validation, Supplier<String> success) {
        return validation(value, validation, success, null);
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
    public static String validation(Object value, Function<Object, Boolean> validation, Supplier<String> success, Supplier<String> error) {
        // 1. 如果字段检查器存在
        if (validation != null) {
            // 1.1 执行字段检查，并且检查未通过
            if (!validation.apply(value)) {
                // 1.1.1 是否配置了失败的处理
                if (error != null) {
                    return error.get();
                }
                return null;
            }
        }
        // 2. 执行sql语句构建处理
        return success.get();
    }

    /**
     * 执行构建器
     * <p>无字段值验证处理的</p>
     *
     * @param value     字段值
     * @param execute   执行处理
     * @return  ZcSql构建器
     */
    public static String notValidation(Object value, Supplier<String> execute) {
        return validation(value, SqlValidation.notValidation(), execute, null);
    }

    /**
     * 无字段值验证处理
     *
     * @return  字段值判空检查器
     */
    public static Function<Object, Boolean> notValidation() {
        return null;
    }

    /**
     * 字段值判空检查器
     *
     * @return  字段值判空检查器
     */
    public static Function<Object, Boolean> notNull() {
        return (value) -> {
            // 如果值为null，则检查不通过
            if (value == null) {
                return false;
            }
            // 如果值为字符串并且为空字符串，则检查不通过
            if (value instanceof String && ((String) value).isBlank()) {
                return false;
            }
            // 检查通过
            return true;
        };
    }

}
