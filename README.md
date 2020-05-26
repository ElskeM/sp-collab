# OrderLagerFaktureringService
Du behöver wildfly samt derby installerat på din dator och använda Eclpise. Wildfly behöver konfigureras att ha datasourcen OlfDb.


## Bygginstruktioner för servern
1. Klona repositoryt
2. Kontrollera att sökvägen till wildfly i build.xml stämmer med din dator. Kontrollera också       att sökvägen till server-dist propertyn pekar på deployments-katalogen i wildfly.
3. Bygg projektet
4. Starta wildfly och Derby

## Bygginstruktioner för klienten
1. Klona [Länk till klient-repo]. Lägg olfdbClasses.jar som återfinns i servicens dist-katalog till klientens build path

### Felsökning
1. Kontrollera att derby-servern körs
2. Kontrollera att Wildfly körs
