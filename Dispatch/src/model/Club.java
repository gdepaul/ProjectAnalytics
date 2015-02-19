package model;

import java.util.ArrayList;
import java.util.List;

import dispatchObject.DispatchObject;

public class Club {
	
	String clubName;
	
	public Club(String clubName){
		this.clubName = clubName;
	}
	
	List<DispatchObject> transactions = new ArrayList<DispatchObject>();
	
	public String totalTransactions(){
		return "TO BE IMPLEMENTED";
		
	}
	
}
