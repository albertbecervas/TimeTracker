package com.ds.timetracker.ui.reports.format;

import com.ds.timetracker.ui.reports.builders.BriefReport;
import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.ui.reports.elements.Element;
import com.ds.timetracker.ui.reports.elements.Paragraph;
import com.ds.timetracker.ui.reports.elements.Separator;
import com.ds.timetracker.ui.reports.elements.Title;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/**
 * When the father class is called from a report this class generates
 * a report in web format
 *
 * @author Albert
 */
public class HtmlFormatPrinter extends Format {

    public HtmlFormatPrinter() {
    }

    @Override
    public void generateFile(Report report) {
        PrintWriter writer = null;
        String fileTitle = " ";

        if (report instanceof BriefReport) {
            fileTitle = "BriefReport.html";
        } else {
            fileTitle = "DetailedReport.html";
        }

        try {
            writer = new PrintWriter(fileTitle, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (Element element : report.getElements()) {
            if (element instanceof Title) {
                String titleHtml = "<h4>"
                        + element.getElement()
                        + "</h4>";
                writer.print(titleHtml);
            }
            if (element instanceof Separator) {
                String separatorHtml = "<h4>"
                        + element.getElement()
                        + "</h4>";
                writer.println(separatorHtml);
            }
            if (element instanceof Paragraph) {
                String paragraphHtml = "<p>"
                        + element.getElement()
                        + "</p>";
                writer.println(paragraphHtml);
            }
        }

        writer.close();
    }


}
