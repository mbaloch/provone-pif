# provone-pif
ProvONE base Provenance Interoperability Framework

### 1. Setup APACHE ODE
Download Apache ODE from http://ode.apache.org/getting-ode.html .
Change the settings to Use the Hibernate instead of JPA.  
### 2. Setup Taverna Server
Easiest way to setup a Taverna Server is to use a Taverna Server Docker Image such as https://hub.docker.com/r/taverna/taverna-server/
### 3. Setup provone-pif
Build the project and deploy the provone-provenance.war of provone-provenance module to Wildfly Application Server.
Make appropirate settings changes in provenance.properties and copy the file to Wildfly configuration folder.
Start the Wildfly Application Server

