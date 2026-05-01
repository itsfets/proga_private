package core.managers;

import core.models.standard.StudyGroup;

import java.util.TreeSet;

public interface DumpManagerTemplate {
    TreeSet<StudyGroup> readCollection();

    void saveCollection(TreeSet<StudyGroup> collection);
}
