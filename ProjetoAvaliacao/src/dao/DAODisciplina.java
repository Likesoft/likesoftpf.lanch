package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import avaliacao.bean.Aluno;
import avaliacao.bean.Disciplina;
import avaliacao.bean.Pessoa;


public class DAODisciplina extends Dao {

	private static DAODisciplina instancia;

	public static DAODisciplina getInstancia() {
		if (instancia == null)
			instancia = new DAODisciplina();
		return instancia;
	}

	private DAODisciplina() {
	}

	public List<Disciplina> consultaHQLDisciplina(Session sessao) {
		Query query = sessao.createQuery("FROM Disciplina");
		return query.list();
	}

	public List<Disciplina> consultaHQLParametrizado(Session sessao, String nome) {
		Query query = sessao.createQuery(
				"FROM Disciplina WHERE upper(nomeDisciplina) like upper(:NOME)").setString(
				"nomePessoa", "%" + nome + "%");
		return query.list();
	}
	
	public List<Disciplina> consultaCriteria(Session sessao, String nome) {
		Criteria criteria= sessao.createCriteria(Disciplina.class);
		criteria.add(Restrictions.like("nomeDisciplina", "%" + nome + "%"));
		return criteria.list();
	}
	
	public void mostraDisciplina(Session sessao, String nome) {

		List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();
		listaDisciplina = consultaHQLDisciplina(sessao);
		
		for (Disciplina disciplina : listaDisciplina) {
			
			System.out.println("\n Codigo :" + disciplina.getCodDisciplina() +"\n Disciplina" + disciplina.getNomeDisciplina());
			
		}
	}
	
	public Disciplina criaDisciplina(Session session,String nome){
		Disciplina disciplina = new Disciplina();
		
		disciplina.setNomeDisciplina(nome);
		disciplina.setDsObservacao("Materia de Matemática");
		instancia.salvar(session, disciplina);
		
		return disciplina;
	}
	
}
