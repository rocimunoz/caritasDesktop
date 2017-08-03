package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Ticket;

public class ProgramDAO {




private SqlSessionFactory sqlSessionFactory = null;

public ProgramDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public  Program findProgram(People people){
	Program program = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        program = session.selectOne("Program.findProgram", people);
    } finally {
        session.close();
    }
    System.out.println("findProgramById() --> "+program);
    return program;

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





}