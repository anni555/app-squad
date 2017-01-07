package com.appmonitoring.duster;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testMustache(Model model) {
		model.addAttribute("title", "Sample title");
		model.addAttribute("body", "boht tight");
		return "NewFile";
	}
	
	@RequestMapping(value = "/elastic", method = RequestMethod.GET)
	public String testElastic(Locale locale, Model model) throws UnknownHostException {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		InetAddress adr = InetAddress.getByAddress(new byte[]{(byte)10, (byte)237, (byte)53, (byte)67});
		
		Client client = TransportClient.builder().build()
				   .addTransportAddress(new InetSocketTransportAddress(adr, 9300));
		
		/*Spark.get("/", (request, response) -> {
	        SearchResponse searchResponse = 
	            client.prepareSearch("music").setTypes("lyrics").execute().actionGet();
	        SearchHit[] hits = searchResponse.getHits().getHits();
	 
	            Map<String, Object> attributes = new HashMap<>();
	            attributes.put("songs", hits);
	 
	            return new ModelAndView(attributes, "index.mustache");
	        }, new MustacheTemplateEngine());*/
		
		SearchResponse searchResponse = client.prepareSearch("cdb").setTypes("storedata").execute().actionGet();

		SearchHit[] hits = searchResponse.getHits().getHits();
		
		
		for (  SearchHit sd : hits) {
			sd.getSource().keySet();
		  }
		
		model.addAttribute("searchResult", hits);
		model.addAttribute("completeResult", searchResponse);
				
		return "elastic";
	}

}
