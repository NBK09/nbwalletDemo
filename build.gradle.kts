plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    id("io.qameta.allure") version "2.9.4"
    id("org.gradle.test-retry") version "1.6.2"
}
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "com.nbwallet"
version = "1.0-SNAPSHOT"

allure {
    report {
        version.set("2.25.0")
    }
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.25.0")
            }
        }
    }
}
repositories {
    mavenCentral()
}
val lombokVersion = "1.18.34"
val junitVersion = "5.10.0"
val restAssuredVersion = "5.5.6"
val jacksonVersion = "2.20.0"
val ownerVersion = "1.0.9"
val assertjVersion = "3.22.0"
val slf4jVersion = "2.0.17"
val log4jVersion = "2.23.1"
val allureVersion = "2.30.0"
val aspectjVersion = "1.9.22"
val fakerVersion = "1.0.2"
val selenideVersion = "7.8.1"
val webDriverManagerVersion = "6.1.0"
val postgresqlVersion = "42.7.8"
val hibernateVersion = "7.1.3.Final"
val hikariCPVersion = "7.0.2"

dependencies {
    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // JUnit 5
    implementation(platform("org.junit:junit-bom:$junitVersion"))
    implementation("org.junit.jupiter:junit-jupiter")

    // REST Assured
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // Owner
    implementation("org.aeonbits.owner:owner:$ownerVersion")

    // AssertJ
    implementation("org.assertj:assertj-core:$assertjVersion")

    // Логирование
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")

    // Allure
    implementation("io.qameta.allure:allure-junit5:$allureVersion")
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")

    // AspectJ
    implementation("org.aspectj:aspectjtools:$aspectjVersion")
    implementation("org.aspectj:aspectjweaver:$aspectjVersion")

    // Faker
    implementation("com.github.javafaker:javafaker:$fakerVersion")

    // Selenide + Selenoid
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-selenoid:$selenideVersion")

    // WebDriverManager
    implementation("io.github.bonigarcia:webdrivermanager:$webDriverManagerVersion")

    // PostgreSQL + Hibernate
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")

    //pool connections
    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
}


tasks.test {
    useJUnitPlatform()
}

// Отдельная задача для запуска только тестов с @Tag("Smoke")
tasks.register<Test>("smokeTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Smoke\")"
    useJUnitPlatform {
        includeTags("Smoke")
    }
}

    tasks.register<Test>("E2E") {
        group = "verification"
        description = "Runs tests tagged with @Tag(\"E2E\")"
        useJUnitPlatform {
            includeTags("E2E")
        }
    }
