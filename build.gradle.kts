import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	kotlin("plugin.allopen") version "1.8.22"
	kotlin("plugin.noarg") version "1.8.22"
}

group = "alvarez.fernando"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws-parameter-store-config
	implementation("org.springframework.cloud:spring-cloud-starter-aws-parameter-store-config:2.2.6.RELEASE")
	// https://mvnrepository.com/artifact/io.awspring.cloud/spring-cloud-starter-aws-secrets-manager-config
	implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:2.4.4")
	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap
	// https://stackoverflow.com/questions/66807577/access-aws-paramstore-from-springboot-app
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.0.4")
	runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE")
	runtimeOnly("io.r2dbc:r2dbc-pool")
	runtimeOnly("org.postgresql:postgresql") {
		because("Flyway needs the blocking JDBC driver to work; it doesn't work with R2DBC out of the box: https://www.baeldung.com/spring-r2dbc-flyway")
	}
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}