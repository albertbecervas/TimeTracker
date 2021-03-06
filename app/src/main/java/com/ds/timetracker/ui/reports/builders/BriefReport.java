package com.ds.timetracker.ui.reports.builders;

import android.content.Context;

import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.reports.elements.Element;
import com.ds.timetracker.ui.reports.elements.Paragraph;
import com.ds.timetracker.ui.reports.elements.Separator;
import com.ds.timetracker.ui.reports.elements.Title;
import com.ds.timetracker.ui.reports.format.HtmlFormatPrinter;
import com.ds.timetracker.ui.reports.format.TextFormatPrinter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Generates a Brief report of the main items that shows: Duration, initial date, final date
 * and name of every item
 *
 * @author Albert
 */
public class BriefReport extends Report implements Serializable{

    public ArrayList<ItemReportDetail> mainItems;

    private long duration = 0;

    private DateFormat df;

    public BriefReport(String name,ArrayList<Item> items, String format, String initialDate, String finalDate) {
        super(initialDate,finalDate);
        this.items = items;
        this.name = name;
        this.formatStr = format;

        mainItems = new ArrayList<>();

        this.df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        setFormat(format);

    }

    /**
     * Sets the format of the file that will be generated
     */
    private void setFormat(String format) {
        switch (format) {
            case "txt":
                this.format = new TextFormatPrinter();
                break;
            case "html":
                this.format = new HtmlFormatPrinter();
        }
    }


    public void generateBriefReport(Context context) {
        for (Item item : items) {
            //calculates all item details and saves them in the projects lists
            setItemDetails(item);
        }

        elements = new ArrayList<>();
        setReportElements();

        format.generateFile(this, context);
    }

    public String getFormattedElementsToDisplay(){
        for (Item item : items) {
            //calculates all item details and saves them in the projects lists
            setItemDetails(item);
        }

        elements = new ArrayList<>();
        setReportElements();
        StringBuilder formatted = new StringBuilder();
        for (Element element: elements){
            String e = element.getElement() + "\n";
            formatted.append(e);
        }

        return formatted.toString();
    }

    /**
     * Sets a list of items with all calculated details
     *
     * @param item Project or ic_task that we want to calculate the details
     */
    private void setItemDetails(Item item) {
        recursiveTreeSearch(item);
        Date initialDate = calculateInitialDate(item);
        Date endDate = calculateFinalDate(item);

        String initialDateText;
        String endDateText;
        if (initialDate != null && endDate != null) {
            initialDateText = df.format(initialDate);
            endDateText = df.format(endDate);
        } else {
            initialDateText = "-";
            endDateText = "-";
        }

        mainItems.add(new ItemReportDetail(item.getName(), duration / 1000, initialDateText, endDateText));//add this to the node projects lists
        duration = 0; //Reset the global variable in order to start again with the other item
    }

    /**
     * Iterates through the items in order to calculate every subItem parameters
     *
     * @param item Project or ic_task that we want to calculate the details
     */
    private void recursiveTreeSearch(Item item) {
        long itemDuration = 0;
        if (item instanceof Task) { //Basic case
            for (Interval interval : ((Task) item).getIntervals()) {

                itemDuration = setIntervalDetails(item, itemDuration, interval);
            }

            duration += itemDuration;//update the projects time

        } else {
            for (Item subItem : ((Project) item).getItems()) {
                recursiveTreeSearch(subItem); //recursive function
            }
        }
    }


    /**
     * Calculates the duration of every interval.
     *
     * @param item         ic_task that we want to calculate the details of the interval
     * @param itemDuration duration of the ic_task to return in the iterative function
     * @param interval     that we want to set details
     */
    private long setIntervalDetails(Item item, long itemDuration, Interval interval) {
        long intervalDuration = 0;
        Date initialDate = calculateInitialDate(interval);
        Date endDate = calculateFinalDate(interval);

        if (initialDate != null && endDate != null) {
            intervalDuration = endDate.getTime() - initialDate.getTime();
            itemDuration += intervalDuration;
        } else {
            intervalDuration = 0;
        }
        return itemDuration;
    }

    private Date calculateInitialDate(Item item) {
        long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
        long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

        if (initialDateTime <= startPeriodTime) {
            if (endDateTime <= startPeriodTime) {
                return null;
            }
            return startPeriodDate;
        } else if (initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime) {
            return item.getPeriod().getStartWorkingDate();
        } else {
            return null;
        }
    }

    private Date calculateInitialDate(Interval interval) {
        long initialDateTime = interval.getInitialDate().getTime();
        long endDateTime = interval.getFinalDate().getTime();

        if (initialDateTime <= startPeriodTime) {
            if (endDateTime <= startPeriodTime) {
                return null;
            }
            return startPeriodDate;
        } else if (initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime) {
            return interval.getInitialDate();
        } else {
            return null;
        }
    }

    private Date calculateFinalDate(Item item) {
        long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
        long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

        if (endDateTime >= endPeriodTime) {
            if (initialDateTime >= endPeriodTime) {
                return null;
            }
            return endPeriodDate;
        } else if (endDateTime < endPeriodTime && endDateTime >= startPeriodTime) {
            return item.getPeriod().getFinalWorkingDate();
        } else {
            return null;
        }
    }

    private Date calculateFinalDate(Interval interval) {
        long initialDateTime = interval.getInitialDate().getTime();
        long endDateTime = interval.getFinalDate().getTime();

        if (endDateTime >= endPeriodTime) {
            if (initialDateTime >= endPeriodTime) {
                return null;
            }
            return endPeriodDate;
        } else if (endDateTime < endPeriodTime && endDateTime >= startPeriodTime) {
            return interval.getFinalDate();
        } else {
            return null;
        }
    }

    /**
     * Once all items are calculated, we set the elements that we want our report to have
     */
    private void setReportElements() {

        elements.add(new Separator());
        elements.add(new Title("INFORME BREU"));
        elements.add(new Separator());
        elements.add(new Paragraph("desde-->  " + this.startDateString));
        elements.add(new Paragraph("fins-->  " + this.endDateString));
        elements.add(new Paragraph("desde-->  " + this.generationDate));
        elements.add(new Separator());

        elements.add(new Title("Projects 	 Data_Inici 	 Data_final 	   Durada"));
        elements.add(new Separator());
        for (ItemReportDetail item : mainItems) {
            String paragraph = item.getName()
                    + "  |  "
                    + item.getInitialDate()
                    + "  |  "
                    + item.getFinalDate()
                    + "  |  "
                    + item.getIncludedDuration();
            elements.add(new Paragraph(paragraph));
        }
    }
}
