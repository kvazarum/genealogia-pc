
package interfaces;

/**
 *
 * @author kvazar
 */
public interface IFamily {

    /**
     *  Get father's ID in system
     * @return
     */
    public String getFather();

    /**
     *  Set father's ID in system
     * @param fatherID
     */
    public void setFather(String fatherID);

    /**
     *  Get mother's ID in system
     * @return
     */
    public String getMother();

    /**
     *  Set mother's ID in system
     * @param motherID
     */
    public void setMother(String motherID);

    /**
     *  Get wedding date
     * @return
     */
    public String getWDate();

    /**
     *  Set wedding date
     * @param weddingDate
     */
    public void setWDate(String weddingDate);
}
