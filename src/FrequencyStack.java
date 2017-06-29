import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/** 
	* GUI to display all present representatives in an assembly and dynamically create a speaker's list based on the frequency at which individuals speak 
	* @author Divya Manoharan 
	*/
public class FrequencyStack extends SpeakersList implements ActionListener {

	/** The speaker's list. */
  private ArrayList<Assembly> speakersList = new ArrayList<Assembly>();
	/** All present representatives. */
  private ArrayList<Assembly> present = new ArrayList<Assembly>();
	/** All representatives. */
  private ArrayList<Assembly> tot = new ArrayList<Assembly>();
  private JPanel list;
  private JPanel p;
  private PrintStream o;

  public FrequencyStack() {
    super();
  }

	/** 
		* Display all present representatives as buttons which can be clicked to add members to the speaker's list 
		* @param generalAssembly the present members */
  @Override
  public void displayGA(ArrayList<Assembly> generalAssembly) {
    JPanel description = new JPanel();
    JPanel ad = new JPanel();
    p = new JPanel(new GridLayout((int)Math.sqrt(generalAssembly.size()) + 1, (int)Math.sqrt(generalAssembly.size())));
    for (int i = 0; i < generalAssembly.size(); i++) {
      JButton temp = new JButton(generalAssembly.get(i).getName());
      temp.setBackground(Color.white);
      temp.addActionListener(this);
      p.add(temp);
    }
    description.add(new JPanel().add(new JTextArea("Click on representatives you wish to add to the Speaker's List.", 0, 0)));
    description.setSize((int)(Math.sqrt(generalAssembly.size()) + 1) * 500, 50);
    JButton adjourn = new JButton("Adjourn");
    adjourn.addActionListener(this);
    ad.add(adjourn);
    ad.setSize((int)(Math.sqrt(generalAssembly.size()) + 1) * 500, 50);
    this.add(description, "North");
    this.add(p, "Center");
    this.add(ad, "South");
    this.pack();
    this.setVisible(true);
  }

	/** 
		* Sort the speaker's list based on frequency 
		*/
  public void reorder() {
    Collections.sort(speakersList, new Comparator<Assembly>() {
      @Override
      public int compare(Assembly a1, Assembly a2) {
        return a1.getTalked() - a2.getTalked();
      }
    });
  }

	/** 
		* Display the speaker's list
		*/
  public void displaySpeakersList() {
    try {
      this.remove(list);
    }
    catch (NullPointerException e) {}
    list = new JPanel(new GridLayout(speakersList.size(), 1));
    for (Assembly x : speakersList) {
      JButton temp = new JButton(x.getName());
      temp.addActionListener(this);
      temp.setBackground(Color.GRAY);
      list.add(temp);
    }
    this.add(list, "East");
    this.pack();
    this.setVisible(true);
  }

	/** 
		* Remove a speaker from the speaker's list 
		* @param b the button to be removed 
		*/
  public void remove(JButton b) {
    for (int i = 0; i < speakersList.size(); i++) {
      if (b.getText().equals(speakersList.get(i).getName()))
        speakersList.remove(i);
    }
    this.reorder();
    this.displaySpeakersList();
  }

	/** 
		* Read data about the present representatives from present.txt 
		*/
  public void setPresent() {
    ArrayList<Assembly> temp = this.readData("present.txt");
    for (int i = 0; i < temp.size(); i++) {
      this.present.add(temp.get(i));
    }
  }

	/** 
		* Return the ArrayList of present representatives 
		* @return present representatives
		*/
  public ArrayList<Assembly> getPresent() {
    return this.present;
  }

	/** 
		* Input edited data about frequency at which representatives speak to an ArrayList 
		*/
  public void outputFile() {
    ArrayList<Assembly> t = readData("GA.txt");
    for (int i = 0; i < t.size(); i++) {
      this.tot.add(t.get(i));
    }
    for (Assembly x : this.getPresent()) {
      for (Assembly y : this.tot) {
        if (x.getName().equals(y.getName()))
          y.setTalked(x.getTalked());
      }
    }
  }

	/** 
		* Create a new version of GA.txt to be written into 
		*/
  public void createFile() {
    try {
      o = new PrintStream(new File("GA.txt"));
    }
    catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException");
    }
  }

	/** 
		* Output edited data about frequency at which representatives speak to GA.txt 
		*/
  public void writeToFile() {
    System.setOut(o);
    for (Assembly x : this.tot) {
      System.out.println(x.getName().split(" ")[1] + " " + x.getName().split(",")[0] + " " + x.getTalked());
    }
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
  }

	/** 
		* Listen for user adding or removing representatives from the speaker's list 
		* @param e action event
		*/
  @Override
  public void actionPerformed(ActionEvent e) {
  	if (((JButton)e.getSource()).getText().equals("Adjourn")) {
  		this.setVisible(false);
  		this.outputFile();
  		this.createFile();
  		this.writeToFile();
  		this.setVisible(false);
  		this.dispose();
  	}
    else if (((JButton)e.getSource()).getBackground() == Color.white) {
      for (Assembly x : this.getPresent()) {
        if (x.getName().equals(((JButton)e.getSource()).getText())) {
          x.incrementTalked();
          speakersList.add(x);
          this.reorder();
          this.displaySpeakersList();
        }
      }
    }
    else {
      this.remove((JButton)e.getSource());
      ((JButton)e.getSource()).setBackground(Color.white);
    }
  }

	/** 
		* Run the program 
		* @param args n/a
		*/
  public static void main(String[] args) {
    FrequencyStack f = new FrequencyStack();
    f.setPresent();
    f.displayGA(f.getPresent());
  }

}
