#java postgresql
[http://www.postgresqltutorial.com/postgresql-jdbc/]
 
#pour wamp
[http://jc.etiemble.free.fr/abc/index.php/realisations/trucs-astuces/postgresql-wamp]

#récupération de postgresql
[http://www.postgresql.org/download/windows]

#ajout du connecteur jdbc
[https://jdbc.postgresql.org/download.html]


#initialisation database : ajout des scripts 
*	dans pgAdmin : 
	>RC>create database : rtm_01_adm
	>RC> query tools create.sql
	>RC> query tools insert.sql
	>ajout d'un user et des privilèges à la base
	
*	login 
```
jdbc:postgresql://<database_host>:<port>/<database_name>

jdbc:postgresql://localhost:5432/rtm_01_adm
user
user123
```

#utilisation de sql explorer
> bug sur pgAdmin ?


#try catch with resources
[http://stackoverflow.com/questions/10115282/new-strange-java-try-syntax]
[http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html]
