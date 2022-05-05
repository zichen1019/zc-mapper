package com.zc.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSQL<T> {
    protected static final String AND = ") \nAND (";
    protected static final String OR = ") \nOR (";
    public final SQLStatement sql = new SQLStatement();

    public AbstractSQL() {
    }

    public abstract T getSelf();

    public T UPDATE(String table) {
        this.sql().statementType = StatementType.UPDATE;
        this.sql().tables.add(table);
        return this.getSelf();
    }

    public T SET(String sets) {
        this.sql().sets.add(sets);
        return this.getSelf();
    }

    public T SET(String... sets) {
        this.sql().sets.addAll(Arrays.asList(sets));
        return this.getSelf();
    }

    public T INSERT_INTO(String tableName) {
        this.sql().statementType = StatementType.INSERT;
        this.sql().tables.add(tableName);
        return this.getSelf();
    }

    public T VALUES(String columns, String values) {
        this.INTO_COLUMNS(columns);
        this.INTO_VALUES(values);
        return this.getSelf();
    }

    public T INTO_COLUMNS(String... columns) {
        this.sql().columns.addAll(Arrays.asList(columns));
        return this.getSelf();
    }

    public T INTO_VALUES(String... values) {
        List<String> list = (List)this.sql().valuesList.get(this.sql().valuesList.size() - 1);
        Collections.addAll(list, values);
        return this.getSelf();
    }

    public T SELECT(String columns) {
        this.sql().statementType = StatementType.SELECT;
        this.sql().select.add(columns);
        return this.getSelf();
    }

    public T SELECT(String... columns) {
        this.sql().statementType = StatementType.SELECT;
        this.sql().select.addAll(Arrays.asList(columns));
        return this.getSelf();
    }

    public T SELECT_DISTINCT(String columns) {
        this.sql().distinct = true;
        this.SELECT(columns);
        return this.getSelf();
    }

    public T SELECT_DISTINCT(String... columns) {
        this.sql().distinct = true;
        this.SELECT(columns);
        return this.getSelf();
    }

    public T DELETE_FROM(String table) {
        this.sql().statementType = StatementType.DELETE;
        this.sql().tables.add(table);
        return this.getSelf();
    }

    public T FROM(String table) {
        this.sql().tables.add(table);
        return this.getSelf();
    }

    public T FROM(String... tables) {
        this.sql().tables.addAll(Arrays.asList(tables));
        return this.getSelf();
    }

    public T JOIN(String join) {
        this.sql().join.add(join);
        return this.getSelf();
    }

    public T JOIN(String... joins) {
        this.sql().join.addAll(Arrays.asList(joins));
        return this.getSelf();
    }

    public T INNER_JOIN(String join) {
        this.sql().innerJoin.add(join);
        return this.getSelf();
    }

    public T INNER_JOIN(String... joins) {
        this.sql().innerJoin.addAll(Arrays.asList(joins));
        return this.getSelf();
    }

    public T LEFT_OUTER_JOIN(String join) {
        this.sql().leftOuterJoin.add(join);
        return this.getSelf();
    }

    public T LEFT_OUTER_JOIN(String... joins) {
        this.sql().leftOuterJoin.addAll(Arrays.asList(joins));
        return this.getSelf();
    }

    public T RIGHT_OUTER_JOIN(String join) {
        this.sql().rightOuterJoin.add(join);
        return this.getSelf();
    }

    public T RIGHT_OUTER_JOIN(String... joins) {
        this.sql().rightOuterJoin.addAll(Arrays.asList(joins));
        return this.getSelf();
    }

    public T OUTER_JOIN(String join) {
        this.sql().outerJoin.add(join);
        return this.getSelf();
    }

    public T OUTER_JOIN(String... joins) {
        this.sql().outerJoin.addAll(Arrays.asList(joins));
        return this.getSelf();
    }

    public T WHERE(String conditions) {
        this.sql().where.add(conditions);
        this.sql().lastList = this.sql().where;
        return this.getSelf();
    }

    public T WHERE(String... conditions) {
        this.sql().where.addAll(Arrays.asList(conditions));
        this.sql().lastList = this.sql().where;
        return this.getSelf();
    }

    public T OR() {
        this.sql().lastList.add(") \nOR (");
        return this.getSelf();
    }

    public T AND() {
        this.sql().lastList.add(") \nAND (");
        return this.getSelf();
    }

    public T GROUP_BY(String columns) {
        this.sql().groupBy.add(columns);
        return this.getSelf();
    }

    public T GROUP_BY(String... columns) {
        this.sql().groupBy.addAll(Arrays.asList(columns));
        return this.getSelf();
    }

    public T HAVING(String conditions) {
        this.sql().having.add(conditions);
        this.sql().lastList = this.sql().having;
        return this.getSelf();
    }

    public T HAVING(String... conditions) {
        this.sql().having.addAll(Arrays.asList(conditions));
        this.sql().lastList = this.sql().having;
        return this.getSelf();
    }

    public T ORDER_BY(String columns) {
        this.sql().orderBy.add(columns);
        return this.getSelf();
    }

    public T ORDER_BY(String... columns) {
        this.sql().orderBy.addAll(Arrays.asList(columns));
        return this.getSelf();
    }

    public T LIMIT(String variable) {
        this.sql().limit = variable;
        this.sql().limitingRowsStrategy = LimitingRowsStrategy.OFFSET_LIMIT;
        return this.getSelf();
    }

    public T LIMIT(int value) {
        return this.LIMIT(String.valueOf(value));
    }

    public T OFFSET(String variable) {
        this.sql().offset = variable;
        this.sql().limitingRowsStrategy = LimitingRowsStrategy.OFFSET_LIMIT;
        return this.getSelf();
    }

    public T OFFSET(long value) {
        return this.OFFSET(String.valueOf(value));
    }

    public T FETCH_FIRST_ROWS_ONLY(String variable) {
        this.sql().limit = variable;
        this.sql().limitingRowsStrategy = LimitingRowsStrategy.ISO;
        return this.getSelf();
    }

    public T FETCH_FIRST_ROWS_ONLY(int value) {
        return this.FETCH_FIRST_ROWS_ONLY(String.valueOf(value));
    }

    public T OFFSET_ROWS(String variable) {
        this.sql().offset = variable;
        this.sql().limitingRowsStrategy = LimitingRowsStrategy.ISO;
        return this.getSelf();
    }

    public T OFFSET_ROWS(long value) {
        return this.OFFSET_ROWS(String.valueOf(value));
    }

    public T ADD_ROW() {
        this.sql().valuesList.add(new ArrayList());
        return this.getSelf();
    }

    protected SQLStatement sql() {
        return this.sql;
    }

    public <A extends Appendable> A usingAppender(A a) {
        this.sql().sql(a);
        return a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.sql().sql(sb);
        return sb.toString();
    }

}