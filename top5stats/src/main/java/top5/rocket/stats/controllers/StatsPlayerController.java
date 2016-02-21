package top5.rocket.stats.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top5.rocket.stats.DAO.PlayerDAO;
import top5.rocket.stats.model.Player;

/**
 * Calcul des statistiques pour chaque joueur sur sa page perso
 * (exemple top5.tf/stats/gero)
 */
@Controller
@RequestMapping("/{joueur}")
public class StatsPlayerController {
	private HashMap<String, String> top5jouzineurs = new HashMap<String, String>();
	
	
//	@Autowired
//	PlayerDAO playerDao;
	
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
		Player jouzineur = new Player(pseudoJoueur, top5jouzineurs.get(pseudoJoueur));
		String stats = jouzineur.toString();
		System.out.println(stats);
		model.addAttribute("stats", stats);
		model.addAttribute("joueur", pseudoJoueur);
		model.addAttribute("nombreMatchs", jouzineur.getNbMatchs());
		model.addAttribute("nombreAirshoutes", jouzineur.getNbAirshoutes());
		model.addAttribute("nombreFrags", jouzineur.getNbFrags());
		model.addAttribute("nombreDommages", jouzineur.getNbDMG()/10000);
		model.addAttribute("nombreAirshoutesMoyen", jouzineur.getMoyenneAirshoutes());
		model.addAttribute("nombreFragsMoyen", jouzineur.getMoyenneFrags());
		model.addAttribute("nombreDommagesMoyen", jouzineur.getMoyenneDMG());
		//playerDao.fetchPlayerStatsFromLogsTF(jouzineur.getIdSteam());
		for(String c : jouzineur.classes){
			model.addAttribute(c, jouzineur.getMoyenneClassesPrises().get(c));
		}
		
		return "joueur";
	}
	

	
}
