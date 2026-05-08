package core.managers.standard;

import core.models.standard.StudyGroup;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager implements core.managers.CollectionManager {
    private LocalDateTime initTime;
    private int currentId = 0;
    private final Map<Integer, StudyGroup> studyGroups = new HashMap<>();
    private TreeSet<StudyGroup> collection = new TreeSet<StudyGroup>();
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }

    @Override
    public TreeSet<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public StudyGroup byId(int id) { return studyGroups.get(id); }

    @Override
    public boolean isContain(StudyGroup e) { return e == null || byId(e.getId()) != null; }

    @Override
    public int getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }

    @Override
    public void clearCollection() {
        collection.clear();
    }

    public long getSumStudsCount() {
        long sum = 0;
        for (var studyGroup : collection) {
            sum += studyGroup.getStudentsCount();
        }
        return sum;
    }

    public StudyGroup getMaxAvgMark() {
        return collection.stream().max(StudyGroup::compareTo).get();
    }

    public StudyGroup getMinAvgMark() {
        return collection.stream().min(StudyGroup::compareTo).get();
    }

    @Override
    public boolean add(StudyGroup a) {
        if (isContain(a)) return false;
        studyGroups.put(a.getId(), a);
        collection.add(a);
        return true;
    }

    @Override
    public boolean update(StudyGroup a) {
        if (!isContain(a)) return false;
        collection.remove(byId(a.getId()));
        studyGroups.put(a.getId(), a);
        collection.add(a);
        return true;
    }

    @Override
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

    @Override
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

    @Override
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

