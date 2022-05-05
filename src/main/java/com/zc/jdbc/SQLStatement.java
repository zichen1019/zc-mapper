package com.zc.jdbc;

import java.util.ArrayList;
import java.util.List;

public class SQLStatement {

    StatementType statementType;
    List<String> sets = new ArrayList();
    List<String> select = new ArrayList();
    List<String> tables = new ArrayList();
    List<String> join = new ArrayList();
    List<String> innerJoin = new ArrayList();
    List<String> outerJoin = new ArrayList();
    List<String> leftOuterJoin = new ArrayList();
    List<String> rightOuterJoin = new ArrayList();
    List<String> where = new ArrayList();
    List<String> having = new ArrayList();
    List<String> groupBy = new ArrayList();
    List<String> orderBy = new ArrayList();
    List<String> lastList = new ArrayList();
    List<String> columns = new ArrayList();
    List<List<String>> valuesList = new ArrayList();
    boolean distinct;
    String offset;
    String limit;
    LimitingRowsStrategy limitingRowsStrategy;

    public List<String> getWhere() {
        return where;
    }

    public SQLStatement() {
        this.limitingRowsStrategy = LimitingRowsStrategy.NOP;
        this.valuesList.add(new ArrayList());
    }

    private void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close, String conjunction) {
        if (!parts.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append("\n");
            }

            builder.append(keyword);
            builder.append(" ");
            builder.append(open);
            String last = "________";
            int i = 0;

            for(int n = parts.size(); i < n; ++i) {
                String part = parts.get(i);
                if (i > 0 && !") \nAND (".equals(part) && !") \nOR (".equals(part) && !") \nAND (".equals(last) && !") \nOR (".equals(last)) {
                    builder.append(conjunction);
                }

                builder.append(part);
                last = part;
            }

            builder.append(close);
        }

    }

    private String selectSQL(SafeAppendable builder) {
        if (this.distinct) {
            this.sqlClause(builder, "SELECT DISTINCT", this.select, "", "", ", ");
        } else {
            this.sqlClause(builder, "SELECT", this.select, "", "", ", ");
        }

        this.sqlClause(builder, "FROM", this.tables, "", "", ", ");
        this.joins(builder);
        this.sqlClause(builder, "WHERE", this.where, "(", ")", " AND ");
        this.sqlClause(builder, "GROUP BY", this.groupBy, "", "", ", ");
        this.sqlClause(builder, "HAVING", this.having, "(", ")", " AND ");
        this.sqlClause(builder, "ORDER BY", this.orderBy, "", "", ", ");
        this.limitingRowsStrategy.appendClause(builder, this.offset, this.limit);
        return builder.toString();
    }

    private void joins(SafeAppendable builder) {
        this.sqlClause(builder, "JOIN", this.join, "", "", "\nJOIN ");
        this.sqlClause(builder, "INNER JOIN", this.innerJoin, "", "", "\nINNER JOIN ");
        this.sqlClause(builder, "OUTER JOIN", this.outerJoin, "", "", "\nOUTER JOIN ");
        this.sqlClause(builder, "LEFT OUTER JOIN", this.leftOuterJoin, "", "", "\nLEFT OUTER JOIN ");
        this.sqlClause(builder, "RIGHT OUTER JOIN", this.rightOuterJoin, "", "", "\nRIGHT OUTER JOIN ");
    }

    private String insertSQL(SafeAppendable builder) {
        this.sqlClause(builder, "INSERT INTO", this.tables, "", "", "");
        this.sqlClause(builder, "", this.columns, "(", ")", ", ");

        for(int i = 0; i < this.valuesList.size(); ++i) {
            this.sqlClause(builder, i > 0 ? "," : "VALUES", (List)this.valuesList.get(i), "(", ")", ", ");
        }

        return builder.toString();
    }

    private String deleteSQL(SafeAppendable builder) {
        this.sqlClause(builder, "DELETE FROM", this.tables, "", "", "");
        this.sqlClause(builder, "WHERE", this.where, "(", ")", " AND ");
        this.limitingRowsStrategy.appendClause(builder, (String)null, this.limit);
        return builder.toString();
    }

    private String updateSQL(SafeAppendable builder) {
        this.sqlClause(builder, "UPDATE", this.tables, "", "", "");
        this.joins(builder);
        this.sqlClause(builder, "SET", this.sets, "", "", ", ");
        this.sqlClause(builder, "WHERE", this.where, "(", ")", " AND ");
        this.limitingRowsStrategy.appendClause(builder, (String)null, this.limit);
        return builder.toString();
    }

    public String sql(Appendable a) {
        SafeAppendable builder = new SafeAppendable(a);
        if (this.statementType == null) {
            return null;
        } else {
            String answer;
            switch(this.statementType) {
                case DELETE:
                    answer = this.deleteSQL(builder);
                    break;
                case INSERT:
                    answer = this.insertSQL(builder);
                    break;
                case SELECT:
                    answer = this.selectSQL(builder);
                    break;
                case UPDATE:
                    answer = this.updateSQL(builder);
                    break;
                default:
                    answer = null;
            }

            return answer;
        }
    }
    
}
