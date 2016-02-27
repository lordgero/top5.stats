package top5.rocket.stats.DAO;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import top5.rocket.stats.model.Player;

public interface PlayerDAO {
	
	/**
	 * 
	 * @return all player from the database maggle
	 */
	public List<Player> listPlayer();
	/**
	 * Find jouzineur from id
	 * @param id
	 * @return
	 */
	public Player get(int id);
	
	/**
	 * Retrouver un jouzineur depuis l'id steam
	 * Pour l'instant on met les joueurs dans une arraylist de joueurs, avant de faire le travail sur la BD
	 * @param idSteam
	 * @return
	 */
	public Player get(String idSteam);
	
	/**
	 * Retrouver un jouzineur depuis son pseudonyme
	 * Pour l'instant on met les joueurs dans une arraylist de joueurs, avant de faire le travail sur la BD
	 * @param idSteam
	 * @return
	 */
	public Player getFromPseudo(String pseudo);
	
	/**
	 * Initialise les stats d'un joueur
	 * @param joueur
	 * @return
	 */
	public void initStats(Player joueur) throws JSONException, IOException;
	
	/**
	 * Ajoute un joueur dans la liste de joueurs
	 * Devra le faire sur la base de données par la suite.
	 * Appeller initStats() pour initialiser les statistiques.
	 */
	public Player createPlayer(String pseudo, String idSteam) throws JSONException, IOException;
	
	/**
	 * Obtenir la liste des logs d'un joueur 
	 * Elimine les doublons et modifie l'attribut LogsJoueur du jouzineur.
	 * @param jouzineur : le joueur
	 * @throws JSONException
	 * @throws IOException
	 * @throws la game avec l'eau du bébé
	 */
	
	public void fetchListLogs(Player jouzineur) throws JSONException, IOException;
	/**
	 * Récupere tous les logs depuis logs.tf et fait appel à statistics()
	 * Pour initialiser les stats d'un joueur, appeler "initStats".
	 * @param idSteam : le steam id sous la forme [X:X:XXXXX]
	 * @throws IOException 
	 * @throws JSONException 
	 */
	
	public void fetchPlayerStatsFromLogsTF(Player jouzineur) throws JSONException, IOException;
	/**
	 * Met à jour les statistiques d'un joueur suivant la date.
	 * @param p
	 * Il faudra voir comment gérer la maj de la bdd
	 */
	public void updatePlayerFromLogsTF(Player p) throws JSONException, IOException;
	
	/**
	 * Hop poubelle
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * Calcule les statistiques.
	 * Code moche, soz
	 * @param j
	 * @param log
	 */
	public void statistics(Player j, JSONObject log);
}
