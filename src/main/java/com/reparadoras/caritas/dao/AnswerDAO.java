package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.filter.FilterAnswer;
import com.reparadoras.caritas.filter.FilterTicket;
import com.reparadoras.caritas.model.Answer;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;

public class AnswerDAO {




private SqlSessionFactory sqlSessionFactory = null;

public AnswerDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public int insert(Answer answer){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Answer.insert", answer);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+answer+") --> "+answer.getId());
     return id;
 }


public int update(Answer answer){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.update("Answer.update", answer);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+answer+") --> "+answer.getId());
     return id;
 }


public int delete(People people){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Answer.delete", people);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }



public  List<Answer> findAll(){
	List<Answer> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Answer.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  List<Answer> findAnswer(FilterAnswer filter){
	List<Answer> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Answer.findAnswer", filter);
    } finally {
        session.close();
    }
    System.out.println("findAnswer() --> "+list);
    return list;

}

/*
public  Ticket findTicket(People people){
	Ticket ticket = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        ticket = session.selectOne("Ticket.findTicket", people);
    } finally {
        session.close();
    }
    System.out.println("findTicket() --> "+ticket);
    return ticket;

}*/


}