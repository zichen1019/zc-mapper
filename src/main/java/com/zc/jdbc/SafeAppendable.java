package com.zc.jdbc;

import java.io.IOException;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public class SafeAppendable {
    private final Appendable appendable;
    private boolean empty = true;

    public SafeAppendable(Appendable a) {
        this.appendable = a;
    }

    public SafeAppendable append(CharSequence s) {
        try {
            if (this.empty && s.length() > 0) {
                this.empty = false;
            }

            this.appendable.append(s);
            return this;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public boolean isEmpty() {
        return this.empty;
    }
}
