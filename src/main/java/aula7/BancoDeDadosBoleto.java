package aula7;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author fernando.dipp
 */

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class BancoDeDadosBoleto {
	private Connection connection = null;

	public BancoDeDadosBoleto() {
		conectaBanco();
	}

	private void conectaBanco() {

		try {
			String url = "jdbc:postgresql://localhost/qts";
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "123456");
			connection = DriverManager.getConnection(url, props);
			if (connection != null) {
				System.out.println("STATUS--->Conectado com sucesso!");
			} else {
				System.out.println("STATUS--->Não foi possivel realizar conexão");
			}

		} catch (Exception e) {
			System.out.println("Error ao conectar o banco de dados");
			e.printStackTrace();
		}

	}

	public void deletaTudo() {
		String sql = "delete from boleto";
		System.out.println("sql deleteTudo: " + sql);
		try {
			connection.createStatement().execute(sql);

		} catch (Exception e) {
			System.out.println("Error na remocao boleto " + e);

		}
	}

	public int insertBoleto(Boleto b) {
		String sql = "insert into boleto (valor,sacado) values(" + b.valor + ",'" + b.sacado
				+ "') RETURNING idBoleto";

		System.out.println("sql insert " + sql);

		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			rs.next();
			int idBoleto = rs.getInt(1);
			b.idBoleto = idBoleto;

			return idBoleto;

		} catch (Exception e) {
			System.out.println("Error na insercao boleto " + e);
			return -1;
		}

	}

	public boolean deleteBoleto(int idBoleto) {
		String sql = "delete from  boleto where idBoleto = " + idBoleto;
		System.out.println("sql delete " + sql);

		try {
			connection.createStatement().execute(sql);

		} catch (Exception e) {
			System.out.println("Error na remocao boleto " + e);
			return false;
		}

		return true;
	}

	public List<Boleto> listBoleto() {
		List<Boleto> lista = new ArrayList<Boleto>();
		String sql = "select * from boleto";
		System.out.println("sql select " + sql);
		try {
			connection.createStatement().execute(sql);
			ResultSet resultSet = connection.createStatement().executeQuery(sql);

			while (resultSet.next()) {
				Boleto pessoa = new Boleto();
				pessoa.idBoleto = (resultSet.getInt("idBoleto"));
				pessoa.valor = (resultSet.getFloat("valor"));
				pessoa.sacado = (resultSet.getString("sacado"));
				lista.add(pessoa);
			}

		} catch (Exception e) {

		}

		return lista;
	}

	public Boleto selectBoleto(int idBoleto) {

		String sql = "select * from boleto where idBoleto =" + idBoleto;
		;
		System.out.println("sql select " + sql);
		Boleto b = null;
		try {
			connection.createStatement().execute(sql);
			ResultSet resultSet = connection.createStatement().executeQuery(sql);

			while (resultSet.next()) {
				b = new Boleto();
				b.idBoleto = (resultSet.getInt("idBoleto"));
				b.valor = (resultSet.getFloat("valor"));
				b.sacado = (resultSet.getString("sacado"));
			}

		} catch (Exception e) {

		}

		return b;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
