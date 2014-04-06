package parse;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Parser {
	Analyzer analyzer;
	Parser(Analyzer a) {
		this.analyzer = a;
	}
	
	public HashMap<Integer,Integer> indexResult(ArrayList<String> list, DictMap dictMap, boolean addItIfNot) {
		HashMap<Integer, Integer> countMap = new HashMap<Integer,Integer>();
		//boolean addItIfNot = true;
		for(String w: list) {
			try {
				TokenStream stream  = analyzer.tokenStream(null, new StringReader(w));
				stream.reset();
				while(stream.incrementToken()) {
					String token = stream.getAttribute(CharTermAttribute.class).toString();
					token = token.toLowerCase();
					int id = dictMap.getIndex(token, addItIfNot);
					if(countMap.containsKey(id)) countMap.put(id, countMap.get(id) + 1);
					else countMap.put(id, 1);
	            }
				stream.end();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return countMap;
	}
	
	public HashMap<Integer,Integer> indexResult(ArrayList<String> list, DictMap dictMap) {
		return indexResult(list, dictMap, true); 
	}
}
