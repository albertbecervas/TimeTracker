package com.ds.timetracker.reports.format;


import com.ds.timetracker.reports.Report;

/**
 * This class will be called when a report is set and needs to be extracted
 * to a file in an specific format
 * @author Albert
 *
 */
public abstract class Format {

	public abstract void generateFile(Report report);
}
