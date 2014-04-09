package interfaces;

/**
 *
 * @author kvazar
 */
public interface IHuman {
    
    public genealogia.enums.Gender getGender();
    
    public void setGender(genealogia.enums.Gender gender);

    public String getRod();
    
    public void setRod(String rod);
    
    public String getName();
    
    public void setName(String name);
    
    public void setBDate(String bdate);
    
    public String getBDate();
    
    public void setSurname(String sname);
    
    public String getSurname();
    
    public void setMiddleName(String mname);
    
    public String getMiddleName();
    
    public void setBPlace(String bplace);
    
    public String getBPlace();
    
    public void setFather(String father);
    
    public String getFather();
    
    public void setMother(String mother);
    
    public String getMother();
    
    public String getDDate();
    
    public void setDDate(String ddate);
}
