package com.zbl.mapper;

import com.zbl.aop.Master;
import com.zbl.aop.Master2;
import com.zbl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zbl
 * @Date: 17:28 2020/3/1
 * @Description:
 */
@Repository
@Mapper
public interface UserMapper {

    User queryUserByName(String name);

    List<User> findAll();

    @Master
    void updateIsSystem(@Param("userId") Long userId,
                        @Param("isSystem") boolean isSystem
                        );
    @Master2
    void updateIsSystem2(@Param("userId") Long userId,
                        @Param("isSystem") boolean isSystem
    );

    User findById(@Param("userId") Long userId);
}
