package com.ds.timetracker.ui.reports.format;


import android.content.Context;

import com.ds.timetracker.ui.reports.builders.Report;

import java.io.Serializable;

/**
 * This class will be called when a report is set and needs to be extracted
 * to a file in an specific format
 * @author Albert
 *
 */
public abstract class Format implements Serializable{

	public abstract void generateFile(Report report, Context context);
}
