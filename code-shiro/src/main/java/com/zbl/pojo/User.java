package com.zbl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zbl
 * @Date: 17:02 2020/3/1
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;

    private String name;

    private String pwd;

    private String perms;

    private String salt;

    private String json;

    private boolean isSystem;

    private Date dateTime;

    private Date timeStamp;
}
