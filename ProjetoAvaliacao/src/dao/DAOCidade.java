package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import util.HibernateUtil;

import avaliacao.bean.Aluno;
import avaliacao.bean.Cidade;
import avaliacao.bean.Pessoa;


public class DAOCidade extends Dao {

	private static DAOCidade instancia;
	Session sessao = HibernateUtil.getSessao();
	

	public static DAOCidade getInstancia() {
		if (instancia == null)
			instancia = new DAOCidade();

		
		return instancia;
	}

	private DAOCidade() {
	}

	public List<Aluno> consultaHQLCidade(Session sessao, String nome) {
		Query query = sessao
				.createQuery("FROM Cidade WHERE upper(nome) like upper('%"
						+ nome + "%')");
		return query.list();
	}

	public List<Aluno> consultaHQLParametrizado(Session sessao, String nome) {
		Query query = sessao.createQuery(
				"FROM Cidade WHERE upper(nome) like upper(:NOME)").setString(
				"nm_pessoa", "%" + nome + "%");
		return query.list();
	}
	
	public List<Pessoa> consultaCriteria(Session sessao, String nome) {
		Criteria criteria= sessao.createCriteria(Cidade.class);
		criteria.add(Restrictions.like("nome", "%" + nome + "%"));
		return criteria.list();
	}
	
	public Cidade criaCidade(String nome){
		
		Cidade cidade = new Cidade();
		cidade.setNome(nome);
		cidade.setUf("RS");
		
		instancia.salvar(sessao, cidade);
		
		return cidade;
	}

}
