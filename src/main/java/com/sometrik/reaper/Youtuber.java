package com.sometrik.reaper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Lists;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.samples.youtube.cmdline.data.Quickstart;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.CommentThreadReplies;

public class Youtuber {
  
  private YouTube youtube;
  
  public Youtuber() {
    try {
      ArrayList<String> scopes = Lists.newArrayList();
      scopes.add("https://www.googleapis.com/auth/youtube");
      scopes.add("https://www.googleapis.com/auth/youtube.force-ssl");
      Credential credential = Auth.authorize(scopes, "commentthreads");
      youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).build();

      getComments("64r9cYzn8SQ");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void getComments(String videoId) {

    Quickstart start = new Quickstart();
    try {
      CommentThreadListResponse commentsPage = prepareListRequest(videoId).execute();
      handleCommentsThreads(commentsPage.getItems());
      
//      while (true) {
//        handleCommentsThreads(commentsPage.getItems());
//
//        String nextPageToken = commentsPage.getNextPageToken();
//        if (nextPageToken == null)
//            break;
//
//        // Get next page of video comments threads
//        commentsPage = prepareListRequest(videoId).setPageToken(nextPageToken).execute();
//    }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private YouTube.CommentThreads.List prepareListRequest(String videoId) throws Exception {

    return youtube.commentThreads().list("snippet,replies").setVideoId(videoId).setMaxResults(100L).setModerationStatus("published").setTextFormat("plainText");
  }
  
  private void handleCommentsThreads(List<CommentThread> commentThreads) {

    for (CommentThread commentThread : commentThreads) {
        List<Comment> comments = Lists.newArrayList();
        comments.add(commentThread.getSnippet().getTopLevelComment());

        CommentThreadReplies replies = commentThread.getReplies();
        if (replies != null)
            comments.addAll(replies.getComments());

        System.out.println("Found " + comments.size() + " comments.");

        for (Comment comment : comments) {
          System.out.println("comment: " + comment);
        }
        // Do your comments logic here
//        counter += comments.size();
    }
}
  
  
}
