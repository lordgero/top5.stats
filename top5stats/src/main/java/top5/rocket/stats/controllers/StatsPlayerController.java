package top5.rocket.stats.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top5.rocket.stats.model.Player;

/**
 * Calcul des statistiques pour chaque joueur sur sa page perso
 * (exemple top5.tf/stats/gero)
 */
@Controller
@RequestMapping("/{joueur}")
public class StatsPlayerController {
	private HashMap<String, String> top5jouzineurs = new HashMap<String, String>();
	
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(StatsPlayerController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String stats(Locale locale, Model model, @PathVariable(value="joueur") final String pseudoJoueur) throws JSONException, IOException {
		top5jouzineurs.put("Gero", "[U:1:62587760]");
		top5jouzineurs.put("Mooshi", "[U:1:55570032]");
		top5jouzineurs.put("Ombrack", "[U:1:54738661]");
		//System.out.println(top5jouzineurs.get(pseudoJoueur));
		Player jouzineur = new Player(pseudoJoueur, top5jouzineurs.get(pseudoJoueur));
		jouzineur.initStats();
		String stats = jouzineur.toString();
		System.out.println(stats);
		/*
		JSONObject json = readJsonFromUrl("http://logs.tf/json_search?player=76561198043755694");
		System.out.println(json.toString());
		*/
		model.addAttribute("stats", stats);
		model.addAttribute("joueur", pseudoJoueur);
		model.addAttribute("nombreMatchs", jouzineur.getNbMatchs());
		model.addAttribute("nombreAirshoutes", jouzineur.getNbAirshoutes());
		model.addAttribute("nombreFrags", jouzineur.getNbFrags());
		model.addAttribute("nombreDommages", jouzineur.getNbDMG()/10000);
		model.addAttribute("nombreAirshoutesMoyen", jouzineur.getMoyenneAirshoutes());
		model.addAttribute("nombreFragsMoyen", jouzineur.getMoyenneFrags());
		model.addAttribute("nombreDommagesMoyen", jouzineur.getMoyenneDMG());
		
		for(String c : jouzineur.classes){
			model.addAttribute(c, jouzineur.getMoyenneClassesPrises().get(c));
		}
		
		return "joueur";
	}
	

	
}
