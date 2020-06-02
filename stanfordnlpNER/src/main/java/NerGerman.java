import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;


import java.util.Properties;
import java.util.stream.Collectors;

public class NerGerman {
	
	public static void main(String[] args){
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
		
		// set up pipeline
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		CoreDocument doc = new CoreDocument("Joe Smith is from Seattle.");
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
}
