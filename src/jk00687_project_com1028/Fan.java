package jk00687_project_com1028;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Fan {
	private List<TableInfo> tableInformation = new ArrayList<TableInfo>();
	private List<KnockoutTree> knockoutTree = new ArrayList<KnockoutTree>();

	public Fan(List<TableInfo> tableInformation, List<KnockoutTree> knockoutTree) {
		super();
		this.tableInformation = tableInformation;
		this.knockoutTree = knockoutTree;
	}
	
	public String showKnockouts(List<KnockoutTree> knockoutTree) {
		
		return null;
	}
	
	public String knockoutsSearch(List<KnockoutTree> knockoutTree) {
		
		return null;
	}
	
	public String showTable(List<TableInfo> tableInformation) {
		try{
            BufferedReader buf = new BufferedReader(new FileReader("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt"));
            String lineJustFetched = null;
            String[] wordsArray;

            while(true){
                lineJustFetched = buf.readLine();
                if(lineJustFetched == null){  
                    break; 
                }else{
                    wordsArray = lineJustFetched.split("\t");
                    for(String each : wordsArray){
                        if(!"".equals(each)){
                            //tableInformation.add(each);
                        }
                    }
                }
            }
            buf.close();

        }catch(Exception e){
            e.printStackTrace();
        }
		return tableInformation.toString();
	}

	public String tableSearch(List<TableInfo> tableInformation) {
		
		return null;
	}
	
	@Override
	public String toString() {
		
		return null;
	}
}
