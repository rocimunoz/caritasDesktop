package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import com.reparadoras.caritas.model.OtherInfo;


public class OtherInfoDAO {




private SqlSessionFactory sqlSessionFactory = null;

public OtherInfoDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(OtherInfo otherInfo){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("OtherInfo.update", otherInfo);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+otherInfo+") --> "+otherInfo.getId());
     return id;
 }

public int insert(OtherInfo otherInfo){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("OtherInfo.insert", otherInfo);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+otherInfo+") --> "+otherInfo.getId());
     return id;
 }

public int delete(OtherInfo otherInfo){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("OtherInfo.delete", otherInfo);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }








}