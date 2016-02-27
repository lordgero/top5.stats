package top5.rocket.stats.DAO;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import top5.rocket.stats.model.Player;

@Repository
public class PlayerDAOImpl implements PlayerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	// Temporaire
	private ArrayList<Player> joueurs;
	
	/**
	 * Constructeur vide de chie
	 */
	public PlayerDAOImpl(){
		
	}
	/**
	 * Le constructeur qui nous va bien,
	 * SessionFactory qui nous permet de comm avec la BDD
	 * @param sf
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public PlayerDAOImpl(SessionFactory sf) throws JSONException, IOException{
		this.sessionFactory = sf;
		this.joueurs = new ArrayList<Player>();
		this.createPlayer("Gero", "[U:1:62587760]");
		this.createPlayer("Mooshi", "[U:1:55570032]");
		this.createPlayer("Ombrack", "[U:1:54738661]");
		this.createPlayer("Zelkhar", "[U:1:97408210]");
		this.createPlayer("Enjoyed", "[U:1:8458321]");
		this.createPlayer("Blitzo", "[U:1:42669671]");
		this.createPlayer("Lake", "[U:1:39353955]");
		this.createPlayer("Neo", "[U:1:69115311]");
		this.createPlayer("Kinty", "[U:1:2043271]");
		this.createPlayer("Shado", "[U:1:69781430]");
		this.createPlayer("Medico", "[U:1:84161969]");
		this.createPlayer("Fmapkmn", "[U:1:38560474]");
		this.createPlayer("Leyo", "[U:1:39051312]");
		this.createPlayer("Screw", "[U:1:66218918]");
		this.createPlayer("Opti", "[U:1:31892934]");
		this.createPlayer("Kaiizo", "[U:1:49056203]");
		this.createPlayer("OMG", "[U:1:55003116]");
		this.createPlayer("Glastry", "[U:1:47477593]");
		this.createPlayer("Corbac", "[U:1:83489966]");
		this.createPlayer("Pierrot", "[U:1:120180855]");
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
	@Transactional
	public Player get(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Player getFromPseudo(String pseudo){
		for(Player p : this.joueurs){
			if(p.getPseudo().equals(pseudo))
				return p;
		}
		return null;
	}
	
	@Override
	public void initStats(Player j) throws JSONException, IOException{
		if(j.getLogsJoueur() == null){
			this.fetchListLogs(j);
			this.fetchPlayerStatsFromLogsTF(j);
		}
	}
	
	@Override
	@Transactional
	public Player get(String idSteam){
		for(Player p : this.joueurs){
			if(p.getIdSteam().equals(idSteam))
				return p;
		}
		return null;
	}
	
	@Override
	public Player createPlayer(String pseudo, String idSteam) throws JSONException, IOException{
		if(this.get(idSteam) == null){
			Player jouzineur = new Player(pseudo, idSteam);
			this.joueurs.add(jouzineur);
			return jouzineur;
		}
		else{
			return (this.get(idSteam));
		}
	}

	@Override
	@Transactional
	public void fetchListLogs(Player jouzineur) throws JSONException, IOException {
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+jouzineur.getIdSteam()).getJSONArray("logs");
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
	@Transactional
	public void fetchPlayerStatsFromLogsTF(Player j) throws JSONException, IOException {
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+j.getIdSteam()).getJSONArray("logs");
		int dateLog = (Integer) rawLogs.getJSONObject(0).get("date");
		j.setLastUpdate(dateLog);
		for(int id : j.getLogsJoueur()){
			JSONObject log = readJsonFromUrl("http://logs.tf/json/"+id).getJSONObject("players");
			try{
				statistics(j, log);
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
	@Transactional
	public void updatePlayerFromLogsTF(Player j) throws JSONException, IOException {
		ArrayList<Integer> logs = j.getLogsJoueur();
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+j.getIdSteam()).getJSONArray("logs");
		// dateLog contient la date du dernier log
		int dateLog = (Integer) rawLogs.getJSONObject(0).get("date");
		int curDate = j.getLastUpdate();
		j.setLastUpdate(dateLog);
		
		JSONObject log;
		
		if(dateLog != curDate){
			log = readJsonFromUrl("http://logs.tf/json/"+j.getLogsJoueur().get(0)).getJSONObject("players");
			statistics(j, log);
		}
		
		int i = 1;
		while(dateLog > curDate && i < logs.size()){
			log = readJsonFromUrl("http://logs.tf/json/"+j.getLogsJoueur().get(i)).getJSONObject("players");
			try{
				statistics(j, log);
			}
			catch(Exception e){
				System.out.println("logs vieux format // ignoré");
				break;
			}
		}
	}
	
	@Override
	public void statistics(Player j, JSONObject log){
		JSONObject statsJoueur = log.getJSONObject(j.getIdSteam());
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
	


	@Override
	@Transactional
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
