package com.gardyanakbar.guardianheadpaindiary.methods;

import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperation
{
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

    //History
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
}
