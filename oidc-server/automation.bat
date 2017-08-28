@echo off

if %1 == 1 (

call mvn clean package

rename C:\Users\GOXR3PLUS\Desktop\GitHub\OpenConext-oidc\oidc-server\target\oidc-server.war oidc.war
pscp -i "C:/Users/GOXR3PLUS/.ssh/putty private key.ppk"  "C:\Users\GOXR3PLUS\Desktop\GitHub\OpenConext-oidc\oidc-server\target\oidc.war" root@snf-761523.vm.okeanos.grnet.gr:/var/lib/tomcat8/webapps/

)else (

pscp -i "C:/Users/GOXR3PLUS/.ssh/putty private key.ppk"  "C:\Users\GOXR3PLUS\Desktop\GitHub\OpenConext-oidc\oidc-server\target\oidc.war" root@snf-761523.vm.okeanos.grnet.gr:/var/lib/tomcat8/webapps/

)