package core;

import database.Manager;
import dto.StudyGroup;

import java.time.LocalDateTime;
import java.util.*;

public class StandardCollection {
    private final Map<Integer, StudyGroup> studyGroups = Collections.synchronizedMap(new HashMap<>());
    private LocalDateTime initTime;
    private final NavigableSet<StudyGroup> collection = Collections.synchronizedNavigableSet(new TreeSet<>());
    private final Manager dbManager;

    public StandardCollection(Manager dbManager) {
        this.dbManager = dbManager;
    }

    public NavigableSet<StudyGroup> getCollection() {
        return collection;
    }

    public StudyGroup byId(int id) {
        return studyGroups.get(id);
    }

    private void setCollection(NavigableSet<StudyGroup> dbCollection) {
        synchronized (studyGroups) {
            synchronized (collection) {
                studyGroups.clear();
                if (dbCollection == null) {
                    collection.clear();
                    return;
                }
                for (var studyGroup : dbCollection) {
                    studyGroups.put(studyGroup.getId(), studyGroup);
                }
                collection.clear();
                collection.addAll(dbCollection);
            }
        }
    }

    public boolean isContain(StudyGroup e) {
        return studyGroups.containsKey(e.getId());
    }

    public boolean add(StudyGroup a) {
        if (isContain(a)) return false;
        setCollection(dbManager.insert(a));
        return true;
    }

    public boolean update(StudyGroup a, String login) {
        StudyGroup stored = byId(a.getId());
        if (stored == null) return false;
        if (!login.equals(stored.getCreatedBy())) return false;
        setCollection(dbManager.update(a));
        return true;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public boolean remove(StudyGroup a, String login) {
        StudyGroup stored = byId(a.getId());
        if (stored == null) return false;
        if (!login.equals(stored.getCreatedBy())) return false;
        setCollection(dbManager.delete(a));
        return true;
    }

    public void init() {
        synchronized (studyGroups) {
            synchronized (collection) {
                studyGroups.clear();
                collection.clear();
                initTime = LocalDateTime.now();
                setCollection(dbManager.getCollection());
            }
        }
    }

    @Override
    public String toString() {
        synchronized (collection) {
            if (collection.isEmpty()) return "Empty collection!";
            StringBuilder info = new StringBuilder();
            for (var studyGroup : collection) {
                info.append(studyGroup).append("\n");
            }
            return info.toString().trim();
        }
    }

    public void clearCollection() {
        try {
            dbManager.clearCollection();
        } finally {
            synchronized (studyGroups) {
                synchronized (collection) {
                    studyGroups.clear();
                    collection.clear();
                }
            }
        }
    }
}

