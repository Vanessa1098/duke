package dukeApp.files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    protected String filePath;
    ArrayList<Task> arrList = new ArrayList<>();

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load data from file and store in an ArrayList
     * @return task list
     * @throws FileNotFoundException if the file does not exist
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        Task t;
        boolean isDone;

        while (s.hasNext()) {
            String c = s.next();
            if (c.equals("T")) {
                isDone = (s.nextInt())==1;
                t = new Todo(s.nextLine());
                arrList.add(t);
            }
            else if (c.equals("E")) {
                isDone = (s.nextInt())==1;
                t = new Event(s.nextLine());
                arrList.add(t);
            }
            else {
                isDone = (s.nextInt())==1;
                t = new Deadline(s.nextLine());
                arrList.add(t);
            }
            if (isDone) {
                t.markAsDone();
            }
        }

        return arrList;
    }

    /**
     * Write to file the new task list
     * @param newList new task list created
     * @throws IOException if there is an error with the inputs from newList or output when writing to file
     */
    public void appendToFile(ArrayList<Task> newList) throws IOException {
        StringBuilder s = new StringBuilder();
        FileWriter fw = new FileWriter(filePath);
        for (int i = 0; i < newList.size(); i++) {
            if ((newList.get(i).getType()).equals("T")) {
                s.append(newList.get(i).getType()).append(" ").append(newList.get(i).getDone())
                        .append(newList.get(i).getDescription());
            }
            else if ((newList.get(i).getType()).equals("E")) {
                s.append(newList.get(i).getType()).append(" ").append(newList.get(i).getDone())
                        .append(newList.get(i).toString());
            }
            else {
                ((Deadline)newList.get(i)).changeDate();
                s.append(newList.get(i).getType()).append(" ").append(newList.get(i).getDone())
                        .append(newList.get(i).toString());
            }
            if (i != newList.size()-1) {
                s.append(System.lineSeparator());
            }
        }
        fw.write(s.toString());
        fw.close();
    }
}
