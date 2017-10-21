package com.reparadoras.caritas.dao;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;

public class IncomesDAO {




private SqlSessionFactory sqlSessionFactory = null;

public IncomesDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public  List<Income> findAllIncomes(){
	List<Income> incomes = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	incomes = session.selectList("Income.findAllIncomes");
    } finally {
        session.close();
    }
    System.out.println("findAllIncomes() --> ");
    return incomes;

}

public  List<Income> findIncomes(Income incomeFilter){
	List<Income> incomes = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	incomes = session.selectList("Income.findIncome", incomeFilter);
    } finally {
        session.close();
    }
    System.out.println("findIncome() --> "+incomeFilter);
    return incomes;

}



public int update(Income income){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
    	 session.update("Income.update", income);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("update("+income+") --> "+income.getId());
     return id;
 }

public int insert(Income income){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         id = session.insert("Income.insert", income);
        
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("insert("+income+") --> "+income.getId());
     return id;
 }

public int delete(Income income){
    int id = -1;
     SqlSession session = sqlSessionFactory.openSession();

     try {
         session.delete("Income.delete", income);
     } finally {
         session.commit();
         session.close();
     }
     System.out.println("delete("+id+") --> ");
     return id;
 }






}