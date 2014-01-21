package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

import avaliacao.bean.Aluno;
import avaliacao.bean.Cidade;
import avaliacao.bean.Disciplina;
import avaliacao.bean.Notas;
import avaliacao.bean.Pessoa;

import dao.DAOCidade;
import dao.DAONota;
import dao.DAOPessoa;

public class DAOPessoaTest {
	private static final String PESSOA = "ANTONIO";
	Session sessao;
	DAOPessoa daoPessoa;
	DAOCidade daoCidade;
	DAONota daoNota;
	

	@Before
	public void setUp(){
		HibernateUtil.configura();
		sessao = HibernateUtil.getSessao();
		daoPessoa = DAOPessoa.getInstancia();
		daoCidade = DAOCidade.getInstancia();
		daoNota = DAONota.getInstancia();
	}

	@Test
	public void testConsultaHQL() {
		
		Aluno aluno = criaAluno(PESSOA);
		List<Aluno> alunos = daoPessoa.consultaHQLAluno(sessao, "");
		assertEquals (alunos.size(),1);
		assertEquals (alunos.get(0).getNomePessoa(), PESSOA);
		
		daoNota.criaNota(alunos.get(0), 98.5);
		daoNota.criaNota(alunos.get(0), 80.0);
		daoNota.criaNota(alunos.get(0), 70.0);
		
		Aluno alunoNotas = associaNotaAluno(alunos.get(0));
		assertEquals(alunoNotas,aluno);
		
	}

	@Test
	public void testConsultaHQLParametrizado() {
	
		List<Aluno> listaPessoa = daoPessoa.consultaHQLParametrizado(sessao,PESSOA);
		assertEquals(listaPessoa.size(), 0);
		if(listaPessoa.size() > 0){
			assertEquals (listaPessoa.get(0).getNomePessoa(),PESSOA);
		}	
	}

	@Test
	public void testConsultaCriteria() {
	
		List<Aluno> listaPessoa = daoPessoa.consultaCriteria(sessao,PESSOA);
		assertEquals(listaPessoa.size(), 0);
		if(listaPessoa.size() > 0){
			assertEquals (listaPessoa.get(0).getNomePessoa(),PESSOA);
		}	
		
	}
	
	private Aluno criaAluno(String nome){
		
		Aluno aluno = new Aluno();
		aluno.setNomePessoa(nome);
		aluno.setIdade("18");
		aluno.setMatricula("9445");
		aluno.setSerie("8");
		aluno.setCidade(daoCidade.criaCidade("Pontao"));
		
		daoPessoa.salvar(sessao, aluno);
		return aluno;
	}
	
	private Aluno associaNotaAluno(Aluno aluno){
		
		List<Notas> lisNotas =  daoNota.getNotaByAluno(sessao, new Long(aluno.getCodPessoa()));
		aluno.setNotas(lisNotas);
		daoPessoa.salvar(sessao, aluno);
		return aluno;
	}
}
