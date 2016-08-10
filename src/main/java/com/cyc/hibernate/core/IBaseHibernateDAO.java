package com.cyc.hibernate.core;

import java.util.List;

import org.hibernate.Session;


/**
 * Data access interface for domain model
 * @author MyEclipse Persistence Tools
 */
public interface IBaseHibernateDAO<T> {
	public Session getSession();
	public void save(T t);
	public void delete(T t);
    List<T> findByPage(String hql,int page,int pagesize);
    List<T> findByPage(String hql,int page,int pagesize,Object...params);
 	
}