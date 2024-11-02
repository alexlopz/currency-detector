# Detector de monedas y converso de divisas

Aplicación móvil para Android desarrollada en Java que utiliza TensorFlow Lite para reconocer y clasificar imágenes de billetes de distintas denominaciones. El proyecto está construido en Android Studio y utiliza Gradle para la gestión de dependencias.

## Requisitos mínimos

- **Android Studio**: versión 4.0 o superior
- **Java Development Kit (JDK)**: versión 8 o superior
- **Gradle**: incluido en el proyecto
- **Dispositivo Android**: con Android 5.0 (Lollipop) o superior

## Dependencias

Las principales dependencias del proyecto incluyen TensorFlow Lite para el modelo de clasificación y otras bibliotecas necesarias para el funcionamiento de la aplicación. Puedes revisar el archivo [`build.gradle`](https://github.com/alexlopz/currency-detector/blob/main/app/build.gradle) para ver las dependencias completas.

### Dependencias clave en `build.gradle`

```gradle
implementation 'org.tensorflow:tensorflow-lite:2.7.0'
implementation 'androidx.appcompat:appcompat:1.3.0'
implementation 'com.google.android.material:material:1.3.0'
   ```

## Instalación y Ejecución

Sigue los siguientes pasos para instalar y ejecutar el proyecto en tu máquina local:

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/alexlopz/currency-detector.git
   ```

2. **Navegar al directorio del proyecto:**

   ```bash
   cd currency-detector
   ```

3. **Abre el proyecto en Android Studio:**

   - Inicia Android Studio.
   - Selecciona Open an existing project y navega hasta la carpeta del proyecto.
   - Android Studio descargará automáticamente las dependencias especificadas en build.gradle.
  
4. **Configura un dispositivo emulador o físico:**

   - Para pruebas, usa un dispositivo Android físico o emulador con Android 5.0 o superior.
   - Si usas un emulador, configúralo en AVD Manager dentro de Android Studio.
