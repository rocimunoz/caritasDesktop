package com.reparadoras.caritas.dao;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;

public class RelativeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public RelativeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public  List<Relative> findAllRelatives(){
	List<Relative> relatives = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	relatives = session.selectList("Relative.findAllRelatives");
    } finally {
        session.close();
    }
    System.out.println("findAllRelatives() --> ");
    return relatives;

}

public  List<Relative> findRelative(Relative relativeFilter){
	List<Relative> relatives = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	relatives = session.selectList("Relative.findRelative", relativeFilter);
    } finally {
        session.close();
    }
    System.out.println("findRelative() --> "+relativeFilter);
    return relatives;

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

public int delete(Relative relative){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Relative.delete", relative);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }






}