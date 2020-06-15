In this class NerGerman, I had made an exemple of detecting NER in German 
for a set of data named dev_data. But there is a problem of 

>>INFO edu.stanford.nlp.parser.lexparser.BiLexPCFGParser - FactoredParser: exceeded MAX_ITEMS work limit [200000 items]; aborting

and it could not treat big size data. I will try to solve this problem this week.

Here I give the result of the `first text` in the data detected by stanfordnlp

text`:
(Condeer.] Petersburg den 18. Dec. Se.russisch- kaiserl. Majestät haben dem Prinzen vonCondé bey dessen Ankunft in Petersburg den St.Andreas-Orden u. den Maltheser Ritterorden in Polen zu ertheilen, und ihn mit einem prächtigen,völlig meublirten Palais in Petersburg zu beschenken geruhet. Das aus 3 Infanterie- und 2 Kavallerie-Regimentern bestehende Corps des Prinzen vonCondé, welches in kaiserliche Dienste genommenworden, ist nun nach Wladimir, Luzk und Kowelin Quartier verlegt. Das ganze Corps wird unterbestandiger Inspection des Prinzen von Condéstehen. Se. kaiserl. Majestät haben ihn zum Chef desadelichen Infanterie-Regiments, und den Duc deBerry zum Chef des adelichen Kavallerie-Regiments ernannt. Als der Prinz in seinen Pallasttrat, fand er daselbst bereits Leute mit seiner Libréevor, auch Carossen mit seinem Wappen. Der Prinzwar in Verlegenheit an welcher Stelle er eigentlichdas Zeichen des St. Andreas-Ordens tragen sollte.Der Kaiser antwortete ihm: Er möchte es mit denInsignien des hl. Geist-Ordens auf derselben Linietragen, und hieng ihm den Orden um.`

`results`

| NER | Type | start | end | context |
| --- | ---  | ---—- | --- | ------- |
| Petersburg | LOCATION| 11 | 21 | Petersburg den 18. Dec |
| Se.russisch | MISC | 35 | 46 | Dec. Se.russisch- kaiserl. Majestät |
| Petersburg | LOCATION | 115 | 125 | y dessen Ankunft in Petersburg den St.Andreas-Orde |
| Maltheser Ritterorden | ORGANIZATION | 154 | 175 | ndreas-Orden u. den Maltheser Ritterorden in Polen zu ertheil |
| Polen | LOCATION | 179 | 184 | eser Ritterorden in Polen zu ertheilen, und |
| Petersburg | LOCATION | 256 | 266 | eublirten Palais in Petersburg zu beschenken geruh |
| Wladimir | PERSON | 440 | 448 | orden, ist nun nach Wladimir, Luzk und Kowelin Q |
| Luzk | LOCATION | 450 | 454 | nun nach Wladimir, Luzk und Kowelin Quartie |
| Duc deBerry | LOCATION | 647 | 658 | Regiments, und den Duc deBerry zum Chef des adelic 
| St. Andreas-Ordens | ORGANIZATION | 911 | 929 | lichdas Zeichen des St. Andreas-Ordens tragen sollte.Der K |

