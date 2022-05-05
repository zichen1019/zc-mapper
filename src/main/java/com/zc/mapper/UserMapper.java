package com.zc.mapper;

import com.zc.function.SqlFunction;
import com.zc.jdbc.SqlBuilder;
import com.zc.model.User;
import com.zc.model.UserAndIds;
import com.zc.model.UserAndIdsDTO;
import com.zc.model.UserIds;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
public interface UserMapper {

    @SelectProvider(value = UserMapper.class, method = "selectListSql1")
    List<User> selectList1(String sex);

    static String selectListSql1(final String sex) {
        String sql = new SQL(){{
            SELECT("*");
            FROM("user");
            if (sex != null) {
                WHERE("sex = #{value} ");
                AND();
                WHERE("sex = #{value} ");
                AND();
            }
            ORDER_BY("id");
        }}.toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql2")
    List<UserAndIds> selectList2(String sex);

    static String selectListSql2(final String sex) {
        String sql = new SQL(){{
            SELECT("user.*,user_ids.*,user_ids.name as name2");
            FROM("user");
            LEFT_OUTER_JOIN("user_ids on user.id = user_ids.id2");
            if (sex != null && !sex.isBlank()) {
                WHERE("sex = #{value} ");
            }
            ORDER_BY("id");
        }}.toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql3")
    List<HashMap<String, Object>> selectList3(String sex, String name);

    static String selectListSql3(final String sex, final String name) {
        String sql = new SQL(){{
            SELECT("user.*,user_ids.*,user_ids.name as name2");
            FROM("user");
            LEFT_OUTER_JOIN("user_ids on user.id = user_ids.id2");
            if (sex != null && !sex.isBlank()) {
                WHERE("sex = #{sex} ");
            }
            if (sex != null && !sex.isBlank()) {
                WHERE("user.name like concat('%', #{name}, '%') ");
            }
            ORDER_BY("id");
        }}.toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql4")
    List<UserAndIds> selectList4(String name);

    static String selectListSql4(final String name) {
        String sql = SqlBuilder.build().select(User::getId, User::getName).from(User.class).equal(User::getName, name).toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql5")
    List<UserAndIds> selectList5(String name);

    static String selectListSql5(final String name) {
        String sql = SqlBuilder.build()
                .select(User::getId, User::getName, User::getSex)
                .select(UserIds::getId1)
                .selectAs(UserIds::getName, UserAndIds::getName2)
                .from(User.class)
                .LEFT_OUTER_JOIN("user_ids on user.id = user_ids.id2")
                .equal(User::getName, name)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql6")
    List<UserAndIds> selectList6(String name, Long id1);

    static String selectListSql6(final String name, final Long id1) {
        String sql = SqlBuilder.build()
                .select(User::getId, User::getName, User::getSex)
                .select(UserIds::getId1)
                .selectAs(UserIds::getName, UserAndIds::getName2)
                .from(User.class)
                .LEFT_OUTER_JOIN("user_ids on user.id = user_ids.id2")
                .leftLike(User::getName, name)
                .equal(UserIds::getId1, id1)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql7")
    List<UserAndIds> selectList7(String name, Long id1);

    static String selectListSql7(final String name, final Long id1) {
        String sql = SqlBuilder.build()
                .selectAll()
                .selectAs(UserIds::getName, UserAndIds::getName2)
                .from(User.class)
                .LEFT_OUTER_JOIN("user_ids on user.id = user_ids.id2")
                .leftLike(User::getName, name)
                .equal(UserIds::getId1, id1)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql8")
    List<UserAndIds> selectList8(String name, Long id1);

    static String selectListSql8(final String name, final Long id1) {
        String sql = SqlBuilder.build()
                .selectAll()
                .selectAs(UserIds::getName, UserAndIds::getName2)
                .from(User.class)
                .leftJoin(UserIds.class, User::getId, UserIds::getId2)
                .leftLike(User::getName, name)
                .equal(UserIds::getId1, id1)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql9")
    List<UserAndIds> selectList9(List<Long> ids);

    static String selectListSql9(final List<Long> ids) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .in(User::getId, "ids", ids)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql9_2")
    List<UserAndIds> selectList9_2(List<Long> ids, List<String> names);

    static String selectListSql9_2(final List<Long> ids, List<String> names) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .in(User::getId, "ids", ids)
                .in(User::getName, "names", names)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql10")
    List<UserAndIds> selectList10(List<Long> id);

    static String selectListSql10(final List<Long> id) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .in(User::getId, id)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql11")
    List<UserAndIds> selectList11(UserAndIdsDTO userAndIdsDTO);

    static String selectListSql11(final UserAndIdsDTO userAndIdsDTO) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .in(User::getId, () -> SqlBuilder.build().select(UserIds::getId2).from(UserIds.class).in(UserIds::getId2, UserAndIdsDTO::getIds, userAndIdsDTO.getIds()))
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql12")
    List<UserAndIds> selectList12(List<Long> id2);

    static String selectListSql12(final List<Long> id2) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .in(User::getId, () -> SqlBuilder.build().select(UserIds::getId2).from(UserIds.class).in(UserIds::getId2, id2))
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

    @SelectProvider(value = UserMapper.class, method = "selectListSql13")
    List<UserAndIds> selectList13(Integer length);

    static String selectListSql13(final Integer length) {
        String sql = SqlBuilder.build()
                .selectAll()
                .from(User.class)
                .gt(SqlFunction.builder(User::getId).length(), "length", length)
                .toString();
        System.err.println("selectListSql1: " + sql);
        return sql;
    }

}
