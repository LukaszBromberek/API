package api.main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


/*
 * While writing this code, my IDE was disconected from Maven, so I was unable to even build this project
 * 
 * My bad, I should ensure that i have all connections established, before starting with test
 * 
 * All code if written blindly, without debugging, but the general concept should show the state of my knowledge
 * 
 * (exception : normally I would find & use some library for parsing json, but without maven and testing possibilites
 * i had to use simple string functions)
 * 
 */

@Path("api")
public class SearchForClubs {

	@GET
	@Path("/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClub ( @PathParam("input") String input){
		
		/*
		 * Build query for wiki opensearch
		 * https://www.mediawiki.org/wiki/API:Opensearch
		 */
		
		
		Client client = ClientBuilder.newClient();
		 WebTarget queryBuilder = client.target("https://en.wikipedia.org/w/api.php")
				 	.queryParam("action", "opensearch")
				 	.queryParam("search", input);
		
		 ResponseWrapperOpenSearchQuery response = queryBuilder
				 .request(MediaType.APPLICATION_JSON)
				 .get(ResponseWrapperOpenSearchQuery.class);
		
		 
		 
		 
		 
		 
		 /*
		  * When i have addresses of sites from wiki's opensearch i check categories from every of them
		  * using another query 
		  * https://www.mediawiki.org/wiki/API:Categories
		  */
		 
		 for (String adress : response.getAdresses()) {
			
			 //Parse last part of URL to use later in queries
			 int index = adress.lastIndexOf("/")+1;
			 
			 String queryParam = adress.substring(index);
			 
			 

			 
			 //Build query to search wiki API for categories of 
			 queryBuilder = client.target("https://en.wikipedia.org/w/api.php")
					 .queryParam("action", "query")
					 .queryParam("format", "json")
					 .queryParam("titles", queryParam)
					 .queryParam("prop", "categories");
			 
			 
			 // Get response into string
			String categoryResponse = queryBuilder.request(MediaType.TEXT_PLAIN).get(String.class);
			
			
			//Retrieve only categories data
			index = categoryResponse.indexOf("\"categories\"")+14;
			int lastIndex = categoryResponse.lastIndexOf("]");
			
			categoryResponse = categoryResponse.substring(index, lastIndex);
			
			String[] categories = categoryResponse.split("\\},\\{");
			
			
			
			boolean footballClubCategoryFound = false;
			
			for (String category : categories) {
				if (category.toLowerCase().contains("football")&category.toLowerCase().contains("club")) {
					footballClubCategoryFound = true;
				}
			}
			
			
			/*
			 * If checked website was the correct one
			 * Return it, and end function
			 */
			
			if(footballClubCategoryFound) {
				return adress;
			}
			 
		}
		
		 /*
		  * If club was not found, return null
		  * 
		  */
		 
		return null;
	}
	
}
