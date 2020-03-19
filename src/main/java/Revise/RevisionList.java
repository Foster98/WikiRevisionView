package Revise;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;

public class RevisionList {
    public String listRevisions(JsonArray revisions) {
        StringBuilder output = new StringBuilder(" ################" + revisions.size() + " Revisions ###########\n");
        for (int x = 0; x < revisions.size(); x++) {

            JsonObject revision;
            revision = revisions.get(x).getAsJsonObject();

            String username = revision.get("user").getAsString();


            Instant timestamp = Instant.parse(revision.get("timestamp").getAsString());

            output.append("Username: ").append(username).append(" ||  Timestamp: ").append(timestamp).append("\n");
        }
        return output.toString();
    }
}