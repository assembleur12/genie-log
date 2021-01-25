package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Article;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.service.ConsoleService;

public class ArticleDAO extends DAO<Article> {

	public ArticleDAO() {
		super();
	}

	@Override
	public void create(Article obj) {
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery(
					"SELECT * FROM article");

			result.moveToInsertRow();
				result.updateBoolean("enRayon", obj.getdispo());
				result.updateFloat("prix", obj.getPrix());
				result.insertRow();
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	@Override
	public void delete(Article obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery(
					"SELECT * FROM article");

			while (result.next()) {
					int id = result.getInt("idArticle");
					if (id == obj.getIdArticle()) {
						result.deleteRow();
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	@Override
	public void update(Article obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery(
					"SELECT * FROM article");

			while (result.next()) {
					int id = result.getInt("idArticle");
					if (id == obj.getIdArticle()) {
						result.moveToCurrentRow();
						result.updateBoolean("enRayon", obj.getdispo());
						result.updateFloat("prix", obj.getPrix());
						result.updateRow();
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	public Integer findLastEntryId() {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM article");
			result.last();
			Integer id = result.getInt("idArticle");
			return id;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}
	
	@Override
	public Article find(Integer id) {
		BandedessineeDAO bdDAO = new BandedessineeDAO();
		FigurineDAO figDAO = new FigurineDAO();
		Bandedessinee bd = bdDAO.findByIdArticle(id);
		Figurine fig = figDAO.findByIdArticle(id);
		if (bd != null) {
			return bd;
		} else if (fig != null) {
			return fig;
		}
		return null;
	}
}