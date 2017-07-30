package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;

public class ProgramDAO {




private SqlSessionFactory sqlSessionFactory = null;

public ProgramDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public  Program findProgramById(People people){
	Program program = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        program = session.selectOne("Program.findProgramById", people);
    } finally {
        session.close();
    }
    System.out.println("findProgramById() --> "+program);
    return program;

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





}