package com.zc;

import com.github.pagehelper.PageHelper;
import com.zc.function.SqlFunction;
import com.zc.mapper.UserMapper;
import com.zc.model.User;
import com.zc.model.UserAndIds;
import com.zc.model.UserAndIdsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ZcMapperApplicationTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 关联查询，但是只映射单表
     */
    @Test
    void contextLoads() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads2() {
        List<UserAndIds> userList = userMapper.selectList2(null);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads3() {
        List<HashMap<String, Object>> userList = userMapper.selectList3("男", "张");
        userList.forEach(System.err::println);
    }


    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads4() {
        List<UserAndIds> userList = userMapper.selectList4("张无忌");
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads5() {
        List<UserAndIds> userList = userMapper.selectList5("张无忌");
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads6() {
        List<UserAndIds> userList = userMapper.selectList6("张无忌", 1L);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads7() {
        List<UserAndIds> userList = userMapper.selectList7("张无忌", 1L);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads8() {
        List<UserAndIds> userList = userMapper.selectList8("张无忌", 1L);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads9() {
        List<Long> id2s = new ArrayList<>();
        id2s.add(1L);
        id2s.add(2L);
        List<UserAndIds> userList = userMapper.selectList9(id2s);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads10() {
        List<Long> id2s = new ArrayList<>();
        id2s.add(1L);
        id2s.add(2L);
        List<UserAndIds> userList = userMapper.selectList10(id2s);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads11() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        UserAndIdsDTO userAndIdsDTO = new UserAndIdsDTO();
        userAndIdsDTO.setIds(ids);
        List<UserAndIds> userList = userMapper.selectList11(userAndIdsDTO);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads12() {
        PageHelper.startPage(1, 1);
        List<Long> id2s = new ArrayList<>();
        id2s.add(1L);
        id2s.add(2L);
        List<UserAndIds> userList = userMapper.selectList12(id2s);
        userList.forEach(System.err::println);
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads13Pre() {
        System.err.println(new SqlFunction<User>().builder(User::getId).length());
    }

    /**
     * 关联查询，映射综合字段
     */
    @Test
    void contextLoads13() {
        List<UserAndIds> userList = userMapper.selectList13(1);
        userList.forEach(System.err::println);
    }

}
