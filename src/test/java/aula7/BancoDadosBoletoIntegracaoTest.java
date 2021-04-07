package aula7;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class BancoDadosBoletoIntegracaoTest {

	static BancoDeDadosBoleto bancoDados = new BancoDeDadosBoleto();
	
	@Before
	public void executaAntesMetodo() {
		bancoDados.deletaTudo();
	}
	
	@AfterClass
	public static void limpaTudoDepoisAcabarTestes() {
		bancoDados.deletaTudo();
	}
	
	
	@Test
	public void testInsert() {
		Boleto b = new Boleto();
		b.valor = 1234;
		b.sacado = "Pagamentos SA";
		bancoDados.insertBoleto(b);
		Boleto funcionarioSalvoBanco =  bancoDados.selectBoleto(b.idBoleto);
		Assert.assertNotNull(funcionarioSalvoBanco);
		
	}
	
	@Test
	public void testDelete() {
		Boleto b = new Boleto();
		b.valor = 7895;
		b.sacado = "Cliente Ltda";
		bancoDados.insertBoleto(b);
		int idBoleto = b.idBoleto;
		bancoDados.deleteBoleto(idBoleto);
		Boleto funcDoBanco = bancoDados.selectBoleto(idBoleto);
		Assert.assertNull(funcDoBanco);
		
	}
	
	@Test
	public void testSelect() {
		Boleto b = new Boleto();
		b.valor = 456123;
		b.sacado = "Teste QTS Ltda";
		bancoDados.insertBoleto(b);
		int tamanhoLista = bancoDados.listBoleto().size();
		Assert.assertTrue(tamanhoLista > 0);
		Assert.assertEquals(1, tamanhoLista);
	}

}
