package com.pawan.order.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonAuthenticationTokenFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(CommonAuthenticationTokenFilter.class);
	
    
    private String tokenHeader = "Authorization";
    
    @Autowired
	private RestTemplate restTemplate1;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        
    	String authToken = request.getHeader(this.tokenHeader);
    	System.out.println("Pawan security toke is :"+authToken);
    	if (authToken == null){
    		 authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXdhbjEwMEBnbWFpbC5jb20iLCJhdWRpZW5jZSI6InVua25vd24iLCJjcmVhdGVkIjoxNTEwNTc3ODAxODE0LCJleHAiOjE1MTA1ODA4MDF9.zqv7iJlC-RxbvIH4lFkBdgwHOHqH7g1fr1okuiFnC34AhpGIySHm6EAp6vVsmsUqnghsfg9oIhsbXMqVyc7tWA";
    	}
        if (!StringUtils.isEmpty(authToken) || SecurityContextHolder.getContext().getAuthentication() == null) {

        	try{
        		
        		HttpHeaders headers = new HttpHeaders();
        		headers.add("Authorization", authToken);
        		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        	
        		HttpEntity<String> entity = new HttpEntity<String>("", headers);
        		
        		ResponseEntity<String> responseEntity = 
        				restTemplate1.exchange(
        						"http://pawan-security-service/auth/current"
        						, HttpMethod.GET
        						, entity
        						, String.class);
            	
        		String jsonUserDetails = responseEntity.getBody();
        		System.out.println("The json details :"+jsonUserDetails);
                UserDetails userDetails = prepareUserDetails(jsonUserDetails);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                
        	}catch(Exception e){
        		System.out.println("The error  is "+e.getMessage());
        		logger.error(e.getMessage());
        	}
        	
        }

        chain.doFilter(request, response);
    }
    
    private UserDetails prepareUserDetails(String jsonUserDetails) throws JsonProcessingException, IOException{
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode root = objectMapper.readTree(jsonUserDetails);
    	
    	String userId = root.get("dbUser").get("id").asText();
    	String username = root.get("username").asText();
    	boolean isEnabled =  root.get("enabled").asBoolean();
    	
    	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    	
    	Iterator<JsonNode> authoritiesIterator = root.get("authorities").elements();
    	while(authoritiesIterator.hasNext()){
    		JsonNode authorityNode = authoritiesIterator.next();
    		authorities.add(new SimpleGrantedAuthority(authorityNode.get("authority").asText()));
    	}
    	
    	return new AuthUser(userId, username, authorities, isEnabled);
    }

	
}
