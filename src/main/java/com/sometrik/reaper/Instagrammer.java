package com.sometrik.reaper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.domain.Comment;
import me.postaddict.instagram.scraper.domain.Media;
import okhttp3.OkHttpClient;

public class Instagrammer {
  
  private static final int MAX_MEDIA_SCRAPE = 10;
  
  public Instagrammer() {
    

//	for (Comment comment : comments) {
//	  System.out.println("comment - " + comment.user + ": " + comment.text);
//	}
  }
  
  private List<Comment> getCommentsFromUsername(String username) {
    
    Instagram gram = new Instagram(new OkHttpClient());

    ArrayList<Comment> fullCommentList = new ArrayList<Comment>();

    try {
      List<Media> medias = gram.getMedias(username, MAX_MEDIA_SCRAPE);
      
      for (Media media : medias) {
	List<Comment> comments = gram.getCommentsByMediaCode(media.shortcode, 10);

	for (Comment comment : comments) {
	  fullCommentList.add(comment);
	}
      }
      
      return fullCommentList;
      
    } catch (IOException e) {
      System.out.println("IOException while getting medias from " + username + ". Shutting down");
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

}
