# zc-mapper

最C的关联查询Mapper

目前版本处于快速迭代中，欢迎试用，不影响原有代码块。

建议环境支持：JDK17
可能最低支持：JDK8

## 使用注意事项

### 1. 如现有情况无法满足你的需求时，需要传入自定义字段时，需要将对应表名一起传入。如：table.column
### 2. 暂不支持内联的关联，就目前方式而言，暂无法处理

## 使用参考思路

### 1. 在Mapper接口中像往常一样定义一个接口方法

> 例如：UserMapper.selectList1()

### 2. 在接口方法上添加@SelectProvider注解

> value：构建sql的类 例如：UserMapper.class
> method：构建sql的类的方法 例如：UserMapper.selectListSql1()

```java
@SelectProvider(value = UserMapper.class, method = "selectListSql1")
List<User> selectList1(String sex);
```

### 3. 构建sql的类的方法 SqlBuilder

> 例如：UserMapper.selectListSql4()
> 其他使用方法参考selectListSql的序号4之后的方法

### 4. 传入的字段过度自定义时，使用函数处理构建工具类：SqlFunction

> ```SqlFunction.builder(column).length().sum()``` 输出为：```sum(length(column))```

- 可与字符串直接拼接，传入的字段未处理时，会自动处理为当前字段

> ```SqlFunction.builder(column) + " as custom"``` 输出为：```column as custom```

### 5. sql构建工具类（SqlBuilder）如果无法满足你的自定义需求时

> 可使用函数处理构建工具类（SqlFunction） + 基础符号处理构建工具类（SqlFunctionSymbol）

### 6. 还有更多新奇使用方式，等你发掘

## TODO

- [x] 联表查询
- [x] [分页查询 参考 pagehelper](https://github.com/pagehelper/pagehelper-spring-boot)
- [x] 如果有两个字段同名时，如何  获取  第二个字段
> 可以采用name2的方式获取第二个字段
- [x] SqlBuilder 构建工具
> 持续加载中......



## what todo ?

- [ ] 自定义字段验证处理器
```java
/**
 * 相同值查询
 * <p>1. 如果值为空，则不进行查询</p>
 * <p>2. 自定义字段验证处理器</p>
 *
 * @param column        查询字段
 * @param value         查询字段值
 * @param validation    自定义字段验证处理器
 * @return  ZcSql构建器
 */
public <T> SqlBuilder equal(Fn<T, Object> column, Object value, Function<Object, Boolean> validation) {
    return validation(value, validation, () -> SqlFunctionSymbol.eq(column));
}
```