package com.zc.jdbc;

public enum LimitingRowsStrategy {
    NOP {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
        }
    },
    ISO {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
            if (offset != null) {
                builder.append(" OFFSET ").append(offset).append(" ROWS");
            }

            if (limit != null) {
                builder.append(" FETCH FIRST ").append(limit).append(" ROWS ONLY");
            }

        }
    },
    OFFSET_LIMIT {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
            if (limit != null) {
                builder.append(" LIMIT ").append(limit);
            }

            if (offset != null) {
                builder.append(" OFFSET ").append(offset);
            }

        }
    };

    private LimitingRowsStrategy() {
    }

    protected abstract void appendClause(SafeAppendable var1, String var2, String var3);
}
