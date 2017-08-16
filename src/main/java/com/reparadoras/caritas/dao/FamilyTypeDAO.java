package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.People;

public class FamilyTypeDAO {




private SqlSessionFactory sqlSessionFactory = null;

public FamilyTypeDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public  List<FamilyType> findAll(){
	List<FamilyType> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("FamilyType.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  FamilyType findFamilyType(FamilyType fType){
	FamilyType familyType = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        familyType = session.selectOne("FamilyType.findFamilyType", fType);
       
    } finally {
        session.close();
    }
    System.out.println("findFamilyType() --> "+familyType);
    return familyType;

}







}