# Solo Leveling App

Aplicación Android inspirada en el universo de Solo Leveling, centrada en una experiencia visual inmersiva basada en animaciones, vídeo introductorio y una estética oscura inspirada en el sistema de sombras.

## 📱 Descripción

Este proyecto tiene como objetivo crear una aplicación Android temática de Solo Leveling con una pantalla de introducción cinematográfica antes de acceder a la aplicación principal.

Actualmente se encuentra en fase de desarrollo inicial, centrándose en la creación de una experiencia de bienvenida visual atractiva mediante la reproducción de un vídeo introductorio personalizado.

---

## ✅ Funcionalidades implementadas

### Intro animada
- Pantalla de inicio personalizada.
- Reproducción automática de vídeo al arrancar la aplicación.
- Introducción inspirada en la estética oscura de Solo Leveling.
- Integración de recursos de vídeo directamente dentro del proyecto Android. 【1-aad091】

### Recursos multimedia
- Incorporación del archivo:

```
app/src/main/res/raw/intro.mp4
```

como recurso local para la intro de la aplicación. 【1-aad091】

### Diseño visual
- Creación y edición de clips de vídeo para la pantalla inicial. 【2-15e956】【3-d8e868】
- Pruebas visuales con imágenes de temática oscura y efectos inspirados en sombras. 【4-efbeb4】【5-406ac5】【6-49983e】【7-94cdc0】
- Integración de material inspirado en el tráiler oficial para referencias visuales. 【8-750145】

---

## 🛠 Tecnologías utilizadas

- Android Studio
- Android SDK
- Gradle
- VideoView / MediaPlayer
- Java / Kotlin

---

## 📂 Estructura actual

```text
app/
└── src/
    └── main/
        ├── java/
        ├── res/
        │   ├── drawable/
        │   ├── mipmap/
        │   ├── raw/
        │   │   └── intro.mp4
        │   ├── values/
        │   └── xml/
        └── AndroidManifest.xml
```

---

## 🚀 Próximas mejoras

- [ ] Finalizar IntroActivity.
- [ ] Mejorar la transición entre la intro y la pantalla principal.
- [ ] Añadir animaciones adicionales.
- [ ] Crear menú principal.
- [ ] Implementar navegación interna.
- [ ] Añadir sistema de progresión inspirado en Solo Leveling.
- [ ] Optimizar rendimiento multimedia.
- [ ] Preparar primera versión MVP.

---

## ⚙️ Instalación

Clona el repositorio:

```bash
git clone https://github.com/TU-USUARIO/TU-REPOSITORIO.git
```

Abre el proyecto en Android Studio.

Sincroniza Gradle.

Ejecuta la aplicación en un emulador o dispositivo Android.

---

## 📌 Estado del proyecto

Versión inicial en desarrollo.

Actualmente el foco principal está en la experiencia de introducción y la integración de recursos multimedia antes de comenzar la implementación de las funcionalidades principales.

---

## 👨‍💻 Autor

**Serhii Synyshyn**

Proyecto personal desarrollado para aprendizaje, experimentación y desarrollo de aplicaciones Android inspiradas en el universo de Solo Leveling.
