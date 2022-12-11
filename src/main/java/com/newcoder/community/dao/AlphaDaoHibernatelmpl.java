package com.newcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate") // 自定义Bean名字
public class AlphaDaoHibernatelmpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }

}
