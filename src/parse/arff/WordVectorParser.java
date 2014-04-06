package parse.arff;

import java.io.File;

import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WordVectorParser {
	public static void main(String[] args) {
		
		String inputFolder = "/home/mz342/github/L101/weka/fulltext/";
		String outputFolder = "/home/mz342/github/L101/weka/fulltextcustomtokenizers/";
		if(!new File(outputFolder).exists()) new File(outputFolder).mkdir();
		transferFile(inputFolder,outputFolder,"training.arff" ,"vectortraining.arff","testVectorbaseontraining.arff");
		transferFile(inputFolder,outputFolder,"adapt.arff" ,"vectoradapt.arff","testVectorbaseonadapt.arff");
		transferFile(inputFolder,outputFolder,"mixture.arff" ,"vectormixture.arff","testVectorbaseonmixture.arff");
		//transferFile(rootFolder,"adapt.arff","vectoradapt.arff");
		//transferFile(rootFolder,"testing.arff" ,"vectortesting.arff");
/*		String[] argv =  {
				"-b",
		"-i",rootFolder + "vectortraining.arff"
		,"-o", rootFolder +"std_vectortraining.arff"
		,"-r",rootFolder + "vectortesting.arff"
		,"-s",rootFolder + "std_vectortesting.arff"
		};

		Standardize.main(argv);*/
	}
	
public static void transferFile(String inputFolder,String outputFolder, String inputFileName, String outputFileName, String outputFileTestName) {
		
		String[] argv =  {
		"-b",
		"-i",inputFolder + inputFileName
		,"-o", outputFolder + "temp" + inputFileName
		,"-r",inputFolder + "testing.arff"
		,"-s", outputFolder + "temptesting.arff"
		,"-R","1"
		,"-W","1000"
		,"-prune-rate","-1.0"
		,"-N","0"
		,"-L"
		,"-stemmer","weka.core.stemmers.NullStemmer"
		,"-M","1"
		,"-P","##from##"
		,"-tokenizer","weka.core.tokenizers.WordTokenizer -delimiters \" \" "
		,"-C"
		};
		StringToWordVector.main(argv);

/*		argv = new String[] {
				"-b",
				"-i",rootFolder + "temp" + inputFileName
				,"-o", rootFolder + "temp2" + inputFileName
				,"-r",rootFolder + "temptesting.arff"
				,"-s",rootFolder + "temp2testing.arff"
				,"-R","1"
				,"-W","5000"
				,"-prune-rate","-1.0"
				,"-N","0"
				,"-L"
				,"-stemmer","weka.core.stemmers.NullStemmer"
				,"-M","1"
				//,"-P","##subject##"
				,"-C"
				};
		StringToWordVector.main(argv);
		argv = new String[] {
				"-b",
				"-i",rootFolder + "temp2" + inputFileName
				,"-o", rootFolder + "temp3" + inputFileName
				,"-r",rootFolder + "temp2testing.arff"
				,"-s",rootFolder + "temp3testing.arff"
				,"-R","1"
				,"-W","5000"
				,"-prune-rate","-1.0"
				,"-N","0"
				,"-L"
				,"-stemmer","weka.core.stemmers.NullStemmer"
				,"-M","1"
				//,"-P","##body##"
				,"-C"
				};
		StringToWordVector.main(argv);
		argv = new String[] {
				"-b",
				"-i",rootFolder + "temp3" + inputFileName
				,"-o", rootFolder + outputFileName
				,"-r",rootFolder + "temp3testing.arff"
				,"-s",rootFolder + outputFileTestName
				,"-R","1"
				,"-W","5000"
				,"-prune-rate","-1.0"
				,"-N","0"
				,"-L"
				,"-stemmer","weka.core.stemmers.NullStemmer"
				,"-M","1"
				//,"-P","##embedded##"
				,"-C"
				};
		StringToWordVector.main(argv);*/
		argv = new String[] {
				"-b",
				"-i",outputFolder + "temp" + inputFileName
				,"-o", outputFolder + outputFileName
				,"-r",outputFolder + "temptesting.arff"
				,"-s",outputFolder + outputFileTestName
				,"-R","1-3"
				,"-W","5000"
				,"-prune-rate","-1.0"
				,"-N","0"
				,"-L"
				,"-stemmer","weka.core.stemmers.NullStemmer"
				,"-M","1"
				,"-tokenizer","weka.core.tokenizers.WordTokenizer -delimiters \" \\r\\n\\t.,;:\\\'\\\"()?{}!\""
				,"-P","##word##"
				,"-C"
				};

		StringToWordVector.main(argv);
		new File(outputFolder + "temp" + inputFileName).delete();
		new File(outputFolder + "temp2" + inputFileName).delete();
		new File(outputFolder + "temp3" + inputFileName).delete();
		new File(outputFolder + "temptesting.arff").delete();
		new File(outputFolder + "temp2testing.arff").delete();
		new File(outputFolder + "temp3testing.arff").delete();
}
	
	public static void transferFile(String rootFolder, String inputFileName, String outputFileName) {
		transferFile(rootFolder, rootFolder,inputFileName, outputFileName, "vectortesting.arff");
	}
}
