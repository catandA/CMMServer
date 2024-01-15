plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "me.catand"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.0.4")
    implementation("com.google.code.gson:gson:2.8.8")
    compileOnly("org.projectlombok:lombok:18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
