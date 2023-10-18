U folderu dokumentacija se nalaze opisi svih interface-a.

U okviru JAR fajla se nalaze interface-i koje je potrebno implementirati i javni test. Ovaj JAR fajl je potrebno importovati u projekat.

JAR se moze raspakovati radi lakseg testiranja. U tom slučaju je potrebno dodati sve klase koje se nalaze u okviru JAR fajla i sledeće dve biblioteke koje su potrebne za ispravan rad testova:
	1. hamcrest-core-1.3.jar
	2. junit-4.12.jar

StudentMain.java je primer klase koja poziva izvršavanje testova. Potrebno je da svaki projekat ima ovakvu klasu koja će sadržati reference na klase sa implementiranim interface-ima.