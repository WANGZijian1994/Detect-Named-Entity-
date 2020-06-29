We know that StanfordNLP adopts the Model CRF(Conditional Random Field) to detect the Named Entities. I have tested with my data (de_train.json)[https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/data/novo_train_de.json] and (de_dev.json)[https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/data/novo_dev_de.json] The global results:

| Precision | Recall | F-Score |
| --- | --- | --- |
| 51% | 55.6% | 53.2% |

Some points:

1. StanfordNLP German has 4 types of labels: LOCATION,ORGANIZATION,PERSON,MISC  

2. StanfordNLP NER CRF Model prefers to note the last words of a syntagme, for example, "Passage de Marigny", it will note "de Marigny" as NER but not "Passage".

3. It has better performance in detecting "LOCATION" than in detecting "PERSON", the last one has more label errors when detecting NER.

