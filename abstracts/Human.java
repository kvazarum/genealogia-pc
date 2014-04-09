package abstracts;

import genealogia.enums.Gender;
import interfaces.IHuman;

/**
 *
 * @author kvazar
 */
abstract public class Human implements IHuman{
/**
 * Name
 */    
    private String name;
/**
 * Surname
 */    
    private String surName;
/**
 * Middle name
 */    
    private String middleName;
/**
 * Gender
 */    
    private genealogia.enums.Gender gender;
/**
 * Date of birth
 */    
    private String bdate;
/**
 * Date of death
 */    
    private String ddate;
/**
 * Place of birth
 */    
    private String bplace;
/**
 * Фамилия отца при рождении во множественном числе
 */    
    private String rod;
/**
 * ID отца
 */    
    private String father;
/**
 * ID матери
 */    
    private String mother;
    

    public Human() {
        
    }

//  Дата рождения   ------------------------------------------  
    @Override
    public String getBDate() {
        return bdate;
    }

    @Override
    public void setBDate(String bdate) {
        this.bdate = bdate;
    }
    
//  Дата смерти   ------------------------------------------  
    @Override
    public String getDDate() {
        return ddate;
    }

    @Override
    public void setDDate(String ddate) {
        this.ddate = ddate;
    }    
    
//  Место рождения  ------------------------------------------   
    @Override
    public String getBPlace() {
        return bplace;
    }

    @Override
    public void setBPlace(String bplace) {
        this.bplace = bplace;
    }

//  Пол     --------------------------------------------------- 
    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

//  Имя     ---------------------------------------------------- 
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
//  Фамилия ---------------------------------------------------
    @Override
    public String getSurname() {
        return surName;
    }

    @Override
    public void setSurname(String sname) {
        this.surName = sname;
    }

//  Отчество  -------------------------------------------------  
    @Override
    public String getMiddleName() {
        return this.middleName;
    }

    @Override
    public void setMiddleName(String mname) {
        this.middleName = mname;
    }

//  Род     ---------------------------------------------------
    @Override
    public String getRod() {
        return rod;
    }

    @Override
    public void setRod(String rod) {
        this.rod = rod;
    }

//  Отец   ---------------------------------------------------- 
    @Override
    public String getFather() {
        return father;
    }

    @Override
    public void setFather(String father) {
        this.father = father;
    }
    
//  Мать    ---------------------------------------------------
    @Override
    public String getMother() {
        return mother;
    }

    @Override
    public void setMother(String mother) {
        this.mother = mother;
    }
    
}
        

