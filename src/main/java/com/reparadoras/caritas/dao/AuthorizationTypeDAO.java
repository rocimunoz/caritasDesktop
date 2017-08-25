package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.People;

public class AuthorizationTypeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public AuthorizationTypeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public  List<AuthorizationType> findAll(){
	List<AuthorizationType> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("AuthorizationType.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  AuthorizationType findAuthorizationType(AuthorizationType aTypeFilter){
	AuthorizationType aType = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	aType = session.selectOne("AuthorizationType.findAuthorizationType", aTypeFilter);
       
    } finally {
        session.close();
    }
    System.out.println("findAuthorizationType() --> "+aType);
    return aType;

}







}