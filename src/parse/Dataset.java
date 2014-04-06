package parse;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataset {
	ArrayList<HashMap<Integer,Integer>> toWriteList;
	ArrayList<Integer> labelList;
	
	public Dataset(){
		toWriteList = new ArrayList<HashMap<Integer,Integer>>();
		labelList = new ArrayList<Integer>();
	}
}
