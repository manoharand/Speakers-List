import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
	* GUI which displays all members in General Assembly and allows user to take attendance. 
	* @author Divya Manoharan 
	*/
public class SpeakersList extends JFrame implements ActionListener {

	/** All members of the assembly. */
  private ArrayList<Assembly> total = new ArrayList<Assembly>();
  /** Members who are present. */
  private ArrayList<Assembly> present = new ArrayList<Assembly>();
  /** The speaker's list. */
  private ArrayList<Assembly> speakersList = new ArrayList<Assembly>();
  private PrintStream o;

  public SpeakersList() {}

	/** 
		* Display all representatives of assembly as buttons which turn green upon being clicked to indicate that the representative is present. 
		* @param generalAssembly the total list of representatives 
		*/
  public void displayGA(ArrayList<Assembly> generalAssembly) {
    JPanel description = new JPanel();
    JPanel p = new JPanel(new GridLayout((int)Math.sqrt(generalAssembly.size()) + 1, (int)Math.sqrt(generalAssembly.size())));
    JPanel close = new JPanel();
    for (int i = 0; i < generalAssembly.size(); i++) {
      JButton temp = new JButton(generalAssembly.get(i).getName());
      temp.addActionListener(this);
      p.add(temp);
    }
    description.add(new JPanel().add(new JTextArea("Click on the members of GA who are present.", 0, 0)));
    description.setSize((int)(Math.sqrt(generalAssembly.size()) + 1) * 100, 50);
    description.setBackground(Color.white);
    JButton south = new JButton("Click once finished with attendance.");
    south.addActionListener(this);
    close.add(south);
    close.setSize((int)(Math.sqrt(generalAssembly.size()) + 1) * 100, 50);
    this.getContentPane().add(description, "North");
    this.add(p, "Center");
    this.add(close, "South");
    this.setSize((int)(Math.sqrt(generalAssembly.size()) + 1) * 100, ((int)Math.sqrt(generalAssembly.size()) * 100) + 50);
    this.setVisible(true);
  }

	/** 
		* Read data from an input file containing data about all representatives. 
		* @param inputFileName file with representative info 
		* @return an arrayList containing all data about representatives 
		*/
  public ArrayList<Assembly> readData(String inputFileName) {
    try {
			total = new ArrayList<Assembly>();
      BufferedReader br = new BufferedReader(new FileReader(inputFileName));
      String x;
      while ((x = br.readLine()) != null) {
        String[] temp = x.split(" ");
        total.add(new Assembly((temp[1] + ", " + temp[0]), Integer.parseInt(temp[2])));
      }
      Collections.sort(total, new Comparator<Assembly>() {
        @Override
        public int compare(Assembly a1, Assembly a2) {
          return a1.getName().compareTo(a2.getName());
        }
      });
    }
    catch (IOException e) {}
    return total;
  }

	/** 
		* Listen for the user marking representatives as present. 
		* @param e clicking a button  
		*/
  @Override
  public void actionPerformed(ActionEvent e) {
    for (Assembly x : total) {
      if (x.getName().equals(((JButton)e.getSource()).getText()) && ((JButton)e.getSource()).getBackground() != Color.GREEN) {
        ((JButton)e.getSource()).setBackground(Color.GREEN);
        present.add(x);
      }
      else if (x.getName().equals(((JButton)e.getSource()).getText()) && ((JButton)e.getSource()).getBackground() == Color.GREEN) {
      	for(int i = 0; i < present.size(); i++) {
      		if (present.get(i).getName().equals(((JButton)e.getSource()).getText()))
      			present.remove(i);
      	}
      	((JButton)e.getSource()).setBackground(null);
      }
    }
    if (((JButton)e.getSource()).getText().equals("Click once finished with attendance.")) {
      this.setVisible(false);
      this.createFile();
      this.writeToFile();
      this.dispose();
    }
  }

	/** 
		* Return an ArrayList of representatives who are present. 
		* @return ArrayList of present members 
		*/
  public ArrayList<Assembly> present() {
    return this.present;
  }

	/** 
		* Create a file (present.txt) containing a list of representatives who are present as well as the frequency at which they've spoken 
		*/
  public void createFile() {
    try {
      o = new PrintStream(new File("present.txt"));
    }
    catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException");
    }
  }

	/** 
		* Write data about present representatives to present.txt
		*/
  public void writeToFile() {
    System.setOut(o);
    for (Assembly x : present) {
      System.out.println(x.getName().split(" ")[1] + " " + x.getName().split(",")[0] + " " + x.getTalked());
    }
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
  }
  
	/** 
		* Run the program
		* @param args of the file containing data about all representatives
		*/
  public static void main(String[] args) {
    SpeakersList s = new SpeakersList();
    s.displayGA(s.readData(args[0]));
  }

}
