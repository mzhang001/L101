package parse;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class DictMap {
	HashMap<String, Integer> dictMap;
	TreeMap<Integer,String> indexMap;
	int maxID;
	
	public DictMap() {
		this.dictMap = new HashMap<String,Integer>();
		this.indexMap = new TreeMap<Integer,String>();
		maxID = 0;
	}
	
	public int getIndex(String key, boolean addItIfNot) {
		if(dictMap.containsKey(key)) return dictMap.get(key);
		else if(addItIfNot) {
			addKey(key);
			return maxID;
		}
		return -1;
	}
	
	public int getIndex(String key) {
		if(dictMap.containsKey(key)) return dictMap.get(key);
		else return -1;
	}
	
	public Set<String> getKeySet() {
		return dictMap.keySet();
	}
	
	public TreeMap<Integer,String> getIndexMap() {
		return indexMap;
	}
	
	public void addKey(String key) {
		if(!dictMap.containsKey(key)) {
			dictMap.put(key, maxID);
			indexMap.put(maxID, key);
			maxID++;
		}
	}
}
