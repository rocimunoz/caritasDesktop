package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;

public class AddressDAO {




private SqlSessionFactory sqlSessionFactory = null;

public AddressDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(Address address){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Address.update", address);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+address+") --> "+address.getId());
     return id;
 }

public int insert(Address address){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Address.insert", address);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+address+") --> "+address.getId());
     return id;
 }

public int delete(Address address){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Address.delete", address);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }








}