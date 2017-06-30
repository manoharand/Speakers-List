/** 
  * The Assembly object to store data about representatives. 
  * @author Divya Manoharan 
  */
public class Assembly {

  private String name;
  private int talked;
  private String demographic;

  public Assembly(String name) {
    this.name = name;
  }

  public Assembly(String name, int talked) {
    this.name = name;
    this.talked = talked;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void incrementTalked() {
    this.talked++;
  }

  public int getTalked() {
    return this.talked;
  }

	public void setTalked(int talked) {
		this.talked = talked;
	}

  public void setDemographic(String demographic) {
    this.demographic = demographic;
  }

  public String getDemographic() {
    return this.demographic;
  }

}
