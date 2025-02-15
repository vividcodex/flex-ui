import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.android.application)
}

val sampleVersion = property("flex-ui.sample.version").toString()

kotlin {
	androidTarget {
		compilerOptions {
			jvmTarget = JvmTarget.JVM_21
			languageVersion = KotlinVersion.KOTLIN_2_1
		}
	}
	
	jvm("desktop") {
		compilerOptions {
			jvmTarget = JvmTarget.JVM_21
			languageVersion = KotlinVersion.KOTLIN_2_1
		}
	}
	
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	).forEach { iosTarget ->
		iosTarget.binaries.framework {
			baseName = "FlexUISample"
			isStatic = true
		}
	}
	
	js {
		moduleName = "flexUISample"
		browser {
			commonWebpackConfig {
				outputFileName = "flexUISample.js"
			}
		}
		binaries.executable()
		useEsModules()
	}
	
	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		moduleName = "flexUISample"
		browser {
			val rootDirPath = project.rootDir.path
			val projectDirPath = project.projectDir.path
			commonWebpackConfig {
				outputFileName = "flexUISample.js"
				devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
					static = (static ?: mutableListOf()).apply {
						add(rootDirPath)
						add(projectDirPath)
					}
				}
			}
		}
		binaries.executable()
	}
	
	sourceSets {
		commonMain {
			dependencies {
				implementation(projects.flexUiCore)
				implementation(compose.runtime)
				implementation(compose.foundation)
				implementation(compose.material3)
				implementation(compose.ui)
				implementation(compose.components.resources)
				implementation(compose.materialIconsExtended)
			}
			kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
		}
		androidMain {
			dependencies {
				implementation(compose.preview)
				implementation(libs.androidx.activity.compose)
			}
		}
		val desktopMain by getting
		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
		}
	}
}

android {
	namespace = "cn.vividcode.multiplatform.flex.ui.sample"
	compileSdk = libs.versions.android.compileSdk.get().toInt()
	
	sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
	sourceSets["main"].res.srcDirs("src/androidMain/res")
	sourceSets["main"].resources.srcDirs("src/commonMain/resources")
	
	defaultConfig {
		applicationId = "cn.vividcode.multiplatform.flex.ui.sample"
		minSdk = libs.versions.android.minSdk.get().toInt()
		targetSdk = libs.versions.android.targetSdk.get().toInt()
		versionCode = 1
		versionName = sampleVersion
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
	buildTypes {
		release {
			isMinifyEnabled = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
	buildFeatures {
		compose = true
	}
}

compose.desktop {
	application {
		mainClass = "cn.vividcode.multiplatform.flex.ui.sample.MainKt"
		
		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
			packageName = "Flex UI"
			packageVersion = sampleVersion
		}
	}
}

compose.resources {
	packageOfResClass = "cn.vividcode.multiplatform.flex.ui.sample.generated.resources"
	publicResClass = true
}