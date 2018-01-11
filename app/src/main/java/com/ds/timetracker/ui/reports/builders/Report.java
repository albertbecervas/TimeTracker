package com.ds.timetracker.ui.reports.builders;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.ui.reports.elements.Element;
import com.ds.timetracker.ui.reports.format.Format;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Generic class of a report that keeps the common data in all report types
 *
 * @author Albert
 */
public class Report implements Serializable {

    protected String startDateString = ""; //dd/MM/yyyy hh:mm:ss
    protected String endDateString = "";
    protected long startPeriodTime;
    protected long endPeriodTime;
    protected Date startPeriodDate;
    protected Date endPeriodDate;

    protected String generationDate;

    protected String name;

    protected ArrayList<Item> items;

    protected ArrayList<Element> elements;

    protected Format format;
    protected String formatStr;

    public Report(String startDateString, String endDateString) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        this.startDateString = startDateString;
        this.endDateString = endDateString;

        try {
            startPeriodDate = df.parse(startDateString);
            endPeriodDate = df.parse(endDateString);
            //specified period which we want to get the report from
            startPeriodTime = df.parse(startDateString).getTime();
            endPeriodTime = df.parse(endDateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        generationDate = df.format(new Date());

        elements = new ArrayList<Element>();
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatStr() {
        return formatStr;
    }

    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }
}
