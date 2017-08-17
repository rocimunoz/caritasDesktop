package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.People;

public class HomeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public HomeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(Home home){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Home.update", home);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+home+") --> "+home.getId());
     return id;
 }

public int insert(Home home){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Home.insert", home);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+home+") --> "+home.getId());
     return id;
 }








}