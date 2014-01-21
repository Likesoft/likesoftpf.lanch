package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

import avaliacao.bean.Aluno;
import avaliacao.bean.Notas;


public class DAONota extends Dao {

	private static DAONota instancia;
	Session sessao = HibernateUtil.getSessao();

	public static DAONota getInstancia() {
		if (instancia == null)
			instancia = new DAONota();
		return instancia;
	}

	private DAONota() {
	}

	public List<Notas> getNotaByAluno(Session sessao,Long cdAluno) {
		Query query = sessao.createQuery("FROM Notas  nota WHERE nota.aluno.codPessoa = " +cdAluno );
		return query.list();
	}
	
	public List<Notas> getNotas(Session sessao) {
		Query query = sessao.createQuery("FROM Notas");
		return query.list();
	}
	
	public void criaNota(Aluno aluno, Double nota){
		
		Notas notas = new Notas();
		
		notas.setAluno(aluno);
		notas.setNota(nota);
		
		instancia.salvar(sessao, notas);
	}
	
}
