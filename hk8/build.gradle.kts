plugins {
	id("java")
	id("application")
}

group = "in.hridaykh"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	compileOnly("org.projectlombok:lombok:1.18.48")
	annotationProcessor("org.projectlombok:lombok:1.18.48")

	implementation("org.slf4j:slf4j-api:2.0.16")

	implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.test {
	useJUnitPlatform()
}
application {
	mainClass = "hk8.Main"
}