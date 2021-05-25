package com.ifhc.mapper;

import com.ifhc.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getById(Integer id);
}
