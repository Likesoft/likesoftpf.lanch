package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import avaliacao.bean.Aluno;
import avaliacao.bean.Pessoa;


public class DAOPessoa extends Dao {

	private static DAOPessoa instancia;

	public static DAOPessoa getInstancia() {
		if (instancia == null)
			instancia = new DAOPessoa();
		return instancia;
	}

	private DAOPessoa() {
	}

	public List<Aluno> consultaHQLAluno(Session sessao, String nome) {
		Query query = sessao.createQuery("FROM Aluno");
		return query.list();
	}

	public List<Aluno> consultaHQLParametrizado(Session sessao, String nome) {
		Query query = sessao.createQuery(
				"FROM Pessoa WHERE upper(nomePessoa) like upper(:nome)").setString(
				"nome", "%" + nome + "%");
		return query.list();
	}
	
	public List<Aluno> consultaCriteria(Session sessao, String nome) {
		Criteria criteria= sessao.createCriteria(Aluno.class);
		criteria.add(Restrictions.like("nomePessoa", "%" + nome + "%"));
		return criteria.list();
	}
	
	public void mostraAluno(Session sessao, String nome) {

		List<Aluno> listaAluno = new ArrayList<Aluno>();
		listaAluno = consultaHQLAluno(sessao,nome);
		
		for (Aluno aluno : listaAluno) {
			
			System.out.println("\n Aluno" + aluno.getNomePessoa() +"\n Matricula" + aluno.getMatricula());
			
		}
	}
	
}
