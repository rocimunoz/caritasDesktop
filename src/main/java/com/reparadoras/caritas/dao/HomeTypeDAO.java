package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.HomeType;
import com.reparadoras.caritas.model.People;

public class HomeTypeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public HomeTypeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}


public  HomeType findHomeType(HomeType hType){
	HomeType homeType = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	homeType = session.selectOne("HomeType.findHomeType", hType);
       
    } finally {
        session.close();
    }
    System.out.println("findHomeType() --> "+homeType);
    return homeType;

}





public  List<HomeType> findAll(){
	List<HomeType> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("HomeType.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}




}