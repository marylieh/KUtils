pluginManagement {
	plugins {
		// Update this in libs.version.toml when you change it here.
		kotlin("jvm") version "1.9.22"
		kotlin("plugin.serialization") version "1.9.22"

		id("com.github.jakemarsden.git-hooks") version "0.0.2"
		id("com.github.johnrengelman.shadow") version "8.1.1"
	}
}

rootProject.name = "KUtils"
