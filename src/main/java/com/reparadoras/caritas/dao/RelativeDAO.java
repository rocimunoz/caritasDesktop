package com.reparadoras.caritas.dao;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;

public class RelativeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public RelativeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(Relative relative){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Relative.update", relative);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+relative+") --> "+relative.getId());
     return id;
 }

public int insert(Relative relation){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Relative.insert", relation);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+relation+") --> "+relation.getId());
     return id;
 }

public int delete(int idRelation){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Relative.delete", idRelation);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }






}