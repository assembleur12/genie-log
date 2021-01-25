package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.service.ConsoleService;

public class BandedessineeDAO extends DAO<Bandedessinee> {
	
	ArticleDAO articleDAO = new ArticleDAO();

	public BandedessineeDAO() {
		super();
	}

	@Override
	public void create(Bandedessinee obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			articleDAO.create(obj);
			Integer articleId = articleDAO.findLastEntryId();
			
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery(
					"SELECT * FROM bandedessinee");
			
				result.moveToInsertRow();
				result.updateString("auteur", obj.getAuteur());
				result.updateString("categorie", obj.getCategorie());
				result.updateString("collection", obj.getCollection());
				result.updateString("description", obj.getDescription());
				result.updateString("editeur", obj.getEditeur());
				result.updateString("etat", obj.getEtat());
				result.updateString("libelle", obj.getLibelle());
				result.updateInt("nbrPages", obj.getNbrPages());
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
	public void delete(Bandedessinee obj) {
	}

	@Override
	public void update(Bandedessinee obj) {
		
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM bandedessinee");

			while (result.next()) {
					int id = result.getInt("idBandeDessinee");
					if (id == obj.getIdBandeDessinee()) {
						result.moveToCurrentRow();
						result.updateString("auteur", obj.getAuteur());
						result.updateString("categorie", obj.getCategorie());
						result.updateString("collection", obj.getCollection());
						result.updateString("description", obj.getDescription());
						result.updateString("editeur", obj.getEditeur());
						result.updateString("etat", obj.getEtat());
						result.updateString("libelle", obj.getLibelle());
						result.updateInt("nbrPages", obj.getNbrPages());
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

	@Override
	public Bandedessinee find(Integer id) {
		Bandedessinee bd = new Bandedessinee();
		
		Statement stmt = null;
		ResultSet result = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM bandedessinee "
			        		+ "INNER JOIN article on bandedessinee.article_idArticle = article.idArticle "
			        		+ "WHERE idBandeDessinee = " + id);
			if(result.first()) {
				bd = new Bandedessinee(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("auteur"),
						result.getString("categorie"),
						result.getString("collection"),
						result.getString("description"),
						result.getString("editeur"),
						result.getString("etat"),
						result.getString("libelle"),
						result.getInt("nbrPages"),
						id);
				return bd;
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
	
	public List<Bandedessinee> findAllBD() {
		List<Bandedessinee> bdList = new ArrayList<Bandedessinee>();
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM bandedessinee "
			        		+ "INNER JOIN article on bandedessinee.Article_idArticle = article.idArticle");
			while (result.next()) {
				Bandedessinee bd = new Bandedessinee(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("auteur"),
						result.getString("categorie"),
						result.getString("collection"),
						result.getString("description"),
						result.getString("editeur"),
						result.getString("etat"),
						result.getString("libelle"),
						result.getInt("nbrPages"),
						result.getInt("idBandeDessinee"));
				bdList.add(bd);
			} 
			return bdList;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}
	
	public Bandedessinee findByIdArticle(Integer id) {
		Bandedessinee bd = new Bandedessinee();
		
		Statement stmt = null;
		ResultSet result = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM bandedessinee "
			        		+ "INNER JOIN article on bandedessinee.Article_idArticle = article.idArticle "
			        		+ "WHERE Article_idArticle = " + id);
			if(result.first()) {
				bd = new Bandedessinee(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						id,
						result.getString("auteur"),
						result.getString("categorie"),
						result.getString("collection"),
						result.getString("description"),
						result.getString("editeur"),
						result.getString("etat"),
						result.getString("libelle"),
						result.getInt("nbrPages"),
						result.getInt("idBandeDessinee"));
				return bd;
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