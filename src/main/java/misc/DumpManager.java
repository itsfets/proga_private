package misc;

import core.models.StudyGroup;

import java.util.TreeSet;

public interface DumpManager {
    void saveCollection(TreeSet<StudyGroup> collection, String filename);
    TreeSet<StudyGroup> readCollection();
}
