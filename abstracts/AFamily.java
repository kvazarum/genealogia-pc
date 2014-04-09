/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abstracts;

import interfaces.IFamily;

/**
 *
 * @author kvazar
 */
public abstract class AFamily implements IFamily
{
    private String father;
    private String mother;
    private String wdate;

    @Override
    public String getFather() 
    {
        return this.father;
    }

    @Override
    public void setFather(String fatherID) 
    {
        this.father = fatherID;
    }

    @Override
    public String getMother() 
    {
        return mother;
    }

    @Override
    public void setMother(String motherID) 
    {
        this.mother = motherID;
    }

    @Override
    public String getWDate() 
    {
        return this.wdate;
    }

    @Override
    public void setWDate(String weddingDate) 
    {
        this.wdate = weddingDate;
    }
    
}
