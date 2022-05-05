package com.zc.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
@Data
@Table(name = UserIds.TABLE)
public class UserIds {

    public static final String TABLE = "user_ids";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id1;

    private Long id2;

    private String name;

}
