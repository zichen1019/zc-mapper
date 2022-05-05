/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zc.fn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 参考通用 Mapper weekend 实现，用于获取方法引用对应的字段信息
 * <a href="https://github.com/mybatis-mapper/mapper.git">参考自通用Mapper</a>
 *
 * @author Frank
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public interface Fn<T, R> extends Function<T, R>, Serializable {

    /**
     * 缓存方法引用和对应的字段信息
     */
    Map<Fn, ClassField> FN_MAP = new HashMap<>();

    /**
     * 获取字段信息
     *
     * @return 字段名和所在类信息
     */
    default ClassField toClassField() {
        if (!FN_MAP.containsKey(this)) {
            synchronized (this) {
                if (!FN_MAP.containsKey(this)) {
                    FN_MAP.put(this, Reflections.fnToFieldName(this));
                }
            }
        }
        return FN_MAP.get(this);
    }

    /**
     * 转换为字段：获取方法引用对应的字段信息
     *
     * @return 方法引用对应的字段信息
     */
    default String toField() {
        return toClassField().getField();
    }

    /**
     * 转换为字段对应的列信息：获取方法引用对应的列信息
     *
     * @return 方法引用对应的列信息
     */
    default String toColumn() {
        return toClassField().getColumn();
    }

    /**
     * 转换为表对应的列信息：获取方法引用对应的列信息
     *
     * @return 方法引用对应的列信息
     */
    default String toTable() {
        return toClassField().getTable();
    }

}
