package top5.rocket.stats.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.json.JSONException;

/**
 * Classe définissant un jouzineur
 * @author borey
 */
@Entity
@Table(name="PLAYER")
public class Player {

	/**
	 * Les champs communs avec la BDD
	 */
	private int id;
	private String idSteam;
	private String pseudo;
	private int nbMatchs;
	private int nbFrags;
	private double moyenneFrags;
	private double moyenneDMG;
	private double moyenneAirshoutes;
	
	//stats util pour ici
	private int nbDMG;
	private int nbAirshoutes;
	private int classesTotalesPrises;
	private double moyenneOffclass;
	private int drops;
	private int nbMedics;
	private int moyenneDrops;
	
	public String classes[] = {"scout", "soldier", "demoman", "medic", "spy", "sniper", "pyro", 
							   "heavyweapons", "engineer"};
	
	// On retrouve un log avec son ID
	private ArrayList<Integer> logsJoueur;
	private HashMap<String, Integer> nombreClassesPrises;
	private HashMap<String, Double> moyenneClassesPrises;
	
	public Player(String p, String id) throws JSONException, IOException{
		this.pseudo = p;
		this.idSteam = id;
		this.classesTotalesPrises = 0;
		
		moyenneClassesPrises = new HashMap<String, Double>();
		nombreClassesPrises = new HashMap<String, Integer>();

		for(String c : classes){
			moyenneClassesPrises.put(c, 0.0);
			nombreClassesPrises.put(c, 0);
		}
		nbMatchs = nbFrags = nbAirshoutes = nbDMG = nbMedics = 0;
		moyenneFrags = moyenneAirshoutes = moyenneDMG = moyenneOffclass = 0;
	}
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue
	@Column(name = "\"ID\"", columnDefinition = "serial")
	public int getId(){
		return id;
	}
	@Id
	@Column(name = "\"ID\"", columnDefinition = "serial")
	public void setId(int id){
		this.id = id;
	}
	@Column(name = "\"IDSTEAM\"")
	public String getIdSteam() {
		return idSteam;
	}
	public void setIdSteam(String idSteam) {
		this.idSteam = idSteam;
	}
	@Column(name = "\"PSEUDO\"")
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

	@Column(name = "\"NBMATCHS\"")
	public int getNbMatchs() {
		return nbMatchs;
	}
	public void setNbMatchs(int nbMatchs) {
		this.nbMatchs = nbMatchs;
	}

	public HashMap<String, Integer> getNombreClassesPrises() {
		return nombreClassesPrises;
	}

	public void setNombreClassesPrises(HashMap<String, Integer> nombreClassesPrises) {
		this.nombreClassesPrises = nombreClassesPrises;
	}

	public HashMap<String, Double> getMoyenneClassesPrises() {
		return moyenneClassesPrises;
	}

	public void setMoyenneClassesPrises(HashMap<String, Double> moyenneClassesPrises) {
		this.moyenneClassesPrises = moyenneClassesPrises;
	}
	@Column(name = "\"NBFRAGS\"")
	public int getNbFrags() {
		return nbFrags;
	}

	public void setNbFrags(int nbFrags) {
		this.nbFrags = nbFrags;
	}
	@Column(name = "\"MOYFRAGS\"")
	public double getMoyenneFrags() {
		return moyenneFrags;
	}

	public void setMoyenneFrags(double moyenneFrags) {
		this.moyenneFrags = moyenneFrags;
	}
	@Column(name = "\"MOYDMG\"")
	public double getMoyenneDMG() {
		return moyenneDMG;
	}

	public void setMoyenneDMG(double moyenneDMG) {
		this.moyenneDMG = moyenneDMG;
	}
	@Column(name = "\"MOYAIR\"")
	public double getMoyenneAirshoutes() {
		return moyenneAirshoutes;
	}

	public void setMoyenneAirshoutes(double moyenneAirshoutes) {
		this.moyenneAirshoutes = moyenneAirshoutes;
	}

	public int getNbDMG() {
		return nbDMG;
	}

	public void setNbDMG(int nbDMG) {
		this.nbDMG = nbDMG;
	}

	public int getNbAirshoutes() {
		return nbAirshoutes;
	}

	public void setNbAirshoutes(int nbAirshoutes) {
		this.nbAirshoutes = nbAirshoutes;
	}

	public int getClassesTotalesPrises() {
		return classesTotalesPrises;
	}

	public void setClassesTotalesPrises(int classesTotalesPrises) {
		this.classesTotalesPrises = classesTotalesPrises;
	}

	public double getMoyenneOffclass() {
		return moyenneOffclass;
	}

	public void setMoyenneOffclass(double moyenneOffclass) {
		this.moyenneOffclass = moyenneOffclass;
	}

	public int getDrops() {
		return drops;
	}

	public void setDrops(int drops) {
		this.drops = drops;
	}

	public int getNbMedics() {
		return nbMedics;
	}

	public void setNbMedics(int nbMedics) {
		this.nbMedics = nbMedics;
	}

	public int getMoyenneDrops() {
		return moyenneDrops;
	}

	public void setMoyenneDrops(int moyenneDrops) {
		this.moyenneDrops = moyenneDrops;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	
	@Override
	public String toString() {
		return "Player [idSteam=" + idSteam + ", pseudo=" + pseudo + ", nombreClassesPrises=" + nombreClassesPrises
				+ ", moyenneClassesPrises=" + moyenneClassesPrises + ", nbMatchs=" + nbMatchs + ", nbFrags=" + nbFrags
				+ ", moyenneFrags=" + moyenneFrags + ", moyenneDMG=" + moyenneDMG + ", moyenneAirshoutes="
				+ moyenneAirshoutes + ", nbDMG=" + nbDMG + ", nbAirshoutes=" + nbAirshoutes + ", classesTotalesPrises="
				+ classesTotalesPrises + "]";
	}
}
