package com.reparadoras.caritas.dao;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Expense;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;

public class ExpensesDAO {




private SqlSessionFactory sqlSessionFactory = null;

public ExpensesDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public  List<Expense> findAllExpenses(){
	List<Expense> expenses = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	expenses = session.selectList("Expense.findAllExpenses");
    } finally {
        session.close();
    }
    System.out.println("findAllExpenses() --> ");
    return expenses;

}

public  List<Expense> findExpenses(Expense expenseFilter){
	List<Expense> expenses = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	expenses = session.selectList("Expense.findExpense", expenseFilter);
    } finally {
        session.close();
    }
    System.out.println("findExpense() --> "+expenseFilter);
    return expenses;

}



public int update(Expense expense){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Expense.update", expense);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+expense+") --> "+expense.getId());
     return id;
 }

public int insert(Expense expense){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Expense.insert", expense);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+expense+") --> "+expense.getId());
     return id;
 }

public int deleteByProgram(Program program){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Expense.deleteByProgram", program);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }

public int delete(Expense expense){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Expense.delete", expense);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }






}