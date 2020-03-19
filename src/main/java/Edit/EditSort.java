package Edit;

import Revise.RevisionMap;
import com.google.gson.JsonArray;

import java.time.Instant;
import java.util.ArrayList;

import java.util.Map;

public class EditSort {

    public StringBuilder sortEdits(JsonArray revisionsArray){
        RevisionMap revisionMapMaker = new RevisionMap();
        EditMap editorMapMaker = new EditMap();
        Map<String,Instant> revisionMap = revisionMapMaker.makeRevisionMap(revisionsArray);
        Map<String,Integer> editorMap = editorMapMaker.makeEditorMap(revisionsArray);

        ArrayList<Editor> editors = new ArrayList<>();

        for (Map.Entry<String, Integer> element : editorMap.entrySet()) {
            String username = element.getKey();
            int revisions = element.getValue();
            Instant timestamp = revisionMap.get(element.getKey());
            editors.add(new Editor(username, revisions, timestamp));
        }

        editors.sort(new sortByEdits());

        StringBuilder result = new StringBuilder("##############" + editors.size() + " editors recognized##############\n");

        for(Editor editor : editors){
            String username = editor.username;
            Instant timestamp = editor.timestamp;
            int revisions = editor.revisions;
            result.append("Username: ").append(username).append(" | Timestamp: ").append(timestamp).append(" | Edits: ").append(revisions).append("\n");
        }
        return result;
    }
}
