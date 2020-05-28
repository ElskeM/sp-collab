# OrderLagerFaktureringService
Du behöver wildfly samt derby installerat på din dator och använda Eclipse. Wildfly behöver konfigureras att ha datasourcen OlfDb.


## Bygginstruktioner för servern
1. Klona repositoryt
2. Kontrollera att sökvägen till wildfly i build.xml stämmer med din dator. Kontrollera också       att sökvägen till server-dist propertyn pekar på deployments-katalogen i wildfly.
3. Bygg projektet med Ant
4. Starta Derby och Wildfly

## Bygginstruktioner för klienten
1. Klona https://github.com/ElskeM/sp-collab-client.git. Lägg olfdbClasses.jar som återfinns i servicens dist-katalog till klientens build path

## Bygginstruktioner för webserviceklienten
1. Klona https://github.com/ElskeM/sp-collab-webclient.git. Bygg projektet med Node och Vue-Cli

### Felsökning
1. Kontrollera att derby-servern körs
2. Kontrollera att Wildfly körs
