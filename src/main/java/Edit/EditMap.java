package Edit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.*;

public class EditMap {
    public Map<String,Integer> makeEditorMap(JsonArray revisionsArray){

        Map<String, Integer> User = new LinkedHashMap<>();

        for (int i = 0; i < revisionsArray.size(); i++) {


            JsonObject revision = revisionsArray.get(i).getAsJsonObject();
            String username = revision.get("user").getAsString();

            if(User.containsKey(username)){
                Integer count = User.get(username) + 1;
                User.put(username,count);
            }else{
                User.put(username,1);
            }
        }
        return User;
    }
}
