package com.csc340.restapidemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yeonseo
 * 
 * Assignment2
 * Description of the application: to help make an English name
 */
@RestController
public class RestApiController {
    /**
    * Get a randomuser from randomuser and make it available at this endpoint.
    * 
    */
    @GetMapping("/name")
    public Object getName() {
        try {
            String url = "https://randomuser.me/api/?nat=us";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Print the whole response to console.
            System.out.println(root);

            //Parse out the most relevant piece of the information.
            JsonNode yourResult = root.get("results");

            String yourGender = yourResult.get(0).get("gender").asText();
            String yourName = yourResult.get(0).get("name").get("first").asText();
            
            System.out.println("If your gender is " + yourGender + ",");
            System.out.println("I recommend\n");
            System.out.println(yourName);
            

            return root;
            
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /name";
        }
    }

}
