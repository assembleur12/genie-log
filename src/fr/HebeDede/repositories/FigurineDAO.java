package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.service.ConsoleService;

public class FigurineDAO extends DAO<Figurine> {
	
	ArticleDAO articleDAO = new ArticleDAO();

	public FigurineDAO() {
		super();
	}

	@Override
	public void create(Figurine obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			articleDAO.create(obj);
			Integer articleId = articleDAO.findLastEntryId();
			
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM figurine");
			
				result.moveToInsertRow();
				result.updateString("description", obj.getDescription());
				result.updateInt("taille", obj.getTaille());
				result.updateInt("Article_idArticle", articleId);
				result.insertRow();
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	@Override
	public void delete(Figurine obj) {
		
	}

	@Override
	public void update(Figurine obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM figurine");

			while (result.next()) {
					int id = result.getInt("idFigurine");
					if (id == obj.getIdFigurine()) {
						result.moveToCurrentRow();
						result.updateString("description", obj.getDescription());
						result.updateInt("taille", obj.getTaille());
						result.updateRow();
						
						articleDAO.update(obj);
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	public List<Figurine> findAllFig() {
		List<Figurine> figList = new ArrayList<Figurine>();
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.Article_idArticle = article.idArticle");
			while (result.next()) {
				Figurine fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("description"),
						result.getInt("taille"),
						result.getInt("idFigurine"));
				figList.add(fig);
			}
			return figList;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	
	}

	@Override
	public Figurine find(Integer id) {
		Figurine fig = new Figurine();
		
		Statement stmt = null;
		ResultSet result = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle "
			        		+ "WHERE idFigurine = " + id);
			if(result.first()) {
				fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("description"),
						result.getInt("taille"),
						id);
				return fig;
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return null;
	}
	
	public Figurine findByIdArticle(Integer id) {
		Figurine fig = new Figurine();
		
		Statement stmt = null;
		ResultSet result = null;
	
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.Article_idArticle = article.idArticle "
			        		+ "WHERE Article_idArticle = " + id);
			if(result.first()) {
				fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						id,
						result.getString("description"),
						result.getInt("taille"),
						result.getInt("idFigurine"));
				return fig;
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return null;
	}
}