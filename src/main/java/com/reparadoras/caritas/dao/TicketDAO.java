package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;

public class TicketDAO {




private SqlSessionFactory sqlSessionFactory = null;

public TicketDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public int insert(Ticket ticket){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Ticket.insert", ticket);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+ticket+") --> "+ticket.getId());
     return id;
 }



public  List<Ticket> findAll(){
	List<Ticket> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Ticket.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

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

}


}