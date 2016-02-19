package top5.rocket.stats.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import top5.rocket.stats.logs.LogTF;

/**
 * Classe définissant un jouzineur
 * @author borey
 * Un joueur possède les attributs suivants :
 * 	- son id steam;
 * 	- son pseudo;
 * 	- le nombre total de maps jouées
 *  - La liste des logs de ce joueur (sous la forme d'un tableau associatif) (id:logTF)
 *	- La liste des pourcentages de jouzinage par classe (tableau associatif) (nomClasse : pourcentage)
 *	- 
 */
public class Player {

	private String idSteam;
	private String pseudo;
	// On retrouve un log avec son ID
	private ArrayList<Integer> logsJoueur;
	private HashMap<String, Integer> nombreClassesPrises;
	private HashMap<String, Double> moyenneClassesPrises;
	private int nbMatchs;
	private int nbFrags;
	private double moyenneFrags;
	private double moyenneDMG;
	private double moyenneAirshoutes;
	private int nbDMG;
	private int nbAirshoutes;
	private int classesTotalesPrises;
	
	
	
	public Player(String p, String id) throws JSONException, IOException{
		this.pseudo = p;
		this.idSteam = id;
		this.classesTotalesPrises = 0;
		moyenneClassesPrises = new HashMap<String, Double>();
		moyenneClassesPrises.put("scout", 0.0);
		moyenneClassesPrises.put("soldier", 0.0);
		moyenneClassesPrises.put("demoman", 0.0);
		moyenneClassesPrises.put("medic", 0.0);
		moyenneClassesPrises.put("spy", 0.0);
		moyenneClassesPrises.put("sniper", 0.0);
		moyenneClassesPrises.put("pyro", 0.0);
		moyenneClassesPrises.put("heavy", 0.0);
		moyenneClassesPrises.put("engineer", 0.0);
		
		nombreClassesPrises = new HashMap<String, Integer>();
		nombreClassesPrises.put("scout", 0);
		nombreClassesPrises.put("soldier", 0);
		nombreClassesPrises.put("demoman", 0);
		nombreClassesPrises.put("medic", 0);
		nombreClassesPrises.put("spy", 0);
		nombreClassesPrises.put("sniper", 0);
		nombreClassesPrises.put("pyro", 0);
		nombreClassesPrises.put("heavy", 0);
		nombreClassesPrises.put("engineer", 0);
		
		
		JSONArray rawLogs = readJsonFromUrl("http://logs.tf/json_search?player="+id).getJSONArray("logs");
		this.logsJoueur = genLogs(rawLogs);
		nbMatchs = nbFrags = 0;
		moyenneFrags = 0;
	}
	
	public String getIdSteam() {
		return idSteam;
	}
	public void setIdSteam(String idSteam) {
		this.idSteam = idSteam;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public ArrayList<Integer> getLogsJoueur() {
		return logsJoueur;
	}
	public void setLogsJoueur(ArrayList<Integer> logsJoueur) {
		this.logsJoueur = logsJoueur;
	}

	public int getNbMatchs() {
		return nbMatchs;
	}
	public void setNbMatchs(int nbMatchs) {
		this.nbMatchs = nbMatchs;
	}


	public void initStats() throws JSONException, IOException{
		//for(int id : logsJoueur){
		for(int i = 0; i < 20; i++){
			int id = logsJoueur.get(i);
			JSONObject log = readJsonFromUrl("http://logs.tf/json/"+id).getJSONObject("players");
			JSONObject statsJoueur = log.getJSONObject(idSteam);
			this.nbMatchs++;
			this.nbFrags += (Integer) statsJoueur.get("kills");
			this.nbAirshoutes += (Integer) statsJoueur.get("as");
			this.nbDMG += (Integer) statsJoueur.get("dmg");
			classLog(statsJoueur.getJSONArray("class_stats"));
		}
		this.moyenneFrags = nbFrags/nbMatchs;
		this.moyenneAirshoutes = nbAirshoutes/nbMatchs;
		this.moyenneDMG = nbDMG/nbMatchs;
		moyenneClassLog();
	}
	
	private void classLog(JSONArray classStats){
		for (int i = 0; i < classStats.length(); i++) {
			String classe = classStats.getJSONObject(i).get("type").toString();
			this.nombreClassesPrises.put(classe, nombreClassesPrises.get(classe)+1);
			this.classesTotalesPrises++;
		}
	}
	
	private void moyenneClassLog(){
		//todo
	}
	
	
	public static ArrayList<Integer> genLogs(JSONArray rawLogs){
		ArrayList<Integer> listeLogs = new ArrayList<Integer>();
		ArrayList<Integer> dateLogs = new ArrayList<Integer>();
		int doublons = 0;
		for (int i = 0; i < rawLogs.length(); i++) {
			int idLog = (Integer) rawLogs.getJSONObject(i).get("id");
			int dateLog = (Integer) rawLogs.getJSONObject(i).get("date");
			if(!(dateLogs.contains(dateLog))){
				listeLogs.add(idLog);
			}
			else{
				doublons++;
			}
			dateLogs.add(dateLog);
		}
		System.out.println("genLogs : il y a "+doublons+" doublons");
		return listeLogs;
	}
	
	@Override
	public String toString() {
		return "Player [idSteam=" + idSteam + ", pseudo=" + pseudo + ", nombreClassesPrises=" + nombreClassesPrises
				+ ", moyenneClassesPrises=" + moyenneClassesPrises + ", nbMatchs=" + nbMatchs + ", nbFrags=" + nbFrags
				+ ", moyenneFrags=" + moyenneFrags + ", moyenneDMG=" + moyenneDMG + ", moyenneAirshoutes="
				+ moyenneAirshoutes + ", nbDMG=" + nbDMG + ", nbAirshoutes=" + nbAirshoutes + ", classesTotalesPrises="
				+ classesTotalesPrises + "]";
	}

	public static JSONObject readJsonFromUrl(String url) throws JSONException, IOException{
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
	
	
	
	public static String readAll(Reader rd) throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read())!=-1){
			sb.append((char)cp);
		}
		return sb.toString();
	}
}
