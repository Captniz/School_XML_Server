# Progetto server per streaming live ( Twitch Clone )
>[!CAUTION] Questo progetto non ha alcun senso logico o pratico ed è esclusivamente a scopo didattico.
## Table of contents 
- [Progetto server per streaming live ( Twitch Clone )](#progetto-server-per-streaming-live--twitch-clone-)
  - [Table of contents](#table-of-contents)
  - [Descrizione](#descrizione)
  - [Schema](#schema)
    - [Schema progetto](#schema-progetto)
    - [XML](#xml)
  - [Todo](#todo)
  - [Possibili agginunte](#possibili-agginunte)
  - [Risorse](#risorse)
  - [Criticità](#criticità)
  - [Consegna](#consegna)

## Descrizione

> Il progetto consiste in un **server JAVA principale** ( *ServerMain* ) che accetta richieste di connessione e le gestisce attraverso un **worker** ( *ClientManager* ). 
> Il worker a sua volta risponde alle richieste di un singolo client connesso e ottiene i dati attraverso un **gestore del file XML** ( *XMLManager* ).
>
> Chi si connette al server JAVA quindi? Un altro programma in JAVA che per soddisfare le richieste della consegna e al tempo stesso essere utile nel mio scopo ha una duplice funzione: ottiene le informazioni dal server JAVA e le **traduce in protocollo websocket per JS**; in questo modo posso mandare dati in tempo reale alla pagina html.  
>**Normalmente questo intramezzo sarebbe inutile e il Client JS si sostituirebbe al Client JAVA.**  
>  
> Infine attraverso i dati raccolti si viene a formattare la pagina HTML che è la **GUI finale dell'utente.**  
>  
> Come arriviamo alla streaming live quindi? Attraverso l'indirizzo della streaming nei dati che mandiamo, la pagina HTML si connette al **server RTMP/HLS che converte una streaming in entrata in una playlist HLS disponibile sulla rete**  

## Schema

### Schema progetto  

![alt text](Scheme.svg)

### XML

```xml
<?xml version="1.0" encoding="UTF-8"?>

<streaming>
    <group genre="Gaming">
        <imagePath>../XML_Server/src/groups/gr1.PNG</imagePath>
        <followers>120.000</followers>
        <streamers>
            <streamer name="s1">
                <content>Webcam</content>
                <followers>20.000</followers>
                <language>Italian</language>
                <metadata>
                    <streamIp>http://localhost:8080/hls/simo.m3u8</streamIp>
                    <imagePath>../XML_Server/src/streamers/str1.PNG</imagePath>
                </metadata>
            </streamer>
            <streamer name="s2">
                <name>Streamer2</name>
                <content>Screen</content>
                <followers>30.000</followers>
                <language>English</language>
                <metadata>
                    <streamIp>http://localhost:8080/hls/simo.m3u8</streamIp>
                    <imagePath>../XML_Server/src/streamers/str2.PNG</imagePath>
                </metadata>
            </streamer>
        </streamers>
    </group>
    <group genre="Chatting">
        <imagePath>../XML_Server/src/groups/gr2.PNG</imagePath>
        <followers>20.000</followers>
        <streamers>
            <streamer name="s3">
                <name>Streamer3</name>
                <content>Webcam</content>
                <followers>10.000</followers>
                <language>English</language>
                <metadata>
                    <streamIp>http://localhost:8080/hls/simo.m3u8</streamIp>
                    <imagePath>../XML_Server/src/streamers/str3.PNG</imagePath>
                </metadata>
            </streamer>
            <streamer name="s4">
                <name>Streamer4</name>
                <content>Italian</content>
                <followers>5.000</followers>
                <language>English</language>
                <metadata>
                    <streamIp>http://localhost:8080/hls/simo.m3u8</streamIp>
                    <imagePath>../XML_Server/src/streamers/str4.PNG</imagePath>
                </metadata>
            </streamer>
        </streamers>
    </group>
</streaming>
```

## Todo

- [x] RTMP
  - [x] Creare il server RTMP
  - [x] Setup del server RTMP
    - [x] Setup del server RTMP per trasmissione in HDL
- [ ] HTML/JS
  - [x] CLIENT JAVA
    - [x] Connessione con XML server
    - [x] Connessione con JS WebSockets
  - [x] HTML
    - [x] Rimpiazza le immagini
    - [x] Tasto BACK che torna ai gruppi
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
  - [x] Documenta
  - [x] Dividi in sezioni
  - [ ] Semplifica
  - [x] Finisci README

## Possibili agginunte

 - [ ] Chat
 - [ ] Trova un modo per utilizzare il client java
 - [ ] Crea un hub per la gestione degli streamer
 - [ ] Live viewers & sub count

## Risorse

- [RTMP/HLS Server](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwi0ptKJi4WFAxUlgP0HHcb0DCcQwqsBegQIERAG&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DgDSbQvmEmLY&usg=AOvVaw0IFU25pYEGYCmaDM17EOXW&opi=89978449)
- [XML save and modify](https://chat.openai.com/share/4e6a0dce-1e5c-4150-811b-1b1d60c8bf90)
- [Sockets JS to JAVA](https://chat.openai.com/share/1c773867-ed35-49d9-969e-fa1a7afa8635)

## Criticità

- Comprensione capacità di protocolli come HLS e RTMP
- Importazione librerie di java
- Javascript, in generale
- Trasmissione di file attraverso le socket a causa di problemi specifici

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