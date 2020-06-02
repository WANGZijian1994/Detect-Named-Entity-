import edu.stanford.nlp.pipeline.*;

import java.util.Properties;
import java.util.stream.Collectors;

public class TestNERGerman {
	public void test() throws Exception{
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("./StanfordCoreNLP-german.properties"));
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		CoreDocument doc = new CoreDocument("(Condeer.] Petersburg den 18. Dec. Se.russisch- kaiserl. Majestät haben dem Prinzen vonCondé bey dessen Ankunft in Petersburg den St.Andreas-Orden u. den Maltheser Ritterorden in Po¬len zu ertheilen, und ihn mit einem prächtigen,völlig meublirten Palais in Petersburg zu beschen¬ken geruhet.");
		pipeline.annotate(doc);
		
		System.out.println("----");
		System.out.println("entities found");
		if(doc.entityMentions()!=null){
			for(CoreEntityMention em : doc.entityMentions()){
				System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
			}
			System.out.println("---");
			System.out.println("tokens and ner tags");
			String tokenAndNERTags = doc.tokens().stream().map(token -> "("+token.word()+","+token.ner()+token.beginPosition()+token.endPosition()+")").collect(Collectors.joining(" "));
			System.out.println(tokenAndNERTags);
		}
		else{
			System.out.println(doc.entityMentions());
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		TestNERGerman test = new TestNERGerman();
		test.test();
	}
}
