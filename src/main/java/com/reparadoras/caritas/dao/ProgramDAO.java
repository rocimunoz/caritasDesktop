package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Ticket;

public class ProgramDAO {




private SqlSessionFactory sqlSessionFactory = null;

public ProgramDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}




public int insert(Program program){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Program.insert", program);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+program+") --> "+program.getId());
     return id;
 }

public int delete(People people){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Program.delete", people);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }

public int update(Program program){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Program.update", program);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+program+") --> "+program.getId());
     return id;
 }

public  List<Program> findAll(){
	List<Program> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Program.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  List<Program> findProgram(People people){
	List<Program> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Program.findProgram", people);
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}





}