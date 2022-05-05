package com.zc.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
@Data
public class UserAndIds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sex;

    private Long id1;

    private Long id2;

    private String name2;

}
