package com.ds.timetracker.ui.reports.format;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.ui.reports.elements.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * When the father class is called from a report this class generates
 * a report in text format
 *
 * @author Albert
 */
public class TextFormatPrinter extends Format implements Serializable {

    public TextFormatPrinter() {
    }

    public void generateFile(Report report, Context context) {

        //the directory where file will be created
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String fileName = report.getName() + ".txt";

        PrintWriter writer = null;

        File file = new File(directory + File.separator + fileName);

        try {
            writer = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (Element element : report.getElements()) {
            writer.println(element.getElement());
        }

        writer.close();

    }
}
