plugins {
    id("java")
}

group = "com.WarhammerFantasy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("libs/HytaleServer.jar"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveVersion.set("1.0.0")
    from("src/main/resources")
}


tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("copyJar") {
    dependsOn(tasks.jar)

    from(tasks.jar.flatMap { it.archiveFile })
    into("/home/jammingend/.var/app/com.hypixel.HytaleLauncher/data/Hytale/UserData/Mods/")
}

tasks.build {
    dependsOn("copyJar")
}
