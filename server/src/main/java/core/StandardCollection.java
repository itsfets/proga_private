package core;

import dto.StudyGroup;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class StandardCollection {
    private final Map<Integer, StudyGroup> studyGroups = new HashMap<>();
    private final StandardDumper dumpManager;
    private LocalDateTime initTime;
    private int currentId = 0;
    private TreeSet<StudyGroup> collection = new TreeSet<StudyGroup>();

    public StandardCollection(StandardDumper dumpManager) {
        this.dumpManager = dumpManager;
    }

    public TreeSet<StudyGroup> getCollection() {
        return collection;
    }

    public StudyGroup byId(int id) {
        return studyGroups.get(id);
    }

    public boolean isContain(StudyGroup e) {
        return e == null || byId(e.getId()) != null;
    }

    public int getFreeId() {
        while (byId(++currentId) != null) ;
        return currentId;
    }

    public void clearCollection() {
        collection.clear();
    }

    public boolean add(StudyGroup a) {
        if (isContain(a)) return false;
        studyGroups.put(a.getId(), a);
        collection.add(a);
        return true;
    }

    public boolean update(StudyGroup a) {
        if (!isContain(a)) return false;
        collection.remove(byId(a.getId()));
        studyGroups.put(a.getId(), a);
        collection.add(a);
        return true;
    }

    public boolean remove(int id) {
        StudyGroup a = byId(id);
        if (a == null) return false;
        studyGroups.remove(id);
        collection.remove(a);
        return true;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public boolean init() {
        collection.clear();
        studyGroups.clear();
        initTime = LocalDateTime.now();
        collection = dumpManager.readCollection();
        for (var studyGroup : collection) {
            if (byId(studyGroup.getId()) == null) {
                if (studyGroup.getId() > currentId) currentId = studyGroup.getId();
                studyGroups.put(studyGroup.getId(), studyGroup);
            }
        }
        return true;
    }

    public void saveCollection() {
        dumpManager.saveCollection(collection);
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Empty collection!";
        StringBuilder info = new StringBuilder();
        for (var studyGroup : collection) {
            info.append(studyGroup).append("\n");
        }
        return info.toString().trim();
    }
}

