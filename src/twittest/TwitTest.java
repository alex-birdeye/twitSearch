/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author youser
 */
public class TwitTest {

    static Twitter twitter;

    public TwitTest() {
        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("Cmv4TMj6CWPY9np9uzakmeAPM", "zDnw1ypudjVkJn0zK093TPgf0zXDcjKvkdHiHUVdviKqoFHcN3");

        AccessToken accessToken = null;
        accessToken = loadToken();
        if (accessToken == null) {
            accessToken = getNewToken();
        }

        twitter.setOAuthAccessToken(accessToken);
        System.out.println("token loaded");
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws TwitterException, IOException {
//        // The factory instance is re-useable and thread safe.
//        twitter = TwitterFactory.getSingleton();
//        twitter.setOAuthConsumer("Cmv4TMj6CWPY9np9uzakmeAPM", "zDnw1ypudjVkJn0zK093TPgf0zXDcjKvkdHiHUVdviKqoFHcN3");
//
//        AccessToken accessToken = null;
//        accessToken = loadToken();
//        if (accessToken == null) {
//            accessToken = getNewToken();
//        }
//
//        twitter.setOAuthAccessToken(accessToken);
//        search("#Крим");
//    }
    private static void saveAccesToken(AccessToken accessToken) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File tokenFile = new File("accessToken.act");
        System.out.println("saveAccessToken");
        try {
            if (!tokenFile.exists()) {
                tokenFile.createNewFile();
            }
            fos = new FileOutputStream(tokenFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(accessToken);
            System.out.println("AccesToken saved !!!");
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static AccessToken loadToken() {
        AccessToken accessToken = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        System.out.println("loadToken");
        try {
            fis = new FileInputStream("accessToken.act");
            ois = new ObjectInputStream(fis);
            accessToken = (AccessToken) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.err.println("loadToken: " + e.getMessage());
        }

        return accessToken;
    }

    private static AccessToken getNewToken() {
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        RequestToken requestToken;
        System.out.println("getNewToken");
        try {
            requestToken = twitter.getOAuthRequestToken();
            while (null == accessToken) {
                try {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
                    String pin = br.readLine();
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken();
                    }
                    saveAccesToken(accessToken);
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accessToken;
    }

    public static /*StringBuffer*/ List search(String searchStr, int resQuantity) {
//        StringBuffer sb = new StringBuffer();
        List usersAndStstuses = new ArrayList();
        Query query = new Query(searchStr);
        query.setCount(resQuantity);
        QueryResult result = null;
        User user = null;
        URL url = null;
        ImageIcon image = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("result.getTweets().len = " + result.getTweets().size());
        for (Status status : result.getTweets()) {
            try {
                Object[] res = {null, null, null};
                user = status.getUser();
                url = new URL(user.getProfileImageURL());
                image = new ImageIcon(url);
                res[0] = "@" + user.getScreenName();
                res[1] = status.getText();
//                res[2] = image;
                res[2] = user.getProfileImageURL();
                usersAndStstuses.add(res);
                System.out.println(res[0] + "   " + res[1]);
//                sb.append(res + "\n");
            } catch (MalformedURLException ex) {
                java.util.logging.Logger.getLogger(TwitTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        return sb;
        return usersAndStstuses;
    }

}
