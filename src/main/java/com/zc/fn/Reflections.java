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

import com.zc.util.SqlUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参考通用 Mapper weekend 实现，用于获取方法引用对应的字段信息
 * <a href="https://github.com/mybatis-mapper/mapper.git">参考自通用Mapper</a>
 *
 * @author Frank
 * @author liuzh
 */
public class Reflections {
  private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
  private static final Pattern IS_PATTERN  = Pattern.compile("^is[A-Z].*");
  private static final Pattern INSTANTIATED_CLASS_PATTERN = Pattern.compile("\\(L(?<cls>.+);\\).+");

  private static final String WRITE_REPLACE = "writeReplace";
  private static final String CLS = "cls";
  private static final String SLASH = "/";
  private static final String SPOT = "\\.";
  private static final int GET_PATTERN_SUB_LENGTH = 3;
  private static final int IS_PATTERN_SUB_LENGTH = 2;

  public static <T, R> ClassField fnToFieldName(Fn<T, R> fn) {
    try {
      Method method = fn.getClass().getDeclaredMethod(WRITE_REPLACE);
      method.setAccessible(Boolean.TRUE);
      SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
      String getter = serializedLambda.getImplMethodName();
      if (GET_PATTERN.matcher(getter).matches()) {
        getter = getter.substring(GET_PATTERN_SUB_LENGTH);
      } else if (IS_PATTERN.matcher(getter).matches()) {
        getter = getter.substring(IS_PATTERN_SUB_LENGTH);
      }
      //主要是这里  serializedLambda.getInstantiatedMethodType()
      Matcher matcher = INSTANTIATED_CLASS_PATTERN.matcher(serializedLambda.getInstantiatedMethodType());
      String implClass;
      if (matcher.find()) {
        implClass = matcher.group(CLS).replaceAll(SLASH, SPOT);
      } else {
        implClass = serializedLambda.getImplClass().replaceAll(SLASH, SPOT);
      }

      Class<?> clazz = Class.forName(implClass);

      String table = SqlUtils.tableName(clazz);

      String field = Introspector.decapitalize(getter);

      return ClassField.builder()
              .clazz(clazz)
              .table(table)
              .field(field)
              .column(SqlUtils.columnName(clazz, table, field))
              .build();
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
