package com.gardyanakbar.guardianheadpaindiary.methods;

import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.transform.TransformerException;

import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
    /**
     * Loads the contents of a file and saves it in a List of String, with each element containing each line.
     * @param file
     * @return a list of string of the contents of the file.
     */
    public static List<String> loadTextFile(File file)
    {
        List<String> list = new ArrayList<String>();

        try
        {
            Scanner sc = new Scanner(file);

            while(sc.hasNext())
            {
                list.add(sc.nextLine());
            }
            sc.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * Check if file exists in path.
     * @param path - the path to check for the file. Make sure the path contains the file name and extension.
     * @return true if file exists.
     */
    public static final boolean dataExists(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    //History

    /**
     * Saves the history data relevant to the specified patient.
     * @param history - the history data
     * @param patient - the patient that the history is referring to.
     */
    public static void saveHistory(History history, PatientData patient)
    {
        try
        {
            File file = new File(Constants.HISTORY_FOLDER_PATH + patient.getID() + File.separator + history.getFileName());

            if (!file.exists())
            {
                //file.createNewFile();
                file.getParentFile().mkdirs();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            List<String> historyList = history.getHistory();

            for (String item : historyList)
            {
                bw.write(item);
                bw.newLine();
            }

            bw.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    //Pain Entry
    /**
     * Check if entry exists.
     * @param patient - the PatientData object
     * @param entry - the PainEntryData object
     * @return true if entry file exists.
     */
    public static final boolean entryExists(PatientData patient, PainEntryData entry)
    {
        return dataExists(Methods.generatePainDataFilePathName(patient, entry));
    }

    /**
     * Deletes entry at the specified file path
     * @param filePath
     */
    public static void deleteEntry(String filePath)
    {
        File file = new File(filePath);
        file.delete();
    }

    /**
     * Exports the specified entry.
     * @param patient - the PatientData object
     * @param entry - the PainEntryData object
     */
    public static final void exportPainData(PatientData patient, PainEntryData entry)
    {
        File file = new File(Methods.generatePainDataFolderPathName(patient, entry));
//		System.out.println(entry.toString());
        if (!file.exists())				//Check if the folder directory exists, if not make it
        {
            file.mkdirs();
        }

        file = new File(Methods.generatePainDataFilePathName(patient, entry));
        try
        {
            XMLManager.exportXML(entry.getXMLDocument(), file, 5);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
            MessageManager.showErrorDialog(e);
        }
    }

    /**
     * Saves the patient data
     * @param patientData - the PatientData object to be saved.
     */
    public static void savePatientData(PatientData patientData)
    {
        try
        {
            XMLManager.exportXML(patientData.getXMLDocument(), new File(Globals.settings.getUserDatabasePath() + File.separator + patientData.getFileName()), 5);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete the PatientData that corresponds to the specified ID
     * @param medID - ID of the patient to look for.
     */
    public static void deletePatientData(String medID)
    {
        File file = new File (Globals.settings.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator + medID + ".xml");
        file.delete();
    }

    /**
     * Updates the history by adding the item to the History object and saving it to the respective history data file.
     * @param history - the History object
     * @param patient - the PatientData object
     * @param item - the String text to be added to the history
     */
    public static void updateHistory(History history, PatientData patient, String item)
    {
        history.add(item);
        FileOperation.saveHistory(history, patient);
    }
}
