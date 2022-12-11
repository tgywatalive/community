package com.newcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary // Bean优先装配
public class AlphaDaoMyBatislmpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}
