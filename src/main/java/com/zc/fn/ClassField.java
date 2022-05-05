package com.zc.fn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <a href="https://github.com/mybatis-mapper/mapper.git">参考自通用Mapper</a>
 *
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassField {

    private Class<?> clazz;

    private String table;

    private String field;

    private String column;

}
