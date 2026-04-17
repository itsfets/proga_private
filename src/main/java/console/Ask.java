package console;

import core.models.*;
import misc.Console;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class Ask {
    public static class AskBreak extends Exception {
        @Override
        public String getMessage() {
            return "Exiting without saving...";
        }
    }

    public static StudyGroup askStudyGroup(Console console, int id) throws AskBreak {
        try {
            String name;
            do {
                console.print("Name of the group:");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
            } while (name.isEmpty());
            var coordinates = askCoordinates(console);
            var studentCount = askStudentCount(console);
            var transferredStudents = askTransferredStudents(console);
            var averageMark = askAverageMark(console);
            var formOfEducation = askFormOfEducation(console);
            var groupAdmin = askGroupAdmin(console);
            return new StudyGroup(id, name, coordinates, studentCount, transferredStudents, averageMark, formOfEducation, groupAdmin);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.println("Error reading the file");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            int x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        if (x > -740) break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            int y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return null;
        }
    }

    public static long askStudentCount(Console console) throws AskBreak {
        try {
            long studentCount;
            while (true) {
                console.print("studentCount: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        studentCount = Long.parseLong(line);
                        if (studentCount > 0) break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return studentCount;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return 0;
        }
    }

    public static long askTransferredStudents(Console console) throws AskBreak {
        try {
            long transferredStudents;
            while (true) {
                console.print("transferredStudents: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        transferredStudents = Long.parseLong(line);
                        if (transferredStudents > 0) break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return transferredStudents;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return 0;
        }
    }

    public static double askAverageMark(Console console) throws AskBreak {
        try {
            double averageMark;
            while (true) {
                console.print("averageMark: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        averageMark = Long.parseLong(line);
                        if (averageMark > 0) break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return averageMark;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return 0;
        }
    }

    public static FormOfEducation askFormOfEducation(Console console) throws AskBreak {
        try {
            FormOfEducation formOfEducation;
            while (true) {
                console.print("formOfEducation (" + Arrays.toString(FormOfEducation.values()) + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        formOfEducation = FormOfEducation.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException ignored) {
                    }
                } else return null;
            }
            return formOfEducation;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return null;
        }
    }

    public static Person askGroupAdmin(Console console) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("groupAdmin.name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        name = line;
                        break;
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
            double height;
            while (true) {
                console.print("groupAdmin.height: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        height = Double.parseDouble(line);
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            Color eyeColor;
            while (true) {
                console.print("groupAdmin.eyeColor:(" + Arrays.toString(Color.values()) + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        eyeColor = Color.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException ignored) {
                    }
                }
            }
            try {
                float x;
                while (true) {
                    console.print("groupAdmin.location.x: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            x = Float.parseFloat(line);
                            break;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                double y;
                while (true) {
                    console.print("groupAdmin.location.y: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            y = Double.parseDouble(line);
                            break;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                long z;
                while (true) {
                    console.print("groupAdmin.location.z: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            z = Long.parseLong(line);
                            break;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                String locationName;
                while (true) {
                    console.print("groupAdmin.location.name: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            locationName = line;
                            break;
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                }
                return new Person(name, height, eyeColor, new Location(x, y, z, locationName));
            } catch (NoSuchElementException | IllegalStateException e) {
                console.printError("Error reading data provided");
                return null;
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Error reading data provided");
            return null;
        }
    }
}