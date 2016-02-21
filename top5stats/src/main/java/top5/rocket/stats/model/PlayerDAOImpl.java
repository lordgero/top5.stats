package top5.rocket.stats.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
	public List<Player> listPlayer() {
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
	public Player get(String idSteam){
		//todo
		return null;
	}

	@Override
	public void fetchListLogs(String idSteam) throws JSONException, IOException {
		Player jouzineur = new Player();
		jouzineur.setIdSteam(idSteam);
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+idSteam).getJSONArray("logs");
		ArrayList<Integer> listeLogs = new ArrayList<Integer>();
		ArrayList<Integer> dateLogs = new ArrayList<Integer>();
		//int doublons = 0;
		for (int i = 0; i < rawLogs.length(); i++) {
			int idLog = (Integer) rawLogs.getJSONObject(i).get("id");
			int dateLog = (Integer) rawLogs.getJSONObject(i).get("date");
			if(!(dateLogs.contains(dateLog))){
				listeLogs.add(idLog);
			}
			dateLogs.add(dateLog);
		}
		jouzineur.setLogsJoueur(listeLogs);
	}

	@Override
	public void fetchPlayerStatsFromLogsTF(String idSteam) throws JSONException, IOException {
		Player j = get(idSteam);
		for(int id : j.getLogsJoueur()){
			JSONObject log = readJsonFromUrl("http://logs.tf/json/"+id).getJSONObject("players");
			try{
				JSONObject statsJoueur = log.getJSONObject(idSteam);
				j.setNbMatchs(j.getNbMatchs()+1);
				j.setNbFrags(j.getNbFrags()+ (Integer) statsJoueur.get("kills"));
				j.setNbAirshoutes(j.getNbAirshoutes()+ (Integer) statsJoueur.get("as"));
				j.setNbDMG(j.getNbDMG()+ (Integer) statsJoueur.get("dmg"));
				JSONArray classStats = statsJoueur.getJSONArray("class_stats");
				for (int i = 0; i < classStats.length(); i++) {
					String classe = classStats.getJSONObject(i).get("type").toString();
					j.getNombreClassesPrises().put(classe, j.getNombreClassesPrises().get(classe)+1);
					j.setClassesTotalesPrises(j.getClassesTotalesPrises()+1);
				}
				j.setMoyenneFrags(j.getNbFrags()/j.getNbMatchs());
				j.setMoyenneAirshoutes(j.getNbAirshoutes()/j.getNbMatchs());
				j.setMoyenneDMG(j.getNbDMG()/j.getNbMatchs());
				j.setMoyenneOffclass(j.getClassesTotalesPrises()/j.getNbMatchs());
				for(String c : j.getClasses()){
					j.getMoyenneClassesPrises().put(c, (Math.round((((double) j.getNombreClassesPrises().get(c)/((double) j.getClassesTotalesPrises()))*100)*10d)/10d));
				}
			}
			catch(Exception e){
				/* les vieux logs ont l'ancien format steam, du coup on ne retrouve pas les informations bonus 
				ainsi que le jouzineur, ce qui leve une exception */
				System.out.println("logs vieux format // ignoré");
				break;
			}
		}
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
