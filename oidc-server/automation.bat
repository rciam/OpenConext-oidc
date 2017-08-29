call mvn clean package
rename C:\Users\GOXR3PLUS\Documents\OpenConext-oidc\oidc-server\target\oidc-server.war oidc.war
pscp -i "C:/Users/GOXR3PLUS/.ssh/putty private key.ppk"  "C:\Users\GOXR3PLUS\Documents\OpenConext-oidc\oidc-server\target\oidc.war" root@snf-761523.vm.okeanos.grnet.gr:/var/lib/tomcat8/webapps/