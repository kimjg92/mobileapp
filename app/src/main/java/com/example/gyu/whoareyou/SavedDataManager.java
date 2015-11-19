package com.example.gyu.whoareyou;

import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by GYU on 2015-11-17.
 */
public class SavedDataManager extends AppCompatActivity implements Serializable {
    String SaveDataDir;// = getFilesDir() + File.separator + "saved_data";
    Vector<Action_log_Object> action_log_object = new Vector<>();

    //public void addData ()

    public SavedDataManager (){
        /*SaveDataDir = path;
        action_log_object = loadVectorObjectOnCreate();
        System.out.println("SavedDataManager Class : Class Creator executed, action_log_object : " + action_log_object);*/
    }

    public void savePicture (String path){
       /* Action_log_Object temp = new Action_log_Object();
        action_log_object.add(temp);
        Action_log_Object temp = new Action_log_Object();
        temp.picture_object.set_Path(path);
        System.out.println("SavedDataManager Class : SavePicture Method call, temp : " + temp.picture_object.get_Path());
        action_log_object.add(temp);
        saveVectorObject();*/
    }

    public void saveVectorObject(){//Vector vec) {

       /* try {
            //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("\\storage\\emulated\\0\\Pictures\\" + File.separator + string.toString()));
            //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/storage/emulated/0/Pictures" + File.separator + string.toString()));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SaveDataDir));
            //System.out.println(getExternalFilesDir(null) + File.separator + string.toString());
            oos.writeObject(action_log_object);
            oos.close();
        } catch (Exception e) {

        }*/
    }
    private Vector loadVectorObjectOnCreate(){
        /*Vector<Action_log_Object>temp = null;
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SaveDataDir));
            temp = (Vector)ois.readObject();
            ois.close();
            return temp;

        } catch (Exception e){
            System.out.println("SaveDataManager Class loadVectorObjectOnCreate Method exception stack trace : ");
            e.printStackTrace();
            return temp;
        }*/
        return null;

    }
    public void loadVectorObject() {

        try{
            Vector<Action_log_Object> temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilesDir() + File.separator + "save_data.WRY"));
            temp = (Vector<Action_log_Object>) ois.readObject();
            action_log_object = temp;
            ois.close();
        } catch (FileNotFoundException e){
            System.out.println("SavedDataManager Class - loadVectorObject Method : FileNotFoundException, create File (call CreateSaveData Method)");
            createSaveData();
        } catch (Exception e){
            System.out.println("SavedDataManager Class - loadVectorObject Method : Exception, print Stack Trace");
            createSaveData();
            e.printStackTrace();
        }
      /*
       // Vector temp;
        try {
            //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("\\storage\\emulated\\0\\Pictures\\" + File.separator + string.toString()));
            //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/emulated/0/Pictures" + File.separator + string.toString()));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SaveDataDir));

            action_log_object = (Vector) ois.readObject();
            System.out.println("SavedDataManager Class : loadVectorObject executed, loading from " + SaveDataDir);
            ois.close();
            //return temp;
        } catch (FileNotFoundException fe) {
            System.out.println("SavedDataManager Class : File Not Found, Create save_data at " + SaveDataDir);
            createVectorObject();
            //return temp;
        } catch (StreamCorruptedException se){
            System.out.println("SavedDataManager Class : StreamCorruptedException");
        } catch (IOException ie){
            System.out.println("SavedDataManager Class : IOException");
            //ie.printStackTrace();
        } catch (ClassNotFoundException ce){
            System.out.println("SavedDataManager Class : ClassNotFoundException");
        }*/

    }
    private void createSaveData(){//Vector vec) {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilesDir() + File.separator + "save_data.WRY"));
            oos.writeObject(action_log_object);
            oos.close();
        } catch (Exception e){
            System.out.println("SavedDataManager Class - createSaveData Method : Exception");
        }
        /*try {
            Vector<Action_log_Object> new_action_log_object = new Vector<>();
            //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("\\storage\\emulated\\0\\Pictures\\" + File.separator + string.toString()));
            //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/storage/emulated/0/Pictures" + File.separator + string.toString()));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SaveDataDir));
            //System.out.println(getExternalFilesDir(null) + File.separator + string.toString());
            oos.writeObject(new_action_log_object);
            oos.close();
        } catch (Exception e) {

        }*/
    }
    public String getDataSavePath(){
        return SaveDataDir;
    }
}
