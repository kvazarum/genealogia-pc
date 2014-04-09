/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import genealogia.ui.mainForm;

/**
 *
 * @author ingvar
 */
public class DataReceiver extends Thread{
    
    String id;
    mainForm mf;
    
    public DataReceiver(String id, mainForm mf)
    {
        this.id = id;
        this.mf = mf;
    }
    
    @Override
    public void run()
    {
        Relative hmn = new Relative();
        boolean result = hmn.setRelative(id);
        if(result){
            mf.setHuman(hmn);
        }
    }
}
