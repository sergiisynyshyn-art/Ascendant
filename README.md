# Ascendant

Ascendant es una aplicación Android de desarrollo personal y construcción de hábitos que transforma el progreso diario en una experiencia gamificada.

La aplicación combina misiones, niveles, logros, seguimiento de actividad física y un sistema de progresión inspirado en los RPG para ayudar al usuario a desarrollar disciplina y constancia en sus objetivos personales.

---

## Características principales

### Onboarding inmersivo
- Flujo completo de bienvenida.
- Introducción guiada al sistema.
- Registro de apodo del usuario.
- Selección de clase o identidad inicial.
- Configuración personalizada del perfil.

### Sistema de progresión
- Sistema de niveles.
- Experiencia acumulada mediante actividad diaria.
- Evolución continua del usuario.
- Representación visual del progreso.

### Misiones diarias
- Creación y gestión de objetivos diarios.
- Control de deadlines.
- Reinicio automático de ciclos diarios.
- Seguimiento del cumplimiento de tareas.
- Recordatorios programados.

### Seguimiento de actividad física
- Integración con sensores de pasos del dispositivo.
- Registro automático de actividad.
- Acumulación de progreso basada en movimiento real.
- Persistencia local de estadísticas.

### Sistema de logros
- Desbloqueo de hitos y recompensas.
- Seguimiento del progreso histórico.
- Almacenamiento permanente de logros obtenidos.

### Notificaciones y automatización
- Alarmas programadas.
- Recordatorios diarios.
- Actualización automática de estados y misiones.
- Mantenimiento del ciclo de progresión incluso entre sesiones.

---

## Tecnologías utilizadas

- Android Native (Java)
- AndroidX
- AppCompat
- Material Design Components
- SQLite
- AlarmManager
- Broadcast Receivers
- Sensores de hardware Android
- SharedPreferences
- Android Notification System

---

## Arquitectura

La aplicación está organizada en módulos centrados en la experiencia del usuario y la persistencia local de datos.

### Componentes principales

- Onboarding
- Sistema de clases
- Gestión de misiones
- Seguimiento de pasos
- Sistema de logros
- Gestión de niveles
- Persistencia SQLite
- Notificaciones y recordatorios

---

## Estructura del proyecto

```text
app/
├── src/
│   └── main/
│       ├── java/com/example/ascendant/
│       ├── res/
│       │   ├── drawable/
│       │   ├── layout/
│       │   ├── mipmap/
│       │   ├── values/
│       │   └── xml/
│       └── AndroidManifest.xml
│
└── build.gradle.kts
```

---

## Flujo principal

La experiencia del usuario sigue el siguiente recorrido:

```text
PreIntroActivity
        │
        ▼
Introducción
        │
        ▼
Confirmación de inicio
        │
        ▼
Creación de apodo
        │
        ▼
Selección de clase
        │
        ▼
MainActivity
        │
 ┌──────┼──────┐
 ▼      ▼      ▼
Misiones Logros Progreso
```

---

## Persistencia de datos

La aplicación almacena localmente:

- Nivel actual.
- Experiencia acumulada.
- Misiones activas.
- Historial de progreso.
- Pasos registrados.
- Logros desbloqueados.
- Configuración del usuario.

Toda la información se conserva entre sesiones mediante SQLite y almacenamiento local del dispositivo.

---

## Requisitos

### Desarrollo

- Android Studio
- JDK 11
- Android SDK

### Ejecución

- Android 10 (API 29) o superior
- Permisos de actividad física
- Permisos de notificaciones

---

## Instalación

Clonar el repositorio:

```bash
git clone https://github.com/TU-USUARIO/ascendant.git
```

Abrir el proyecto:

```bash
cd ascendant
```

Abrir la carpeta en Android Studio y sincronizar Gradle.

---

## Comandos útiles

Compilar versión Debug:

```bash
./gradlew assembleDebug
```

Ejecutar tests:

```bash
./gradlew test
```

Analizar código:

```bash
./gradlew lint
```

Generar APK:

```bash
./gradlew assembleRelease
```

---

## Roadmap

### v0.1
- ✅ Onboarding completo
- ✅ Sistema de niveles
- ✅ Seguimiento de pasos
- ✅ Logros
- ✅ Persistencia SQLite

### v0.2
- ⏳ Estadísticas avanzadas
- ⏳ Mejoras visuales
- ⏳ Nuevos tipos de misiones

### v1.0
- ⏳ Sistema completo de progresión
- ⏳ Personalización avanzada
- ⏳ Dashboard de evolución
- ⏳ Experiencia gamificada completa

---

## Filosofía del proyecto

Ascendant no se centra únicamente en registrar hábitos, sino en transformar el progreso personal en una experiencia motivadora mediante sistemas de progresión, objetivos y recompensas inspirados en videojuegos RPG.

Cada acción realizada por el usuario contribuye a su desarrollo dentro del sistema, convirtiendo la mejora personal en una experiencia medible y visual.

---

## Autor

Desarrollado por Serhii Synyshyn.

Proyecto personal enfocado en Android nativo, gamificación, productividad y desarrollo personal.
