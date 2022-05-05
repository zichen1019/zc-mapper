package com.zc.function;

import com.zc.fn.Fn;

public class CaseWhenFunction {

    private final StringBuilder sql = new StringBuilder("case ");

    public static CaseWhenFunction build() {
        return new CaseWhenFunction();
    }

    public <T> CaseWhenFunction build(Fn<T, Object> column) {
        sql.append(column.toColumn());
        return this;
    }

    public <T> CaseWhenFunction when(Fn<T, Object> column, Object value) {
        sql.append(" when ").append(column.toColumn()).append("");
        return this;
    }

    @Override
    public String toString() {
        return sql.toString();
    }

}
