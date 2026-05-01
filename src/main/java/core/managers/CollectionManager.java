package core.managers;

import core.models.standard.StudyGroup;

import java.util.TreeSet;

public interface CollectionManager {
    TreeSet<StudyGroup> getCollection();

    StudyGroup byId(int id);

    boolean isContain(StudyGroup e);

    int getFreeId();

    void clearCollection();

    boolean add(StudyGroup a);

    boolean update(StudyGroup a);

    boolean remove(int id);

    boolean init();

    void saveCollection();
}
