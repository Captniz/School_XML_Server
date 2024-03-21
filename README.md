# Progetto server XML -> JAVA -> HTML per streaming live

## Descrizione

Il progetto consiste in un server JAVA che riceve un file XML, poi lo invia ad un client JAVA che lo invia ad una pagina HTML. La pagina HTML riceve i dati e visualizza in tempo reale una live streaming.

![alt text](Scheme.svg)

## Todo

- [x] RTMP
  - [x] Creare il server RTMP
  - [x] Setup del server RTMP
    - [x] Setup del server RTMP per trasmissione in HDL
- [ ] HTML/JS
  - [x] CLIENT JAVA
    - [x] Connessione con XML server
    - [x] Connessione con JS WebSockets
  - [ ] HTML
    - [ ] Rimpiazza le immagini
    - [ ] Tasto BACK che torna ai gruppi
  - [x] CSS
  - [ ] JS
    - [ ] Quando chiamo SHOWSTREAM fai si che il server update del numero di user live
- [x] JAVA SERVER
  - [x] XML + MANAGER
    - [x] Creare XML
      - [x] Parsing info XML a RESPONSE
    - [x] Creare XSD
  - [x] SERVER MAIN
    - [x] Connessione e accettazione del client
  - [x] CLIENT MANAGER
    - [x] Gestione singolo client
- [ ] Documentazione
  - [ ] Documenta
  - [ ] Dividi in sezioni
  - [ ] Semplifica
  - [ ] Finisci README

## Possibili agginunte

 - [ ] Chat
 - [ ] Trova un modo per utilizzare il client java
 - [ ] Crea un hub per la gestione degli streamer
 - [ ] Live viewers & sub count

## Risorse

- GUARDA CHATGPT E LE TABELLE SU GOOGLE
- [XML save and modify](https://chat.openai.com/share/4e6a0dce-1e5c-4150-811b-1b1d60c8bf90)
- [Sockets JS to JAVA](https://chat.openai.com/share/1c773867-ed35-49d9-969e-fa1a7afa8635)

## Consegna

Cose da fare per il progetto:
1. Struttura Server-Multiclient TCP in cui il client può collegarsi al server per richiedere
un servizio. Questo servizio potrebbe essere una raccolta di dati particolari, tipo
archivio libri, documenti aziendali, videoteche, oppure iscrizioni a corsi online ecc.
1. Ci deve essere un file XML con all’interno dei dati. Il documento deve avere le
conseguenti caratteristiche:
a. almeno 3 livelli oltre l’elemento radice,
b. almeno una restrizione per un tipo di elemento
c. almeno un elemento deve avere un attributo
1. Ci deve comunque essere un file XSD che valida il file XML.
2. Il client ha la possibilità di richiedere la visualizzazione di un tipo di elemento del file
XML al server (Es: chiedo l’elemento titolo e il server risponde con tutti i titoli dei
libri/album). Il server creerà un nuovo file XML con elemento radice = <richiesta>,
che avrà come figli i contenuti dell’elemento richiesto.

```xml
  (Es
  <richiesta>:
  <titolo>ABC</titolo>
  <titolo>DCE</titolo>
  ……
  </richiesta>
```

5. Il client può aggiungere dati alla raccolta del file XML (Es: aggiungo un nuovo libro),
che dovrà comunque rispettare la struttura e le eventuali restrizioni dettate dallo
schema. Il server dovrà poi salvare il documento modificato.
Sarà poi necessario svolgere una presentazione di 15 minuti per spiegare il lavoro fatto.


La valutazione sarà fatta su:
- Completamento delle richieste
- Chiarezza del codice
- Chiarezza della presentazione ed esposizione
- Analisi delle criticità emerse sull’applicazione e/o sul lavoro personale
- Aggiunta di funzionalità o personalizzazioni legate al contesto scelto non richieste
dalla consegna