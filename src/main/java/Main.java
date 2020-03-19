import Edit.EditSort;
import Parse.parser;
import Revise.RevisionList;
import Revise.RevisionObjGetter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import Domain.*;

import java.io.InputStream;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("What article do you need");
        String word = input.nextLine();


        WikiPage page = new WikiPage();
        InputStream stream = page.wikiConnectionBuilder(word);

        if(stream == null){
            System.out.println("no connection");
        }


        parser parser = new parser();
        JsonObject query = parser.parseData(stream);

        if(query == null){
            System.out.println("Page does not exist.");

        }else{

            RevisionObjGetter getter = new RevisionObjGetter();
            JsonArray revisions = getter.getRevisionsObject(query);

            // Checking if redirect
            checkRedirect checker = new checkRedirect();
            String redirect = checker.checkForRedirects(query);
            if(redirect != null){
                System.out.println("Redirected from "+word+" to " + redirect);
            }

            RevisionList revisionLister = new RevisionList();
            String output = revisionLister.listRevisions(revisions);

            System.out.println(output);
            EditSort editorSort = new EditSort();
            String result = editorSort.sortEdits(revisions).toString();
            System.out.println(result);
        }
    }
}