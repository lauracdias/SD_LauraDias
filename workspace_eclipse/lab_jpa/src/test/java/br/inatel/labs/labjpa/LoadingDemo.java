package br.inatel.labs.labjpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.inatel.labs.labjpa.entity.NotaCompra;
import br.inatel.labs.labjpa.entity.NotaCompraItem;
import br.inatel.labs.labjpa.service.NotaCompraService;

@SpringBootTest
public class LoadingDemo 
{
	@Autowired
	private NotaCompraService service;
	
	@Test
	public void demoPlanejandoConsulta()
	{
		try 
		{
			NotaCompra nota = service.buscarNotaCompraPeloIdComListaItem( 1L );
			List<NotaCompraItem> listaNotaCompraItem = nota.getListaNotaCompraItem();
			
			for(NotaCompraItem item : listaNotaCompraItem)
			{
				System.out.println(item);
			}
			
			System.out.println("Se chegou até aqui, o planejamento da consulta funcionou");
		
		}
		catch (Exception e) 
		{
			System.out.println("O carregamento foi LAZY e por isso lançou exception");
			e.printStackTrace();
		}
	}

	
	@Test
	public void demoLazyLoading()
	{
		try
		{
			Optional<NotaCompra> opNota = service.buscarNotaCompraPeloId(1L);
			
			if(opNota.isPresent()) {
				NotaCompra nota = opNota.get();
				int nItems = nota.getListaNotaCompraItem().size();
				System.out.println(nItems);
			}
		}
		catch (Exception e)
		{
			System.out.println("O carregamento foi LAZY e por isso lançou exception");
			e.printStackTrace();
		}
	}

	
	@Test
	public void demoEagerLoading() 
	{
		try 
		{
			Optional<NotaCompraItem> opItem = service.buscarNotaCompraItemPeloId(1L);
			if(opItem.isPresent()) {
				NotaCompraItem item = opItem.get();
				LocalDate dataEmissao = item.getNotaCompra().getDataEmissao();
				System.out.println(dataEmissao);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}