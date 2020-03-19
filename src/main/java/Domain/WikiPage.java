package Domain;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WikiPage {

    public InputStream wikiConnectionBuilder(String term) throws Exception {
        ConnectionTest tester = new ConnectionTest();
        InputStream in;
        if(tester.TestConnection()){
            URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + term + "&rvprop=timestamp|user&rvlimit=30&redirects");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Revision Tracker/0.1 (http://www.cs.bsu.edu/; hgfoster@bsu.edu)");
            in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
        }else{
            in = null;
        }
        return in;
    }
}