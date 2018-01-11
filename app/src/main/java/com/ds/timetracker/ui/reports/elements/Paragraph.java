package com.ds.timetracker.ui.reports.elements;

public class Paragraph extends Element{
	
	private String paragraph;
	
	public Paragraph(String paragraph){
		this.paragraph = paragraph;
	}
	
	@Override
	public String getElement(){
		return paragraph;
	}

}
