### This project works with `NER (Named Entity Recognition)` in `German`. 

de_train.json and de_dev.json are the annoted data. And I will try every NLP tools to detect Named Entity in German.

First I have used [Spacy German Model](https://spacy.io/models/de). But the result of the precision of these models is not satisfactory for my data.

After using spacy retrain API, I have updated the German Language Model(https://github.com/WANGZijian1994/Detect-Named-Entity-/tree/master/spacyNER/RetrainModels) and test this model with dev data. 

I have followed the same steps when I worked with StanfordCoreNLP

`The result`

| Items | spaCy Create | spaCy sm | spaCy md | StanfordCoreNLP Create |
| --- | --- | --- | --- | --- |
| Precision | 64.07% | 71.70% | 78.18% | 62.08% |
| Recall | 53.8% | 57.58% | 65.15% | 54.01% |
| F-Call | 58.89 | 63.87 | 71.07% | 57.83 |


### Comparaison of different results : intersection of 2 different tools, Complementary of these different tools
spaCy sm vs spaCy md

![image](https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/Visualisation/Spacy_sm_Spacy_md.png)

spaCy Create vs StandfordCoreNLP

![image](https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/Visualisation/Stanford_SpaCy.png)

### Comparaison of different results and intersection of more than 2 different tools, and Complementary of these different tools

spaCy sm | spaCy md | spaCy Create VP ORG

![image](https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/Visualisation/ORG.png)

spaCy sm | spaCy md | StanfordCoreNLP VP PER

![image](https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/Visualisation/Quadr%C3%B4me.png)
