package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.util.Version;

public class TokenParser {
	
	String outputPath;
	File out_Folder;
	DictMap dictMap;
	Dataset trainingSet;
	Dataset testingSet;
	//ArrayList<HashMap<Integer,Integer>> toWriteList;
	//ArrayList<Integer> labelList;
	Parser parser;
	
	public TokenParser(String outputPath) {
		this.outputPath = outputPath;
		out_Folder = new File(outputPath);
		if(!out_Folder.exists()) out_Folder.mkdir();
		dictMap = new DictMap();
		
		trainingSet = new Dataset();
		testingSet = new Dataset();
		//toWriteList = new ArrayList<HashMap<Integer,Integer>>();
		//labelList = new ArrayList<Integer>();
		parser = new Parser(new WhitespaceAnalyzer(Version.LUCENE_47));
	}
	
	public void parseOneType(Dataset dataset, String InputPath, Type type, boolean addItIfTrue) {
		ArrayList<HashMap<Integer,Integer>> toWriteList = dataset.toWriteList;
		ArrayList<Integer> labelList = dataset.labelList;
		BufferedReader br = null;
		boolean readingBody = false;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(InputPath));
			ArrayList<String> body = null;
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.equals("")) continue;
				if(sCurrentLine.charAt(0) != '^') {
					if (readingBody) {
						readingBody = false;
						toWriteList.add(parser.indexResult(body, dictMap,addItIfTrue));
						labelList.add(type.getValue());
						body = null;
					}
					//continue; 
				}
				else {
					if(!readingBody) {
						body = new ArrayList<String>();
						readingBody = true;
					}
					body.add(sCurrentLine.substring(1));
				}
				
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public void printList() {
		printList(this.testingSet);
		printList(this.trainingSet);
		File dict_file = new File(out_Folder, "dict"); 
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(dict_file));
			TreeMap<Integer,String> indexMap = dictMap.getIndexMap();
			for(Integer key : indexMap.keySet()) {
				bw.write(key + ":" + indexMap.get(key) + "\n");
			}
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printList(Dataset dataset) {
		String prefix = null;
		if(dataset.equals(this.trainingSet)) prefix = "train";
		else prefix = "test";
		ArrayList<HashMap<Integer,Integer>> toWriteList = dataset.toWriteList;
		ArrayList<Integer> labelList = dataset.labelList;
		File parseFile = new File(out_Folder, prefix + "data");
		try {
			BufferedWriter file_bw = new BufferedWriter(new FileWriter(parseFile));
			for(HashMap<Integer,Integer> map: toWriteList) {
				int length = getLength(map);
				file_bw.write(length + " ");
				for(Integer key: map.keySet()) {
					file_bw.write(key + ":" + map.get(key) + " ");
				}
				file_bw.write("\n");
			}
			file_bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		File labelFile = new File(out_Folder, prefix + "labelFile");
		try {
			BufferedWriter label_bw = new BufferedWriter(new FileWriter(labelFile));
			for(Integer i : labelList) {
				label_bw.write(i + "\n");
			}
			label_bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}


	public static void main(String[] args) {
		String InputFolder = "/home/mz342/github/L101/GenSpam/"; 
		String outputPath = "/home/mz342/github/L101/outputTestingSkip";
		TokenParser tp = new TokenParser(outputPath);
		tp.parseOneType(tp.trainingSet,InputFolder + "train_GEN.ems", Type.LEGI,true);
		tp.parseOneType(tp.trainingSet,InputFolder + "train_SPAM.ems", Type.SPAM,true);
		tp.parseOneType(tp.testingSet,InputFolder + "test_GEN.ems", Type.LEGI,false);
		tp.parseOneType(tp.testingSet,InputFolder + "test_SPAM.ems", Type.SPAM,false);
		tp.printList();
	}
	public static int getLength(HashMap<Integer,Integer> map) {
		return map.keySet().size();
		/*int length = 0;
		for(Integer key: map.keySet()) {
			length += map.get(key);
		}
		return length;*/
	}
}
