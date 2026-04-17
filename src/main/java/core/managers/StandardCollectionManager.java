package core.managers;

import core.models.StudyGroup;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class StandardCollectionManager {
    private LocalDateTime initTime;
    private int currentId = 1;
    private Map<Integer, StudyGroup> studyGroups = new HashMap<>();
    private TreeSet<StudyGroup> collection = new TreeSet<StudyGroup>();
    private final StandardDumpManager dumpManager;

    public StandardCollectionManager(StandardDumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }

    public TreeSet<StudyGroup> getCollection() {
        return collection;
    }

    public StudyGroup byId(int id) { return studyGroups.get(id); }

    public boolean isContain(StudyGroup e) { return e == null || byId(e.getId()) != null; }

    public int getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }

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
        System.out.println(studyGroups);
        System.out.println(collection);
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
            };
        }
        return true;
    }

    public void saveCollection(String input) {
        dumpManager.saveCollection(collection, input);
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

