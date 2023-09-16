package br.inatel.labs.labjpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.labs.labjpa.entity.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class FornecedorService
{
	@PersistenceContext
	private EntityManager em;
	
	public Fornecedor salvar(Fornecedor f)
	{
		f = em.merge(f);
		return f;
	}
	
	public Fornecedor buscarPeloId(Long id)
	{
		Fornecedor fornecedorEncontrado = em.find(Fornecedor.class, id);
		return fornecedorEncontrado;
	}
	
	public List<Fornecedor> listar ()
	{
		String jpql = "select f from Fornecedor f";
		List<Fornecedor> fornecedores = em.createQuery(jpql, Fornecedor.class).getResultList();
		return fornecedores;
	}
	
	public void remover(Fornecedor f)
	{
		f = em.merge(f);
		em.remove(f);
	}
}
