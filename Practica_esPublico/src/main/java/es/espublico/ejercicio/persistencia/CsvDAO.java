package es.espublico.ejercicio.persistencia;


import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import es.espublico.ejercicio.modelo.Venta;
import es.espublico.ejercicio.utilidades.Constantes;

/**
 * Clase DAO encargada de dar soporte al ejercicio 1 (Lectura, volcado y escritura de CSV)
 * @author Manuel León
 * @since 07/02/2023 
  */
public class CsvDAO extends GeneralDAO<Venta> implements ItfzCsvDAO{

	/**
	 * Inserta una colección de entidades tipo Venta en base de datos
	 * @throws HibernateException
	 */
	@Override
	public void altaVentas(List<Venta> listaVentas) throws HibernateException {
		//El método ya lleva una transacción implícita, por lo tanto no hace falta declararla

			Session session = null;
			Transaction transaccion = null;
			
			try {
				session = super.getSessionFactory().openSession();
				transaccion = session.getTransaction(); //Obtenemos la transacción
				transaccion.begin(); //Abrimos la transacción
				
				for(Venta venta: listaVentas) {
					session.save(venta);
				}
				session.flush(); //Todo lo que hay en memoria lo volcamos a BD
				
				transaccion.commit(); //Todo ha ido bien, hacemos commit
			}catch(HibernateException e) {
				transaccion.rollback(); //Ha ido mal, revertimos
				throw new HibernateException(Constantes.MSJ_ERROR_INSERT+" " +e.getCause().getMessage(), e); //relanzamos la excepción
			}finally {
				if(session != null) {
					session.close(); //Cerramos la sesión
				}
			}
	}

}
