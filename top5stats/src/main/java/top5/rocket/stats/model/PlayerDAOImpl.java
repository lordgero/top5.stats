package top5.rocket.stats.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public class PlayerDAOImpl implements PlayerDAO{

	private SessionFactory sessionFactory;
	
	/**
	 * Constructeur vide de chie
	 */
	public PlayerDAOImpl(){
		
	}
	/**
	 * Le constructeur qui nous va bien,
	 * SessionFactory qui nous permet de comm avec la BDD
	 * @param sf
	 */
	public PlayerDAOImpl(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional
	public List<Player> listPalyer() {
		@SuppressWarnings("unchecked")
		List<Player> players = (List<Player>) sessionFactory.getCurrentSession()
				.createCriteria(Player.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		return players;
	}

	@Override
	public Player get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fetchPlayerStatsFromLogsTF(int idSteam) throws JSONException, IOException {
		Player jouzineur = new Player();
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+idSteam).getJSONArray("logs");
		jouzineur.setNbMatchs(0);
		jouzineur.setNbFrags(0);
		jouzineur.setMoyenneFrags(0);
	}

	@Override
	public void updatePlayerFromLogsTF(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		Player jouzineurToREKT = new Player();
		jouzineurToREKT.setId(id);
		
	}
	
	private static JSONObject readJsonFromUrl(String url) throws JSONException, IOException{
		InputStream is = new URL(url).openStream();
		try{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonTxt = readAll(rd);
			JSONObject json = new JSONObject(jsonTxt);
			return json;
		}finally{
			is.close();
		}
	}
	
	private static String readAll(Reader rd) throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read())!=-1){
			sb.append((char)cp);
		}
		return sb.toString();
	}

}
