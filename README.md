# OrderLagerFaktureringService
Du behöver wildfly samt derby installerat på din dator och använda Eclipse. Wildfly behöver konfigureras att ha datasourcen OlfDb.

## Bygginstruktioner för servern
1. Klona repositoryt och importera projektet i Eclipse
2. Kontrollera att sökvägen till wildfly i build.xml stämmer med din dator. Kontrollera också       att sökvägen till server-dist propertyn pekar på deployments-katalogen i wildfly.
3. Bygg projektet med Ant
4. Lägg till datasource i standalone.xml. Se rekommenderad datasource under rubriken standalone.xml nedan.
5. Starta Derby och Wildfly

## Bygginstruktioner för klienten
1. Klona https://github.com/ElskeM/sp-collab-client.git. 
2. Importera projektet i Eclipse
3. Lägg olfdbClasses.jar som återfinns i servicens dist-katalog till klientens build path

## Bygginstruktioner för webserviceklienten
1. Klona https://github.com/ElskeM/sp-c ollab-webclient.git. 
2. Bygg projektet med Node och Vue-Cli (först `npm install`, sedan `npm run serve`)

### Felsökning
1. Kontrollera att derby-servern körs
2. Kontrollera att Wildfly körs

### standalone.xml
```xml
<datasource jndi-name="java:/OlfDb" pool-name="OlfDb" enabled="true" use-java-context="true">
	<connection-url>jdbc:derby://localhost:50000/OlfDb; create=true</connection-url>
	<driver>derbyclient.jar</driver>
	<pool>
		<min-pool-size>8</min-pool-size>
		<initial-pool-size>8</initial-pool-size>
		<max-pool-size>32</max-pool-size>
	</pool>
	<security>
		<user-name>APP</user-name>
		<password>APP</password>
	</security>
	<validation>
		<check-valid-connection-sql>values 1</check-valid-connection-sql>
		<validate-on-match>false</validate-on-match>
		<background-validation>true</background-validation>
		<background-validation-millis>10000</background-validation-millis>
	</validation>
</datasource>
```
### OlfDb
OlfDatabasen kan tömmas och fyllas med testdata genom att köra `createOlfDb.sql` i Derby.
Exempelkommandon:
1. `cp createOlfDb.sql <derby_bin_folder>`
2. `./ij createOlfDb.sql`