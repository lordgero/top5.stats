package top5.rocket.stats.model;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

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
	 * @param idSteam
	 * @return
	 */
	public Player get(String idSteam);
	
	/**
	 * Obtenir la liste des logs d'un joueur suivant son steam id.
	 * Elimine les doublons et modifie l'attribut LogsJoueur du jouzineur.
	 * @param idSteam : le steam id sous la forme [X:X:XXXXX]
	 * @throws JSONException
	 * @throws IOException
	 * @throws la game avec l'eau du bébé
	 */
	public void fetchListLogs(String idSteam) throws JSONException, IOException;
	/**
	 * One time use function.
	 * Initialise les statistiques d'un jouzineur en parcourant tous les logs jusqu'au plus récent.
	 * @param idSteam : le steam id sous la forme [X:X:XXXXX]
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public void fetchPlayerStatsFromLogsTF(String idSteam) throws JSONException, IOException;
	/**
	 * Update jouzineur stats from LogsTF
	 * @param p
	 * Il faudra voir comment gérer la maj de la bdd
	 */
	public void updatePlayerFromLogsTF(Player p);
	/**
	 * Hop poubelle
	 * @param id
	 */
	public void delete(int id);
}
