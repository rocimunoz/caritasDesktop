package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.People;

public class FamilyDAO {




private SqlSessionFactory sqlSessionFactory = null;

public FamilyDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(Family family){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Family.update", family);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+family+") --> "+family.getId());
     return id;
 }

public int insert(Family family){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Family.insert", family);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+family+") --> "+family.getId());
     return id;
 }

public int delete(int idFamily){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Family.delete", idFamily);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }






}