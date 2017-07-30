package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.People;

public class PeopleDAO {




private SqlSessionFactory sqlSessionFactory = null;

public PeopleDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int update(People person){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("People.update", person);
         id = session.insert("People.update", person);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+person+") --> "+person.getIdPeople());
     return id;
 }

public int insert(People person){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("People.insert", person);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+person+") --> "+person.getIdPeople());
     return id;
 }

public int delete(int idPeople){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("People.delete", idPeople);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }

public  List<People> findAll(){
	List<People> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("People.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  List<People> findPeople(People people){
	List<People> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("People.findPeople", people);
    } finally {
        session.close();
    }
    System.out.println("findPeople() --> "+list);
    return list;

}


}