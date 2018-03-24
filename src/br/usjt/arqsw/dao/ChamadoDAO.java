
package br.usjt.arqsw.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
/**
 * 
 * @author Leandro Pinheiro de Holanda 816113762
 *
 */
@Repository
public class ChamadoDAO {

	@PersistenceContext
	EntityManager manager;
	
	public List<Chamado> listarChamadosAbertos(Fila fila) throws IOException {
		
		String jpql = "select c from Chamado c where c.fila = :fila and c.status = :status";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("fila", fila);
		query.setParameter("status", Chamado.ABERTO);

		@SuppressWarnings("unchecked")
		List<Chamado> result = query.getResultList();
		return result;
	}
	
	public List<Chamado> listarChamados(Fila fila) throws IOException{
		fila = manager.find(Fila.class, fila.getId());
		String jpql = "select c from Chamado c where c.fila = :fila";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("fila", fila);
		
		@SuppressWarnings("unchecked")
		List<Chamado> result = query.getResultList();
		return result;
	}

}
