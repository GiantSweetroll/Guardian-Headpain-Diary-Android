package com.gardyanakbar.guardianheadpaindiary.methods;

import android.content.Context;
import android.util.Log;

import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;
import com.gardyanakbar.guardianheadpaindiary.ui.table.PainEntryAdapter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import giantsweetroll.date.Date;
import giantsweetroll.files.FileManager;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
    private static final String TAG = "FileOperation";

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
            File file = new File(history.getFilePath(patient));

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
            Log.e(TAG, "saveHistory: " + ex.getMessage());
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
     * Delete entries
     * @param filePaths
     */
    public static void deleteEntries(List<String> filePaths)
    {
        for (int i=0; i<filePaths.size(); i++)
        {
            deleteEntry(filePaths.get(i));
        }
    }

    /**
     * Get pain data entries from the specified range of dates (inclusive).
     * @param patientID - the user id
     * @param from - date to start checking
     * @param to - date to stop checking
     * @return a list of PainEntryData objects
     */
    public static List<PainEntryData> getListOfEntries(String patientID, Date from, Date to)
    {
        Log.d(TAG, "getListOfEntries: Getting list of entries...");
        Log.d(TAG, "getListOfEntries: date range: " + from.toString(Date.DAY, Date.MONTH, Date.YEAR, "-") + " to " + to.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        List<PainEntryData> list = new ArrayList<PainEntryData>();
        try
        {
            //Filtering Start
            String userDatabasePath = Globals.settings.getEntriesDatabasePath() + File.separator + patientID + File.separator;
            //Get year range
            List<String> legibleYears = new ArrayList<String>();
            FileManager.getListOfFiles(legibleYears,
                    userDatabasePath,
                    false, FileManager.FOLDER_ONLY,
                    FileManager.NAME_ONLY);
//			MessageManager.printLine("Size of years: " + legibleYears.size());
            Log.d(TAG, "getListOfEntries: size of years before filtering: " + legibleYears.size());
            for (int i=0; i<legibleYears.size(); i++)
            {
                try
                {
                    int yearNow = Integer.parseInt(legibleYears.get(i));
                    int yearMin = from.getYear();
                    int yearMax = to.getYear();
                    if (yearNow < yearMin || yearNow > yearMax)
                    {
                        legibleYears.remove(i);		//Remove Illegible year
                        i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                    }
                }
                catch (NumberFormatException ex)
                {
                    legibleYears.remove(i);		//Remove Illegible year
                    i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                }
            }

            //Add leading zeroes to String, then sort
            Methods.addZeroesToList(legibleYears);
            Collections.sort(legibleYears);
            Log.d(TAG, "getListOfEntries: size of years after filtering: " + legibleYears.size());
            //		MessageManager.printLine("Size of elligible years: " + legibleYears.size());

            //Get month range
            LinkedHashMap<String, List<String>> legibleMonthsMap = new LinkedHashMap<String, List<String>>();
            Log.d(TAG, "getListOfEntries: Size of months before filtering: " + legibleMonthsMap.size());
            //		MessageManager.printLine("Size of eligible months before: " + legibleMonthsMap.size());
            if (legibleYears.size() == 1)
            {
                ArrayList<String> legibleMonths = new ArrayList<String>();
                FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                //		MessageManager.printLine("Number of months: " + legibleMonths.size());

                if (legibleYears.get(0).equals(Integer.toString(from.getYear())))			//If the first legible year is the same as the min year
                {
                    for (int i=0; i<legibleMonths.size(); i++)
                    {
                        //		MessageManager.printLine("Iteration month: " + i);
                        try
                        {
                            int monthNow = Integer.parseInt(legibleMonths.get(i));
                            int monthMin = from.getMonth();
                            int monthMax = to.getMonth();
                            if (legibleYears.get(0).equals(Integer.toString(to.getYear())))		//If the only legible year is the same as the max year
                            {
                                if (monthNow < monthMin || monthNow > monthMax)
                                {
                                    //			MessageManager.printLine("Month " + legibleMonths.get(i) + " is not within range of " + monthMin + " and " + monthMax);
                                    legibleMonths.remove(i);	//remove Illegible month
                                    i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            else
                            {
                                if (monthNow < monthMin)
                                {
                                    //					MessageManager.printLine("Month " + legibleMonths.get(i) + " is less than " + monthMin);
                                    legibleMonths.remove(i);	//remove Illegible month
                                    i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                        }
                        catch (NumberFormatException ex)
                        {
                            legibleMonths.remove(i);	//remove Illegible month
                            i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                        }
                    }
                }
                //If not, accept all months

                Log.d(TAG, "getListOfEntries: Size of months after fitler: " + legibleMonths.size());
//				MessageManager.printLine("Number of months after filter: " + legibleMonths.size() + " (" + legibleYears.get(0) + ")");
                legibleMonthsMap.put(legibleYears.get(0), legibleMonths);
            }
            else
            {
                //			System.out.println(legibleYears.size());
                for (int i=0; i<legibleYears.size(); i++)
                {
                    List<String> legibleMonths = new ArrayList<String>();
                    FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                    /*
                     * Program only needs to check the first year for the min month,
                     * and the last year for the max month,
                     * as all months in the year between will be selected regardless
                     */
                    if (i==0)
                    {
                        for (int a=0; a<legibleMonths.size(); a++)
                        {
                            try
                            {
                                int monthNow = Integer.parseInt(legibleMonths.get(a));
                                int monthMin = from.getMonth();
                                if (monthNow < monthMin)
                                {
                                    legibleMonths.remove(a);	//remove Illegible month
                                    a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            catch (NumberFormatException ex)
                            {
                                legibleMonths.remove(a);	//remove Illegible month
                                a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                            }
                        }
                    }
                    else if (i==legibleYears.size()-1)
                    {
                        for (int a=0; a<legibleMonths.size(); a++)
                        {
                            try
                            {
                                int monthNow = Integer.parseInt(legibleMonths.get(a));
                                int monthMax = to.getMonth();
                                if (monthNow > monthMax)
                                {
                                    legibleMonths.remove(a);	//remove Illegible month
                                    a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            catch (NumberFormatException ex)
                            {
                                legibleMonths.remove(a);	//remove Illegible month
                                a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                            }
                        }
                    }

                    //Add leading zeroes to String, then sort
                    Methods.addZeroesToList(legibleMonths);
                    Collections.sort(legibleMonths);

                    legibleMonthsMap.put(legibleYears.get(i), legibleMonths);
                }
            }
            //		MessageManager.printLine("Size of eligible months after: " + legibleMonthsMap.size());

            //Get day range
//			LinkedHashMap<LinkedHashMap<String, String>, List<String>> legibleDaysMap = new LinkedHashMap<LinkedHashMap<String, String>, List<String>>();
            LinkedHashMap<String, LinkedHashMap<String, List<String>>> legibleDaysMap = new LinkedHashMap<String, LinkedHashMap<String, List<String>>>();
            /*
             * Program only needs to check the first month of the first year to get the min day,
             * and the last month of the last year to get the max day,
             * as all other days in the months between will be selected regardless
             */

            for (Map.Entry<String, List<String>> entry : legibleMonthsMap.entrySet())
            {
                LinkedHashMap<String, List<String>> monthDayMap = new LinkedHashMap<String, List<String>>();
                for (int i=0; i<entry.getValue().size(); i++)
                {
                    List<String> legibleDays = new ArrayList<String>();
                    String path = entry.getValue().get(i);
                    //		System.out.println(path);
                    path = Methods.removeFirstZeroFromString(path);		//Remove first trailing zero (that was previously added for sorting)
                    //		System.out.println(path);
                    path = userDatabasePath + entry.getKey() + File.separator + path;
                    //		System.out.println(path);
                    FileManager.getListOfFiles(legibleDays, path, false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                    Log.d(TAG, "getListOfEntries: Amount of legible days from month " + entry.getValue().get(i) + " before filter: " + legibleDays.size());
                    //			MessageManager.printLine("Amount of legible days from month " + entry.getValue().get(i) + "before filter: " + legibleDays.size());

                    if (entry.getKey().equals(Integer.toString(from.getYear())))		//if the first eligible year is equal to the min year
                    {
                        if (entry.getValue().get(i).equals(Integer.toString(from.getMonth())))		//If the month is equal to the min month
                        {
                            int dayMin = from.getDay();
                            for (int a=0; a<legibleDays.size(); a++)
                            {
                                try
                                {
                                    int dayNow = Integer.parseInt(legibleDays.get(a));
                                    if (dayNow < dayMin)		//If day is less than the min day
                                    {
                                        legibleDays.remove(a);
                                        a = -1;
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    legibleDays.remove(a);
                                    a = -1;
                                }
                            }
                        }
                    }
                    if (entry.getKey().equals(Integer.toString(to.getYear())))	//if the last eligible year is equal to the max year
                    {
                        if (entry.getValue().get(i).equals(Integer.toString(to.getMonth())))	//if the month is equal to the max month
                        {
                            int dayMax = to.getDay();
                            for (int a=0; a<legibleDays.size(); a++)
                            {
                                try
                                {
                                    int dayNow = Integer.parseInt(legibleDays.get(a));
                                    if (dayNow > dayMax)		//If day is greater than the max day
                                    {
                                        legibleDays.remove(a);
                                        a = -1;
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    legibleDays.remove(a);
                                    a = -1;
                                }
                            }
                        }
                    }

                    //Add leading zeroes to String, then sort
                    Methods.addZeroesToList(legibleDays);
                    Collections.sort(legibleDays);

                    monthDayMap.put(entry.getValue().get(i), legibleDays);
                }
                legibleDaysMap.put(entry.getKey(), monthDayMap);
            }
            //Filtering end

            //Gather list of files from each folder
            List<String> filePaths = new ArrayList<String>();
            for (Map.Entry<String, LinkedHashMap<String, List<String>>> entryYear : legibleDaysMap.entrySet())
            {
                //Remove leading zeroes
                String year = entryYear.getKey();
                if (year.substring(0, 1).equals("0"))
                {
                    year = year.substring(1);
                }

                for (Map.Entry<String, List<String>> entryMonth : entryYear.getValue().entrySet())
                {
                    //Remove leading zeroes
                    String month = entryMonth.getKey();
                    if (month.substring(0, 1).equals("0"))
                    {
                        month = month.substring(1);
                    }

                    for (int i=0; i<entryMonth.getValue().size(); i++)
                    {
                        //Remove leading zeroes
                        String day = entryMonth.getValue().get(i);
                        if (day.substring(0, 1).equals("0"))
                        {
                            day = day.substring(1);
                        }

                        List<String> fileList = new ArrayList<String>();
//						FileManager.getListOfFiles(fileList, userDatabasePath + entryYear.getKey() + File.separator + entryMonth.getKey() + File.separator + entryMonth.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
                        String directory = userDatabasePath + year + File.separator + month + File.separator + day;
                        Log.d(TAG, "getListOfEntries: directory: " + directory);
                        FileManager.getListOfFiles(fileList, directory, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
                        for (int a=0; a<fileList.size(); a++)
                        {
                            filePaths.add(fileList.get(a));
                        }
                    }
                }
            }

            //Parse into PainEntryData
            Log.d(TAG, "getListOfEntries: parsing into PainEntryData...");
            for (int i=0; i<filePaths.size(); i++)
            {

                try
                {
                    Log.d(TAG, "getListOfEntries: parsing " + filePaths.get(i) + "...");
                    PainEntryData entry = new PainEntryData(FileOperation.createDocument(filePaths.get(i)));
                    list.add(entry);
                    Log.d(TAG, "getListOfEntries: " + (i+1) + " items parsed");
                }
                catch (ParserConfigurationException | SAXException ex)
                {
                    Log.d(TAG, "getListOfEntries: Failed to parse " + filePaths.get(i));
                    Log.e(TAG, "getListOfEntries: " + ex.getMessage());
                    ex.printStackTrace();
                }
                catch(IOException ex)
                {
                    Log.d(TAG, "getListOfEntries: Failed to parse " + filePaths.get(i) + " due to an IOException: " + ex.getMessage());
                    Log.e(TAG, "getListOfEntries: " + ex.getMessage());
                }
            }

            //		Collections.sort(list, new SortByDate());

//			MessageManager.printLine("Size of entries: " + list.size());
        }
        catch(Exception ex)
        {
//			ex.printStackTrace();
        }

        Log.d(TAG, "getListOfEntries: entries collected with a size of " + Integer.toString(list.size()));

        return list;
    }

    /**
     * Get a list of the file path of the entries from the specified date range (inclusive)
     * @param patientID - the user id
     * @param from - date to start checking
     * @param to - date to stop checking
     * @return a list of the file path of the entries.
     */
    public static List<String> getListOfEntriesPath(String patientID, Date from, Date to)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            //Filtering Start
            String userDatabasePath = Globals.settings.getDataMap().get(Settings.DATABASE_PATH) + File.separator + patientID + File.separator;
            //Get year range
            List<String> legibleYears = new ArrayList<String>();
            FileManager.getListOfFiles(legibleYears,
                    userDatabasePath,
                    false, FileManager.FOLDER_ONLY,
                    FileManager.NAME_ONLY);
//			MessageManager.printLine("Size of years: " + legibleYears.size());
            for (int i=0; i<legibleYears.size(); i++)
            {
                try
                {
                    int yearNow = Integer.parseInt(legibleYears.get(i));
                    int yearMin = from.getYear();
                    int yearMax = to.getYear();
                    if (yearNow < yearMin || yearNow > yearMax)
                    {
                        legibleYears.remove(i);		//Remove Illegible year
                        i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                    }
                }
                catch (NumberFormatException ex)
                {
                    legibleYears.remove(i);		//Remove Illegible year
                    i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                }
            }

            //Add leading zeroes to String, then sort
            Methods.addZeroesToList(legibleYears);
            Collections.sort(legibleYears);
            //		MessageManager.printLine("Size of elligible years: " + legibleYears.size());

            //Get month range
            LinkedHashMap<String, List<String>> legibleMonthsMap = new LinkedHashMap<String, List<String>>();
            //		MessageManager.printLine("Size of eligible months before: " + legibleMonthsMap.size());
            if (legibleYears.size() == 1)
            {
                ArrayList<String> legibleMonths = new ArrayList<String>();
                FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                //		MessageManager.printLine("Number of months: " + legibleMonths.size());

                if (legibleYears.get(0).equals(Integer.toString(from.getYear())))			//If the first legible year is the same as the min year
                {
                    for (int i=0; i<legibleMonths.size(); i++)
                    {
                        //		MessageManager.printLine("Iteration month: " + i);
                        try
                        {
                            int monthNow = Integer.parseInt(legibleMonths.get(i));
                            int monthMin = from.getMonth();
                            int monthMax = to.getMonth();
                            if (legibleYears.get(0).equals(Integer.toString(to.getYear())))		//If the only legible year is the same as the max year
                            {
                                if (monthNow < monthMin || monthNow > monthMax)
                                {
                                    //			MessageManager.printLine("Month " + legibleMonths.get(i) + " is not within range of " + monthMin + " and " + monthMax);
                                    legibleMonths.remove(i);	//remove Illegible month
                                    i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            else
                            {
                                if (monthNow < monthMin)
                                {
                                    //					MessageManager.printLine("Month " + legibleMonths.get(i) + " is less than " + monthMin);
                                    legibleMonths.remove(i);	//remove Illegible month
                                    i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                        }
                        catch (NumberFormatException ex)
                        {
                            legibleMonths.remove(i);	//remove Illegible month
                            i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                        }
                    }
                }
                //If not, accept all months

//				MessageManager.printLine("Number of months after filter: " + legibleMonths.size() + " (" + legibleYears.get(0) + ")");
                legibleMonthsMap.put(legibleYears.get(0), legibleMonths);
            }
            else
            {
                //			System.out.println(legibleYears.size());
                for (int i=0; i<legibleYears.size(); i++)
                {
                    List<String> legibleMonths = new ArrayList<String>();
                    FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                    /*
                     * Program only needs to check the first year for the min month,
                     * and the last year for the max month,
                     * as all months in the year between will be selected regardless
                     */
                    if (i==0)
                    {
                        for (int a=0; a<legibleMonths.size(); a++)
                        {
                            try
                            {
                                int monthNow = Integer.parseInt(legibleMonths.get(a));
                                int monthMin = from.getMonth();
                                if (monthNow < monthMin)
                                {
                                    legibleMonths.remove(a);	//remove Illegible month
                                    a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            catch (NumberFormatException ex)
                            {
                                legibleMonths.remove(a);	//remove Illegible month
                                a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                            }
                        }
                    }
                    else if (i==legibleYears.size()-1)
                    {
                        for (int a=0; a<legibleMonths.size(); a++)
                        {
                            try
                            {
                                int monthNow = Integer.parseInt(legibleMonths.get(a));
                                int monthMax = to.getMonth();
                                if (monthNow > monthMax)
                                {
                                    legibleMonths.remove(a);	//remove Illegible month
                                    a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                                }
                            }
                            catch (NumberFormatException ex)
                            {
                                legibleMonths.remove(a);	//remove Illegible month
                                a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
                            }
                        }
                    }

                    //Add leading zeroes to String, then sort
                    Methods.addZeroesToList(legibleMonths);
                    Collections.sort(legibleMonths);

                    legibleMonthsMap.put(legibleYears.get(i), legibleMonths);
                }
            }
            //		MessageManager.printLine("Size of eligible months after: " + legibleMonthsMap.size());

            //Get day range
//			LinkedHashMap<LinkedHashMap<String, String>, List<String>> legibleDaysMap = new LinkedHashMap<LinkedHashMap<String, String>, List<String>>();
            LinkedHashMap<String, LinkedHashMap<String, List<String>>> legibleDaysMap = new LinkedHashMap<String, LinkedHashMap<String, List<String>>>();
            /*
             * Program only needs to check the first month of the first year to get the min day,
             * and the last month of the last year to get the max day,
             * as all other days in the months between will be selected regardless
             */

            for (Map.Entry<String, List<String>> entry : legibleMonthsMap.entrySet())
            {
                LinkedHashMap<String, List<String>> monthDayMap = new LinkedHashMap<String, List<String>>();
                for (int i=0; i<entry.getValue().size(); i++)
                {
                    List<String> legibleDays = new ArrayList<String>();
                    String path = entry.getValue().get(i);
                    //		System.out.println(path);
                    path = Methods.removeFirstZeroFromString(path);		//Remove first trailing zero (that was previously added for sorting)
                    //		System.out.println(path);
                    path = userDatabasePath + entry.getKey() + File.separator + path;
                    //		System.out.println(path);
                    FileManager.getListOfFiles(legibleDays, path, false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
                    //			MessageManager.printLine("Amount of legible days from month " + entry.getValue().get(i) + "before filter: " + legibleDays.size());

                    if (entry.getKey().equals(Integer.toString(from.getYear())))		//if the first eligible year is equal to the min year
                    {
                        if (entry.getValue().get(i).equals(Integer.toString(from.getMonth())))		//If the month is equal to the min month
                        {
                            int dayMin = from.getDay();
                            for (int a=0; a<legibleDays.size(); a++)
                            {
                                try
                                {
                                    int dayNow = Integer.parseInt(legibleDays.get(a));
                                    if (dayNow < dayMin)		//If day is less than the min day
                                    {
                                        legibleDays.remove(a);
                                        a = -1;
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    legibleDays.remove(a);
                                    a = -1;
                                }
                            }
                        }
                    }
                    if (entry.getKey().equals(Integer.toString(to.getYear())))	//if the last eligible year is equal to the max year
                    {
                        if (entry.getValue().get(i).equals(Integer.toString(to.getMonth())))	//if the month is equal to the max month
                        {
                            int dayMax = to.getDay();
                            for (int a=0; a<legibleDays.size(); a++)
                            {
                                try
                                {
                                    int dayNow = Integer.parseInt(legibleDays.get(a));
                                    if (dayNow > dayMax)		//If day is greater than the max day
                                    {
                                        legibleDays.remove(a);
                                        a = -1;
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    legibleDays.remove(a);
                                    a = -1;
                                }
                            }
                        }
                    }

                    //Add leading zeroes to String, then sort
                    Methods.addZeroesToList(legibleDays);
                    Collections.sort(legibleDays);

                    monthDayMap.put(entry.getValue().get(i), legibleDays);
                }
                legibleDaysMap.put(entry.getKey(), monthDayMap);
            }
            //Filtering end

            //Gather list of files from each folder
            List<String> filePaths = new ArrayList<String>();
            for (Map.Entry<String, LinkedHashMap<String, List<String>>> entryYear : legibleDaysMap.entrySet())
            {
                //Remove leading zeroes
                String year = entryYear.getKey();
                if (year.substring(0, 1).equals("0"))
                {
                    year = year.substring(1);
                }

                for (Map.Entry<String, List<String>> entryMonth : entryYear.getValue().entrySet())
                {
                    //Remove leading zeroes
                    String month = entryMonth.getKey();
                    if (month.substring(0, 1).equals("0"))
                    {
                        month = month.substring(1);
                    }

                    for (int i=0; i<entryMonth.getValue().size(); i++)
                    {
                        //Remove leading zeroes
                        String day = entryMonth.getValue().get(i);
                        if (day.substring(0, 1).equals("0"))
                        {
                            day = day.substring(1);
                        }

                        List<String> fileList = new ArrayList<String>();
//						FileManager.getListOfFiles(fileList, userDatabasePath + entryYear.getKey() + File.separator + entryMonth.getKey() + File.separator + entryMonth.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
                        FileManager.getListOfFiles(fileList, userDatabasePath + year + File.separator + month + File.separator + day, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
                        for (int a=0; a<fileList.size(); a++)
                        {
                            filePaths.add(fileList.get(a));
                        }
                    }
                }
            }

            list.addAll(filePaths);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return list;
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
        }
    }

    /**
     * Saves the patient data
     * @param patientData - the PatientData object to be saved.
     */
    public static void savePatientData(PatientData patientData)
    {
        String folderPath = Globals.settings.getUserDatabasePath() + File.separator;
        File file = new File(folderPath);
        if (!file.exists())				//Check if the folder directory exists, if not make it
        {
            file.mkdirs();
        }

        try
        {
            Log.d(TAG, "savePatientData: will be saved at " + folderPath + patientData.getFileName());
            XMLManager.exportXML(patientData.getXMLDocument(), new File(folderPath + patientData.getFileName()), 5);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of registered patient data
     * @return a list of PatientData objects
     */
    public static List<PatientData> getListOfPatients()
    {
        List<PatientData> list = new ArrayList<PatientData>();
        List<String> files = new ArrayList<String>();

        try
        {
            Log.d(TAG, "getListOfPatients: getting list from path " + Globals.settings.getUserDatabasePath() + File.separator);
            FileManager.getListOfFiles(files, Globals.settings.getUserDatabasePath() + File.separator, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);

            for (int i=0; i<files.size(); i++)
            {
                try
                {
                    list.add(new PatientData(FileOperation.createDocument(files.get(i))));
                }
                catch (ParserConfigurationException | SAXException | IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(NullPointerException ex) {};

        Log.d(TAG, "getListOfPatients: found " + list.size() + " patient data");

        return list;
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

    /**
     * Parses an xml file using DOMParser.
     * @param path
     * @return a Document object of the XML
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document createDocument(String path) throws ParserConfigurationException, IOException, SAXException
    {
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(is);
    }
}
