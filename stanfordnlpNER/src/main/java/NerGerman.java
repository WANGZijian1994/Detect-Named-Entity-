import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NerGerman {
	/*
		'text': "..."
		'labels': [{'ner': 'Petersburg', 'start': 11, 'end': 21, 'label': 'LOC'},]
	*/
	
	// Read Annotated data in Json File
	private List<German>annots = new ArrayList<German>();
	private List<German>results = new ArrayList<>();
	
	public void readData(String address){
		JSONParser jsonParser = new JSONParser();
		try(FileReader reader = new FileReader(address)){
			Object obj = jsonParser.parse(reader);
			
			JSONArray annots = (JSONArray) obj;
			for(Object annot:annots){
				JSONObject key = (JSONObject) annot;
				parseAnnots(key);
			}
			//System.out.println(annots.size()+" "+annots.get(0));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ParseException e){e.printStackTrace();}
	}
	
	public void parseAnnots(JSONObject german){
		German item = new German();
		String text = german.get("text").toString();
		item.setText(text);
		List<Map<String,String>>chacun = new ArrayList<>();
		List<JSONObject>labels = (ArrayList)german.get("labels");
		for(JSONObject x:labels){
			Map<String,String>each = new HashMap<>();
			each.put("ner",x.get("ner").toString());
			each.put("start",x.get("start").toString());
			each.put("end",x.get("end").toString());
			each.put("label",x.get("label").toString());
			chacun.add(each);
		}
		item.setLabels(new ArrayList(chacun));
		annots.add(item);
	}
	
	public void detectNERStanford() throws IOException{
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("./StanfordCoreNLP-german.properties"));
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		for(int i = 0;i<annots.size();i++){
			String text = annots.get(i).getText();
			CoreDocument doc = new CoreDocument(text);
			pipeline.annotate(doc);
			German german = new German();
			german.setText(text);
			List<Map<String,String>>chacun = new ArrayList<>();
			System.out.println(text);
			for(CoreEntityMention em : doc.entityMentions()){
				System.out.println(em.text()+" "+em.entityType()+" start : "+em.charOffsets().first+" end : "+em.charOffsets().second);
				if(em.charOffsets().first>20 && em.charOffsets().second+20<text.length()) {
					System.out.println("context : " + text.substring(em.charOffsets().first - 20, em.charOffsets().second + 20));
				}
				Map<String,String>each = new HashMap<>();
				each.put("ner",em.text());
				each.put("label",em.entityType());
				each.put("start",em.charOffsets().first.toString());
				each.put("end",em.charOffsets().second.toString());
				chacun.add(each);
			}
			german.setLabels(chacun);
			results.add(german);
		}
	}
	
	public void demoNER() throws IOException {
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("./StanfordCoreNLP-german.properties"));
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		CoreDocument doc = new CoreDocument("(Condeer.] Petersburg den 18. Dec. Se.russisch- kaiserl. Majestät haben dem Prinzen vonCondé bey dessen Ankunft in Petersburg den St.Andreas-Orden u. den Maltheser Ritterorden in Po¬len zu ertheilen, und ihn mit einem prächtigen,völlig meublirten Palais in Petersburg zu beschen¬ken geruhet.");
		pipeline.annotate(doc);
		
		System.out.println("----");
		System.out.println("entities found");
		for(CoreEntityMention em : doc.entityMentions()){
			System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
		}
		System.out.println("---");
		System.out.println("tokens and ner tags");
		String tokenAndNERTags = doc.tokens().stream().map(token -> "("+token.word()+","+token.ner()+")").collect(Collectors.joining(" "));
		System.out.println(tokenAndNERTags);
	}
	
	
	public static void main(String[] args) throws IOException {
		NerGerman n = new NerGerman();
		//n.demoNER();
		//n.readData("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/data/train_java.json");
		n.readData("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/data/dev_java.json");
		System.out.println(n.annots.size());
		System.out.println(n.annots.get(0).getText()+n.annots.get(0).getLabels());
		n.detectNERStanford();
		System.out.println(n.results.size());
		System.out.println(n.results.get(0).getText()+n.results.get(0).getLabels());
	}
}
