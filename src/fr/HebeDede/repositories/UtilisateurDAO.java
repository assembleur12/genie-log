package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.service.ConsoleService;

public class UtilisateurDAO extends DAO<Utilisateur> {

	public UtilisateurDAO() {
		super();
	}

	@Override
	public void create(Utilisateur obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM utilisateur");
			if (this.findByUsername(obj.getUsername()) != null) {
				System.out.println("L'utilisateur existe déjà !");
			} else {
				System.out.println("Création de l'utilisateur...");
				result.moveToInsertRow();
				result.updateString("username", obj.getUsername());
				result.updateString("password", obj.getPassword());
				result.updateString("role", obj.getRole());
				result.insertRow();
				result.beforeFirst();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	@Override
	public void delete(Utilisateur obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM utilisateur");
			
			while (result.next()) {
					int id = result.getInt("idUtilisateur");
					if (id == obj.getIdUtilisateur()) {
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
	public void update(Utilisateur obj) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeQuery("SELECT * FROM utilisateur");
			
				while (result.next()) {
					int id = result.getInt("idUtilisateur");
					if (id == obj.getIdUtilisateur()) {
						result.moveToCurrentRow();
						result.updateString("username", obj.getUsername());
						result.updateString("password", obj.getPassword());
						result.updateString("role", obj.getRole());
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

	public List<Utilisateur> findAll() {
		List<Utilisateur> userList = new ArrayList<Utilisateur>();
		Utilisateur user = new Utilisateur();
		
		Statement stmt = null;
		ResultSet result = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM utilisateur");
			while (result.next()) {
				user = new Utilisateur(result.getString("username"), result.getString("password"),
						result.getString("role"), result.getInt("idUtilisateur"));
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	@Override
	public Utilisateur find(Integer id) {
		Utilisateur user = new Utilisateur();
		
		Statement stmt = null;
		ResultSet result = null;
	
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = " + id);
			if(result.first()) {
				user = new Utilisateur(result.getString("username"), result.getString("password"),
						result.getString("role"), id);
				return user;
			} else {
				throw new UtilisateurInconnuException();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} catch (UtilisateurInconnuException e) {
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	public Utilisateur findByUsername(String username) {
		Utilisateur user = null;
		
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM utilisateur WHERE username = '" + username + "'");
			if(result.first()) {
				user = new Utilisateur(username, result.getString("password"), result.getString("role"),
						result.getInt("idUtilisateur"));
				return user;
			} else {
				throw new UtilisateurInconnuException();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} catch (UtilisateurInconnuException e) {
			return null;
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

}