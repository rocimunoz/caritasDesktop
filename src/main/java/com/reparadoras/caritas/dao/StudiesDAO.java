package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Studies;

public class StudiesDAO {




private SqlSessionFactory sqlSessionFactory = null;

public StudiesDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public  List<Studies> findAll(){
	List<Studies> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("Studies.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  Studies findStudies(Studies sFilter){
	Studies studies = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	studies = session.selectOne("Studies.findStudies", sFilter);
       
    } finally {
        session.close();
    }
    System.out.println("findStudies() --> "+studies);
    return studies;

}







}