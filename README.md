# LibZ-Port
Unofficial Port of LibZ Library Mod


LibZ is a library for a few mods made by Globox_Z.

Installation
LibZ is a library built for the Fabric Loader. It requires Fabric API and Cloth Config API to be installed separately; all other dependencies are installed with the mod.

License
LibZ is licensed under MIT.

For Mod Developers
Bring in the library as a dependency:

Be sure to add a maven like the modrinth maven to your build.gradle:
```
repositories {
    maven { url "https://api.modrinth.com/maven" }
}

dependencies {
    modApi ("maven.modrinth:libz:${libz_version}") {
		exclude(group: "net.fabricmc.fabric-api")
	}
}
```
Set the required version for libz in the gradle.properties:
```
    libz_version=...
```
For the required version check out the versions tab on Modrinth.
