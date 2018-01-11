package com.ds.timetracker.ui.reports.elements;

import java.io.Serializable;

/**
 * This abstract class is used to create an Element of a report such as a title, paragraph, etc.
 */
public abstract class Element  implements Serializable{

	public abstract String getElement();
	
}
