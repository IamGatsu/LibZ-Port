# LibZ
LibZ is a library for a few mods made by Globox_Z.

### Installation
LibZ is a library built for the [Fabric Loader](https://fabricmc.net/). It requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately; all other dependencies are installed with the mod.

### License
LibZ is licensed under MIT.

### For Mod Developers
Bring in the library as a dependency:

Be sure to add a maven like the modrinth maven to your `build.gradle`:
```groovy
repositories {
    maven { url "https://api.modrinth.com/maven" }
}

dependencies {
    modApi ("maven.modrinth:libz:${libz_version}") {
		exclude(group: "net.fabricmc.fabric-api")
	}
}
```

Set the required version for libz in the `gradle.properties`:
```
    libz_version=...
```

For the required version check out the [versions](https://modrinth.com/mod/libz/versions) tab on Modrinth.
## Port-Hinweis (26.2)

Dieses Projekt wurde von Minecraft 1.21.1 (Yarn) auf Minecraft 26.2 (offizielle Mojang-Mappings, kein Yarn mehr, Loom non-remap, Java 25) portiert.

**Vor dem Bauen unbedingt prüfen:**
- `cloth-config-fabric` (26.2.155), `modmenu` (20.0.1) und `RoughlyEnoughItems-api-fabric` (26.2.820) wurden anhand bestätigter Modrinth/CurseForge-Releases für 26.2 gesetzt – bitte trotzdem gegen die neuesten Versionen prüfen.
- **EMI hat (Stand jetzt) keinen 26.2-Build veröffentlicht** (letzte EMI-Version unterstützt nur bis 1.21.1). Die `emi-fabric`-Abhängigkeit sowie `compat/LibzEmiPlugin.java` und der `"emi"`-Entrypoint in `fabric.mod.json` wurden daher aus diesem Port entfernt, damit der Build nicht mehr daran scheitert. Sobald EMI eine 26.2-Version veröffentlicht, können beide wieder ergänzt werden (Git-History/vorherige Version des Projekts als Vorlage nutzen).
- `IntegratedServerLoaderMixin` zielt auf `net.minecraft.server.integrated.IntegratedServerLoader` – der Klassenname konnte für 26.2 nicht einzeln verifiziert werden (zuletzt für 1.21.11/25w42a bestätigt).
- Die `GuiGraphics`-Aufrufe in `DrawTabHelper` (`blit`, `renderTooltip`, `renderItem`) nutzen die zuletzt bekannte Mojang-API-Form; diese Methode hat sich in den letzten MC-Versionen mehrfach geändert (u. a. zusätzliche Textur-/RenderType-Parameter) und sollte gegen das tatsächliche 26.2-Mappings-Jar geprüft werden.
- Es konnte in dieser Umgebung nicht gegen die echten 26.2-Artefakte kompiliert werden (kein Netzwerkzugriff für Gradle). Bitte `./gradlew build` lokal ausführen und gemeldete Fehler mit den obigen Punkten abgleichen.
