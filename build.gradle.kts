import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val springBootVersion = "3.0.6"
    val kotlinVersion = "1.7.22"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version kotlinVersion
    // included allOpen
    kotlin("plugin.spring") version kotlinVersion
    // included noArg
    kotlin("plugin.jpa") version kotlinVersion
}

group = "estert"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // stdlib-jdk8
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // mysql jdbc
    implementation("mysql:mysql-connector-java:8.0.27")
    // gson
    implementation("com.google.code.gson:gson:2.8.9")
    // h2
    runtimeOnly("com.h2database:h2")

    // mockk
    testImplementation("io.mockk:mockk:1.12.0")
    // kotest
    val kotestVersion = "5.4.2"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion") // kotest 를 junit 처럼 사용
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion") // kotest 의 assertion(shouldBe, shouldNotBe 등) 사용
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion") // property based testing
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2") // spring test
    // spring test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test>().configureEach() {
    useJUnitPlatform()
}
