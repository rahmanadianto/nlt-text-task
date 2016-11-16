/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimpossitorus;

import java.sql.*;
import java.util.List;
import twitter4j.*;
import twitter4j.api.HelpResources.Language;
import twitter4j.conf.ConfigurationBuilder;



/**
 *
 * @author B2-12
 */
public class TwitterSearch {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DB_URL = "jdbc:mysql://localhost/twitter";

    //  Database credentials
    static final String USER = "root";
    static final String PASSWORD = "";
    
    Connection conn;
    PreparedStatement statement;
    
    public TwitterSearch() {
	//Empty Constructor
    }
   
    
    
    public void searchTweet(String _query, String category) {
	setupDatabase();
	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true)
	  .setOAuthConsumerKey("8eds8GX3A09WCquxXL1VDKiAo")
	  .setOAuthConsumerSecret("XRW1Uvsj5rR5j7Wmz9YHWOgrPtFNB25dl053enmSmZGA4b2LBo")
	  .setOAuthAccessToken("324787944-bDUy51vP0kow2H7QdkbDsA3UmtRUmJIKqKyY9Dlw")
	  .setOAuthAccessTokenSecret("oy5Lj3hdkuGAZ5dlh5EvFMrNUuwAQGg2hSGkROMLEzmrR");
	TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitter = tf.getInstance();
        try {
	    Query query = new Query(_query);
	    query.setLang("id");
	    query.setCount(1000);
            QueryResult result;
	    int i = 0;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
		saveTweets(tweets, category);
                for (Status tweet : tweets) {
		    i++;
                    System.out.println(i+". "+tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
	} catch (TwitterException te) {
		te.printStackTrace();
		System.out.println("Failed to search tweets: " + te.getMessage());
	}
	finally {
	    try{
		if(conn!=null)
		conn.close();
	    }catch(SQLException se){
		se.printStackTrace();
	    }//end finally try
	}
    }
    
    public void setupDatabase() {
	try{
	  //STEP 2: Register JDBC driver
	  Class.forName("com.mysql.jdbc.Driver");
	  //STEP 3: Open a connection
	  //System.out.println("Connecting to a selected database...");
	  conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
	  //System.out.println("Connected database successfully...");
	}catch(SQLException se){
	  //Handle errors for JDBC
	  se.printStackTrace();
	}catch(Exception e){
	  //Handle errors for Class.forName
	  e.printStackTrace();
	}//end try
	//System.out.println("Goodbye!");
    }
    
    public void saveTweets(List<Status> tweets, String category) {
	for (Status tweet : tweets) {
	    try {
		statement = conn.prepareStatement("INSERT INTO tweets(tweet, category) VALUES (?,?)");
		statement.setString(1,tweet.getText());
		statement.setString(2,category);
		statement.executeUpdate();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
}
