import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;


import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;



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
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(address),"UTF-8"))){
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
			int i = Integer.parseInt(x.get("start").toString());
			int j = Integer.parseInt(x.get("end").toString());
			each.put("ner",x.get("ner").toString());
			each.put("start",x.get("start").toString());
			each.put("end",x.get("end").toString());
			each.put("label",x.get("label").toString());
			each.put("context",contexte(text,i,j));
			chacun.add(each);
		}
		item.setLabels(new ArrayList(chacun));
		annots.add(item);
	}
	
	public String contexte(String text,int left,int right){
		int count = 0;
		if(left>0)left--;
		while(count < 5 && left >= 0){
			left--;
			if(left<0){break;}
			if(text.charAt(left)==' '){
				count++;
			}
		}
		if(right<text.length()-1) right++;
		count = 0;
		while(count < 5 && right < text.length()){
			right++;
			if(right==text.length()){break;}
			if(text.charAt(right)==' '){
				count++;
			}
		}
		return text.substring(left+1,right);
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
			//System.out.println(text);
			for(CoreEntityMention em : doc.entityMentions()){
				Map<String,String>each = new HashMap<>();
				each.put("ner",em.text());
				each.put("label",em.entityType());
				each.put("start",em.charOffsets().first.toString());
				each.put("end",em.charOffsets().second.toString());
				each.put("context",contexte(text,em.charOffsets().first,em.charOffsets().second));
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
		n.readData("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/data/novo_train_de_for_java.json");
		n.readData("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/data/novo_dev_de_for_java.json");
		System.out.println(n.annots.size());
		System.out.println();
		JSONObject mT = new JSONObject();
		for(int i = 0;i < n.annots.size();i++){
			mT.put(i+1,n.annots.get(i).getLabels());
		}
		try(BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/spacyNER//corrections.json"), StandardCharsets.UTF_8))){
			file.write(mT.toJSONString());
			file.flush();
		}catch(IOException e){e.printStackTrace();}
		
		n.detectNERStanford();
		
		mT = new JSONObject();
		for(int i = 0;i < n.results.size();i++){
			mT.put(i+1,n.results.get(i).getLabels());
		}
		System.out.println(mT.size());
		
		try(BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/zijian/ZijianStageEntiteNommee/Detect-Named-Entity-/spacyNER//stanfordnlpRes.json"), StandardCharsets.UTF_8))){
			file.write(mT.toJSONString());
			file.flush();
		}catch(IOException e){e.printStackTrace();}
	}
}
