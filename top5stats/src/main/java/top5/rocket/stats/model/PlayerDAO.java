package top5.rocket.stats.model;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

public interface PlayerDAO {
	
	/**
	 * 
	 * @return all player from the database maggle
	 */
	public List<Player> listPalyer();
	/**
	 * Find jouzineur from id
	 * @param id
	 * @return
	 */
	public Player get(int id);
	/**
	 * One time use function.
	 * Find the logs of a jouzineur from logs.tf
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public void fetchPlayerStatsFromLogsTF(int idSteam) throws JSONException, IOException;
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
