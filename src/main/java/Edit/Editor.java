package Edit;

import java.time.*;
import java.util.*;

public class Editor {
    String username;
    Integer revisions;
    Instant timestamp;

    public Editor(String username, Integer revisions, Instant timestamp) {
        this.username = username;
        this.revisions = revisions;
        this.timestamp = timestamp;
    }
}

class sortByEdits implements Comparator<Editor> {
    public int compare(Editor a, Editor b) {
        return b.revisions - a.revisions;
    }

}
