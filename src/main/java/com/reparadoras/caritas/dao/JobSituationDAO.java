package com.reparadoras.caritas.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.People;

public class JobSituationDAO {




private SqlSessionFactory sqlSessionFactory = null;

public JobSituationDAO(SqlSessionFactory sqlSessionFactory){
    this.sqlSessionFactory = sqlSessionFactory;
}

public  List<JobSituation> findAll(){
	List<JobSituation> list = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
        list = session.selectList("JobSituation.findAll");
    } finally {
        session.close();
    }
    System.out.println("findAll() --> "+list);
    return list;

}

public  JobSituation findJobSituation(JobSituation jsFilter){
	JobSituation jobSituation = null;
    SqlSession session = sqlSessionFactory.openSession();

    try {
    	jobSituation = session.selectOne("JobSituation.findJobSituation", jsFilter);
       
    } finally {
        session.close();
    }
    System.out.println("findJobSituation() --> "+jobSituation);
    return jobSituation;

}







}