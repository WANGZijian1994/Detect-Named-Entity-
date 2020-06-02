*** This project works with `NER (Named Entity Recognition)` in `German`. 

de_train.json and de_dev.json are the annoted data. And I will try every NLP tools to detect Named Entity in German.

First I have used [Spacy German Model](https://spacy.io/models/de). But the result of the precision of these models is not satisfactory for my data.

After using spacy retrain API, I have updated the German Language Model(https://github.com/WANGZijian1994/Detect-Named-Entity-/tree/master/spacyNER/RetrainModels) and test this model with dev data. 

`The result`

* Precision：70.90%
- Recall: 59.10%
* F-Call: 64.42%

At the same time, I have tried StanfordCoreNLP for detecting Named Entities with the same data, and it works(https://github.com/WANGZijian1994/Detect-Named-Entity-/blob/master/stanfordnlpNER/src/test/java/TestNERGerman.java). I will add results later. 

