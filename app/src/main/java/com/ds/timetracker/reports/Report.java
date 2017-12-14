package com.ds.timetracker.reports;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.reports.elements.Element;
import com.ds.timetracker.reports.format.Format;

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
public class Report {

    protected String startDateString = "29/11/2017 22:25:16"; //dd/MM/yyyy hh:mm:ss
    protected String endDateString = "29/11/2017 22:29:46";
    protected long startPeriodTime;
    protected long endPeriodTime;
    protected Date startPeriodDate;
    protected Date endPeriodDate;

    protected String generationDate;

    protected ArrayList<Item> items;

    protected ArrayList<Element> elements;

    protected Format format;

    public Report() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

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
}