# AGENTS.md

## Purpose
This is a single-module Android app focused on a linear onboarding flow and local persistence.
Use these notes to make safe, fast edits without re-discovering project structure.

## Project Snapshot
- Module: `app/` only (`settings.gradle.kts` includes `:app`)
- Language/toolchain: Java 11 + Android Gradle Plugin 9.3.1 (`app/build.gradle.kts`, `gradle/libs.versions.toml`)
- Package root: `app/src/main/java/com/example/ascendant/`
- No existing AI policy files were found (no prior `AGENTS.md`, `CLAUDE.md`, `.cursorrules`, etc.)

## Architecture and Data Flow
- App is **Activity-based**, not Fragment/Compose-based.
- Entry/onboarding chain is sequential via `Intent`s:
  `preintro/PreIntroActivity` -> `intro/IntroActivity` -> `confirmation/ConfirmationActivity` -> `nickname/NicknameActivity` -> `classselection/ClassSelectionActivity` -> `finalmessage/FinalMessageActivity`.
- Core screens include `MainActivity` and `missions/MissionsActivity`.
- Persistent state is centralized in `database/PlayerDatabase.java` (SQLiteOpenHelper singleton):
  - `player` table stores nickname + class.
  - `missions` table stores mission completion flags.
- Typical write flow: onboarding Activities collect data -> call `PlayerDatabase` methods (`saveNickname`, `saveClass`, `saveMission`) -> missions/main UI reads via `getMissionState` and related queries.

## UI/UX Conventions Seen in Code
- Fullscreen immersive UI flags are applied in Activities (see `MainActivity.java`).
- Screen transitions frequently use `overridePendingTransition(...)` with fade resources.
- Layout style is XML + ConstraintLayout + Material components (`app/src/main/res/layout`, `drawable`, `values`).
- Manifest enforces portrait orientation and Activity declarations (`app/src/main/AndroidManifest.xml`).

## Build, Test, Debug Workflows
- Build debug APK: `gradlew.bat assembleDebug`
- Unit tests (JVM): `gradlew.bat test`
- Instrumentation tests: `gradlew.bat connectedAndroidTest`
- Lint: `gradlew.bat lint`
- Tests live in `app/src/test/` and `app/src/androidTest/` (JUnit4 + Espresso configured in `app/build.gradle.kts`).

## Editing Guardrails for This Repo
- Prefer extending existing Activity + Intent patterns instead of introducing new architecture layers.
- Keep DB changes backward-aware: `PlayerDatabase` is already at version 2 with upgrade logic.
- If adding persisted fields, update table constants, `onCreate`, `onUpgrade`, and all read/write call sites together.
- Maintain resource naming and transition patterns already used in `res/`.
- Avoid adding heavy dependencies unless necessary; current dependency graph is intentionally small.

