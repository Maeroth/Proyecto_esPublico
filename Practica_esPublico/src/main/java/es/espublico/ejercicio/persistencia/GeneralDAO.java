package es.espublico.ejercicio.persistencia;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import es.espublico.ejercicio.utilidades.Constantes;

/**
 * Clase Abstracta para el DAO genérico. De esta heredan los DAOs que se van a utilizar
 * @author Manuel León
 * @since 04/02/2023
 * @param <T>
 */
public abstract class GeneralDAO<T> {
	private SessionFactory sessionFactory;

	//Constructor
	public GeneralDAO() throws HibernateException{}
	
	/*
	 * Devuelve todos los elementos de una clase pasada por parámetros
	 */
	@SuppressWarnings("unchecked")
	public List<T> devolverTodaLaTabla(T clase){
		Session session = null;
		List<T> listaSalida = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(clase.getClass());
			listaSalida = criteria.list(); //devolvemos toda la tabla
		}catch(HibernateException e) {
			throw new HibernateException(Constantes.MSJ_ERROR_LEER_BD, e);
		}finally {
			if(session != null) { 
				session.close(); //cerramos la sesión
			}
		}
		return listaSalida;
	}

	
	//GETTERS Y SETTERS
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
