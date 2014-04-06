package parse.arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;

import org.apache.commons.lang3.StringEscapeUtils;

import parse.State;
import parse.Type;
public class ArffParser {
	TreeMap<Integer, Email> map;

	public static void main(String[] args) {
		String InputFolder = "/home/mz342/github/L101/GenSpam/"; 
		String outputFolder = "/home/mz342/github/L101/weka/fulltext/";
		File o_f = new File(outputFolder);
		if(!o_f.exists()) o_f.mkdir();
		ArffParser ap = new ArffParser();
		/*		tp.parseOneType(tp.trainingSet,InputFolder + "train_GEN.ems", Type.LEGI,true);
		tp.parseOneType(tp.trainingSet,InputFolder + "train_SPAM.ems", Type.SPAM,true);
		tp.parseOneType(tp.testingSet,InputFolder + "test_GEN.ems", Type.LEGI,false);
		tp.parseOneType(tp.testingSet,InputFolder + "test_SPAM.ems", Type.SPAM,false);*/
		ap.parseOneType(InputFolder + "train_GEN.ems", Type.LEGI);
		ap.parseOneType(InputFolder + "train_SPAM.ems", Type.SPAM);
		ap.writeEmailToArff(outputFolder + "training.arff");
		
		ap = new ArffParser();
		ap.parseOneType(InputFolder + "test_GEN.ems", Type.LEGI);
		ap.parseOneType(InputFolder + "test_SPAM.ems", Type.SPAM);
		ap.writeEmailToArff(outputFolder + "testing.arff");
		
		ap = new ArffParser();
		ap.parseOneType(InputFolder + "adapt_GEN.ems", Type.LEGI);
		ap.parseOneType(InputFolder + "adapt_SPAM.ems", Type.SPAM);
		ap.writeEmailToArff(outputFolder + "adapt.arff");
		
		ap = new ArffParser();
		ap.parseOneType(InputFolder + "adapt_GEN.ems", Type.LEGI);
		ap.parseOneType(InputFolder + "adapt_SPAM.ems", Type.SPAM);
		ap.parseOneType(InputFolder + "train_GEN.ems", Type.LEGI);
		ap.parseOneType(InputFolder + "train_SPAM.ems", Type.SPAM);
		ap.writeEmailToArff(outputFolder + "mixture.arff");
		
	}

	public void writeEmailToArff(String outputFile) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			bw.write("@RELATION email\n");
			bw.write("\n");
			//bw.write("@ATTRIBUTE ##id NUMERIC\n");
			bw.write("@ATTRIBUTE ##from string\n");
			bw.write("@ATTRIBUTE ##subject string\n");
			bw.write("@ATTRIBUTE ##body string\n");
			bw.write("@ATTRIBUTE ##embedded string\n");
			bw.write("@ATTRIBUTE ##class {" + Type.LEGI.toString() +"," + Type.SPAM.toString() + "}\n");
			bw.write("\n");
			bw.write("@DATA\n");
			for(int id: map.keySet()) {
				Email email = map.get(id);
				//bw.write(id + ",");
				bw.write(email.toString(true,true,true,true) + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public TreeMap<Integer, Email> parseOneType(String InputPath, Type type) {
		//TreeMap<Integer, Email> map = new TreeMap<Integer, Email>();
		int id;
		if(map == null) {
			map = new TreeMap<Integer, Email>();
			id = 1;
		} else {
			id = map.lastKey() + 1;
		}

		BufferedReader br = null;
		try {
			String sCurrentLine;
			InputStream is = new FileInputStream(new File(InputPath));
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			Email email = null;
			State state = State.BLANK;
			StringBuffer sb = new StringBuffer();
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				//System.out.println(state.name());
				if (sCurrentLine.startsWith("</MESSAGE>")) {
					if(email.subject == null) {
						email.subject = "";
					}
					if(email.from == null) {
						email.from = "";
					}
					if(email.embedded == null) {
						email.embedded = "";
					} 
					if(email.body == null) {
						email.body = "";
					}
					email.type = type;
					map.put(id, email);
					System.out.println(id);
					id++;
					state = State.BLANK;
					continue;
				}
				sCurrentLine = sCurrentLine.replaceAll("\\P{Print}", "&char");
				//sCurrentLine = StringEscapeUtils.unescapeJava(sCurrentLine);
				switch(state) {
				case BLANK: {
					if(sCurrentLine.startsWith("<FROM>")) {
						if(sCurrentLine.length() > 14) 
							email.from = sCurrentLine.substring(7,sCurrentLine.length() - 8);
						else 
							email.from = " ";
					} else if(sCurrentLine.startsWith("<SUBJECT>")){
						state = State.SUBJECT;
					} else if (sCurrentLine.startsWith("<MESSAGE_BODY>")) {
						state = State.BODY;
					} /*else if (sCurrentLine.equals("<TEXT_EMBEDDED>")) {
						state = State.EMBEDDED;
					} else if (sCurrentLine.startsWith("</MESSAGE>")) {
						if(email.subject == null) {
							email.subject = "";
						}
						if(email.from == null) {
							email.from = "";
						}
						if(email.embedded == null) {
							email.embedded = "";
						} 
						if(email.body == null) {
							email.body = "";
						}
						email.type = type;
						map.put(id, email);
						System.out.println(id);
						id++;
					}*/ else if (sCurrentLine.startsWith("<MESSAGE>")) {
						email = new Email();
					}
				} break;
				case EMBEDDED: {
					if(!sCurrentLine.isEmpty()) { 
						if(sCurrentLine.charAt(0) == '^') {
							sb.append(sCurrentLine.substring(2));
						}
						else if(sCurrentLine.startsWith("</TEXT_EMBEDDED>")){
							email.embedded= sb.toString();
							state = State.BODY;
						}
					}
				}
				case BODYNORMAL: {
					if(!sCurrentLine.isEmpty()) { 
						if(sCurrentLine.charAt(0) == '^') {
							sb.append(sCurrentLine.substring(2));
						}
						else if(sCurrentLine.startsWith("</TEXT_NORMAL>")){
							email.body = sb.toString();
							state = State.BODY;
						}
					}
				} break;
				case BODY: {
					if(sCurrentLine.startsWith("<TEXT_NORMAL>")){
						sb = new StringBuffer();	
						state = State.BODYNORMAL;
					} else if(sCurrentLine.startsWith("<TEXT_EMBEDDED>")) {
						sb = new StringBuffer();	
						state = State.EMBEDDED;
					} else if(sCurrentLine.startsWith("</MESSAGE_BODY>")) {
						state = State.BLANK;
					}
					
				} break;
				case SUBJECT: {
					if(!sCurrentLine.isEmpty()) {
						if(sCurrentLine.charAt(0) == '^') {
							sb.append(sCurrentLine.substring(2));
						}
						else if(sCurrentLine.startsWith("</SUBJECT>")) {
							email.subject= sb.toString();
							sb = new StringBuffer();
							state = State.BLANK;
						}
					}
				} break;

				default:
					break;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return map;
	}


}
