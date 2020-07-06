import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.StringUtils;
import java.io.File;
import java.io.File;
import java.io.IOException;
import java.io.*;
import edu.stanford.nlp.util.Triple;

import javax.jws.WebParam;
import java.io.FileWriter;
import java.util.Properties;



public class Model_Training{
	
	public void trainAndWrite(String modelOutPath,String properties,String trainingFilepath){
		Properties props = StringUtils.propFileToProperties(properties);
		props.setProperty("serializeTo",modelOutPath);
		
		if(trainingFilepath!=null){
			props.setProperty("trainFile",trainingFilepath);
		}
		
		SeqClassifierFlags flags = new SeqClassifierFlags(props);
		
		CRFClassifier<CoreLabel>crf = new CRFClassifier<>(flags);
		crf.train();
		crf.serializeClassifier((modelOutPath));
	}
	
	public CRFClassifier getModel(String modelPath){
		return CRFClassifier.getClassifierNoExceptions(modelPath);
	}
	
	public static void main(String[] args)throws IOException{
		String propsFile="/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/StanfordCoreNLP/Existing Models in StanfordCoreNLP/src/main/resources/TrainingModel.properties";
		String trainfile = "/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/StanfordCoreNLP/Existing Models in StanfordCoreNLP/TRAIN.txt";
		String modelLoc = "/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/StanfordCoreNLP/NewModels/stanfordCRF.ner.german.model.ser.gz";
		
		Model_Training tagger = new Model_Training();
		//tagger.trainAndWrite(modelLoc,propsFile,trainfile);
		
		CRFClassifier model = tagger.getModel(modelLoc);
		Triple<Double,Double,Double> scores = model.classifyAndWriteAnswers("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/StanfordCoreNLP/Existing Models in StanfordCoreNLP/DEV.txt",true);
		System.out.println(scores);
	}
}
