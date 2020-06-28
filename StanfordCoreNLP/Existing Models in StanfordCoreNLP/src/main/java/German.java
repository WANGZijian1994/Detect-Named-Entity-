import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class German {
	
	/*
		'text': "..."
		'labels': [{'ner': 'Petersburg', 'start': 11, 'end': 21, 'label': 'LOC'},{'ner': 'Prinzen', 'start': 76, 'end': 83, 'label': 'PERS'}]
	*/
	
	@JsonProperty
	private String text;
	@JsonProperty
	private List<Map<String,String>> labels;
	
	//private Map<String,String>each;
	private String ner;
	private String label;
	private String context;
	
	private int start;
	private int end;
	
	
	public German(){}
	
	public German(String text,List<Map<String,String>> labels){
		this.text = text;
		this.labels = labels;
	}
	
	@Override
	public String toString(){
		String res = text + labels.toString();
		return res;
	}
}
