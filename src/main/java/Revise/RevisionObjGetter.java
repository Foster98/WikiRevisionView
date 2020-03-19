package Revise;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RevisionObjGetter {
    public JsonArray getRevisionsObject(JsonObject query){
        JsonObject pages = query.getAsJsonObject("pages");
        JsonObject pageIdNumberObject = pages.entrySet().iterator().next().getValue().getAsJsonObject();
        return pageIdNumberObject.getAsJsonArray("revisions");
    }
}



