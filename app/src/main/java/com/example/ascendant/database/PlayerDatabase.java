package com.example.ascendant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ascendant.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlayerDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ascendant.db";
    private static final int DATABASE_VERSION = 15;

    private static final String TABLE_PLAYER = "player";
    private static final String COL_ID = "id";
    private static final String COL_NICKNAME = "nickname";
    private static final String COL_CLASS = "class";

    private static final String TABLE_MISSIONS = "missions";
    private static final String COL_MISSION_ID = "mission_id";
    private static final String COL_COMPLETED = "completed";

    private static final String TABLE_STEPS = "steps";
    private static final String COL_INITIAL_STEPS = "initial_steps";
    private static final String COL_CURRENT_STEPS = "current_steps";
    private static final String COL_STEPS_TODAY = "steps_today";
    private static final String COL_STEP_DATE = "step_date";

    private static final String TABLE_LEVEL = "level_state";
    private static final String COL_CURRENT_LEVEL = "current_level";

    private static final String TABLE_STATS = "stats";
    private static final String COL_DISCIPLINA = "disciplina";
    private static final String COL_INTELIGENCIA = "inteligencia";
    private static final String COL_MENTALIDAD = "mentalidad";
    private static final String COL_VOLUNTAD = "voluntad";
    private static final String COL_SEGURIDAD = "seguridad";

    private static final String TABLE_ACHIEVEMENTS = "achievements";
    private static final String COL_ACHIEVEMENT_NAME = "name";
    private static final String COL_ACHIEVEMENT_DESCRIPTION = "description";
    private static final String COL_ACHIEVEMENT_UNLOCKED = "is_unlocked";
    private static final String COL_ACHIEVEMENT_UNLOCK_DATE = "unlock_date";
    private static final String COL_ACHIEVEMENT_PROGRESS = "progress";
    private static final String COL_ACHIEVEMENT_HIDDEN = "is_hidden";

    private static final String TABLE_ADVANCED_CLASS = "advanced_class";
    private static final String COL_ADVANCED_CLASS_NAME = "class_name";
    private static final String COL_ADVANCED_CLASS_UNLOCK_DATE = "unlock_date";
    private static final String COL_ADVANCED_CLASS_UNLOCKED = "is_unlocked";

    private static final String TABLE_DAILY_PROGRESS = "daily_progress";
    private static final String COL_DATE = "date_key";
    private static final String COL_DAILY_M1 = "mission_1";
    private static final String COL_DAILY_M2 = "mission_2";
    private static final String COL_DAILY_M3 = "mission_3";
    private static final String COL_DAILY_M4 = "mission_4";
    private static final String COL_DAILY_M5 = "mission_5";
    private static final String COL_DAILY_M6 = "mission_6";
    private static final String COL_DAILY_M7 = "mission_7";
    private static final String COL_DAILY_M8 = "mission_8";
    private static final String COL_DAILY_ALL_COMPLETED = "all_completed";
    private static final String COL_DAILY_COMPLETION_HOUR = "completion_hour";
    private static final String COL_DAILY_STEPS = "daily_steps";
    private static final String COL_DAILY_APP_OPENS = "app_opens";
    private static final String COL_DAILY_DAY_STATUS = "day_status";
    private static final String COL_DAILY_DEADLINE_REACHED = "deadline_reached";
    private static final String COL_DAILY_LEVEL_APPLIED = "level_applied";

    private static final String TABLE_MISSION_STATS = "mission_stats";
    private static final String COL_TOTAL_M1 = "total_mission_1";
    private static final String COL_TOTAL_M2 = "total_mission_2";
    private static final String COL_TOTAL_M3 = "total_mission_3";
    private static final String COL_TOTAL_M4 = "total_mission_4";
    private static final String COL_TOTAL_M5 = "total_mission_5";
    private static final String COL_TOTAL_M6 = "total_mission_6";
    private static final String COL_TOTAL_M7 = "total_mission_7";
    private static final String COL_TOTAL_M8 = "total_mission_8";

    private static final int ACH_FIRST_AWAKENING = 1;
    private static final int ACH_INITIAL_DISCIPLINE = 2;
    private static final int ACH_STABLE_ROUTINE = 3;
    private static final int ACH_FIRST_LEVEL = 4;
    private static final int ACH_CONSTANT_HUNTER = 5;

    private static final int ACH_LEVEL_5 = 6;
    private static final int ACH_LEVEL_10 = 7;
    private static final int ACH_LEVEL_15 = 8;
    private static final int ACH_LEVEL_20 = 9;
    private static final int ACH_LEVEL_25 = 10;
    private static final int ACH_LEVEL_30 = 11;
    private static final int ACH_LEVEL_50 = 44;
    private static final int ACH_LEVEL_75 = 45;
    private static final int ACH_LEVEL_100 = 46;

    private static final int ACH_LEGS_OF_STEEL = 12;
    private static final int ACH_AWAKENED_CORE = 13;
    private static final int ACH_HUNTER_FISTS = 14;
    private static final int ACH_SHADOW_ARMS = 15;
    private static final int ACH_SYSTEM_WALKER = 16;

    private static final int ACH_WEEK_IRON = 17;
    private static final int ACH_WEEK_CORE = 18;
    private static final int ACH_WEEK_LEGS = 19;
    private static final int ACH_WEEK_SHADOW = 20;
    private static final int ACH_WEEK_WALKER = 21;
    private static final int ACH_PERFECT_WEEK = 22;
    private static final int ACH_MONARCH_WEEK = 23;

    private static final int ACH_TOTAL_1000_PUSHUPS = 24;
    private static final int ACH_TOTAL_2000_ABS = 25;
    private static final int ACH_TOTAL_2000_SQUATS = 26;
    private static final int ACH_TOTAL_1000_BAR = 27;
    private static final int ACH_TOTAL_100K_STEPS = 28;
    private static final int ACH_HUNTER_BODY = 29;
    private static final int ACH_IRON_WILL = 30;

    private static final int ACH_EARLY_BIRD = 31;
    private static final int ACH_NIGHT_OWL = 32;
    private static final int ACH_NO_FAILURES = 33;
    private static final int ACH_SHADOW_DAY = 34;
    private static final int ACH_STEADY_RHYTHM = 35;

    private static final int ACH_INNER_AWAKENING = 36;
    private static final int ACH_BREAK_LIMITS_II = 37;
    private static final int ACH_BERSERKER = 38;
    private static final int ACH_MONARCH_SHADOW = 39;
    private static final int ACH_HUNTER_ASCENT = 40;
    private static final int ACH_SYSTEM_RECOGNIZES = 41;
    private static final int ACH_ABSOLUTE_DOMINION = 42;
    private static final int ACH_PERFECT_DAY = 43;
    private static final int ACH_ADVANCED_CLASS = 999;

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static PlayerDatabase instance;

    public static class AchievementItem {
        public final int id;
        public final String name;
        public final String description;
        public final boolean isUnlocked;
        public final String unlockDate;
        public final int progress;

        public AchievementItem(int id, String name, String description, boolean isUnlocked, String unlockDate, int progress) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.isUnlocked = isUnlocked;
            this.unlockDate = unlockDate;
            this.progress = progress;
        }
    }

    private PlayerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized PlayerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new PlayerDatabase(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PLAYER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NICKNAME + " TEXT, " +
                COL_CLASS + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_MISSIONS + " (" +
                COL_MISSION_ID + " INTEGER PRIMARY KEY, " +
                COL_COMPLETED + " INTEGER DEFAULT 0)");

        for (int i = 1; i <= 8; i++) {
            ContentValues v = new ContentValues();
            v.put(COL_MISSION_ID, i);
            v.put(COL_COMPLETED, 0);
            db.insert(TABLE_MISSIONS, null, v);
        }

        db.execSQL("CREATE TABLE " + TABLE_STEPS + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_INITIAL_STEPS + " INTEGER DEFAULT 0, " +
                COL_CURRENT_STEPS + " INTEGER DEFAULT 0, " +
                COL_STEPS_TODAY + " INTEGER DEFAULT 0, " +
                COL_STEP_DATE + " TEXT DEFAULT '')");

        ContentValues sv = new ContentValues();
        sv.put(COL_ID, 1);
        sv.put(COL_INITIAL_STEPS, 0);
        sv.put(COL_CURRENT_STEPS, 0);
        sv.put(COL_STEPS_TODAY, 0);
        sv.put(COL_STEP_DATE, "");
        db.insert(TABLE_STEPS, null, sv);

        db.execSQL("CREATE TABLE " + TABLE_LEVEL + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_CURRENT_LEVEL + " INTEGER DEFAULT 0)");

        ContentValues lv = new ContentValues();
        lv.put(COL_ID, 1);
        lv.put(COL_CURRENT_LEVEL, 0);
        db.insert(TABLE_LEVEL, null, lv);

        createStatsTable(db);
        ensureStatsRow(db);
        createAdvancedClassTable(db);
        ensureAdvancedClassRow(db);

        createAchievementsTable(db);
        createDailyProgressTable(db);
        //createMissionStatsTable(db);
        seedAchievements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MISSIONS + " (" +
                    COL_MISSION_ID + " INTEGER PRIMARY KEY, " +
                    COL_COMPLETED + " INTEGER DEFAULT 0)");
            for (int i = 1; i <= 8; i++) {
                ContentValues v = new ContentValues();
                v.put(COL_MISSION_ID, i);
                v.put(COL_COMPLETED, 0);
                db.insertWithOnConflict(TABLE_MISSIONS, null, v, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STEPS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_INITIAL_STEPS + " INTEGER DEFAULT 0, " +
                    COL_CURRENT_STEPS + " INTEGER DEFAULT 0, " +
                    COL_STEPS_TODAY + " INTEGER DEFAULT 0, " +
                    COL_STEP_DATE + " TEXT DEFAULT '')");
            ContentValues sv = new ContentValues();
            sv.put(COL_ID, 1);
            sv.put(COL_INITIAL_STEPS, 0);
            sv.put(COL_CURRENT_STEPS, 0);
            sv.put(COL_STEPS_TODAY, 0);
            sv.put(COL_STEP_DATE, "");
            db.insertWithOnConflict(TABLE_STEPS, null, sv, SQLiteDatabase.CONFLICT_IGNORE);
        }
        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LEVEL + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_CURRENT_LEVEL + " INTEGER DEFAULT 0)");
            ContentValues lv = new ContentValues();
            lv.put(COL_ID, 1);
            lv.put(COL_CURRENT_LEVEL, 0);
            db.insertWithOnConflict(TABLE_LEVEL, null, lv, SQLiteDatabase.CONFLICT_IGNORE);
        }
        if (oldVersion < 5) {
            createAchievementsTable(db);
            createDailyProgressTable(db);
            seedAchievements(db);
        }
        if (oldVersion < 6) {
            createAchievementsTable(db);
            createDailyProgressTable(db);
            try {
                db.execSQL("ALTER TABLE " + TABLE_ACHIEVEMENTS + " ADD COLUMN " + COL_ACHIEVEMENT_PROGRESS + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
            seedAchievements(db);
        }
        if (oldVersion < 7) {
            createAchievementsTable(db);
            seedAchievements(db);
        }
        if (oldVersion < 8) {
            createStatsTable(db);
            ensureStatsRow(db);
        }
        if (oldVersion < 9) {
            createAchievementsTable(db);
            createAdvancedClassTable(db);
            ensureAdvancedClassRow(db);
            try {
                db.execSQL("ALTER TABLE " + TABLE_ACHIEVEMENTS + " ADD COLUMN " + COL_ACHIEVEMENT_HIDDEN + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
            seedAchievements(db);
        }
        if (oldVersion < 10) {
            createDailyProgressTable(db);
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_DAY_STATUS + " TEXT DEFAULT 'pending'");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_DEADLINE_REACHED + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
        }
        if (oldVersion < 11) {
            createDailyProgressTable(db);
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_LEVEL_APPLIED + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
        }
        if (oldVersion < 12) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_M6 + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_M7 + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
        }
        if (oldVersion < 13) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_DAILY_PROGRESS + " ADD COLUMN " + COL_DAILY_M8 + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {
                // Column already exists on some installs.
            }
        }
        if (oldVersion < 15) {
            db.execSQL("UPDATE " + TABLE_LEVEL + " SET " + COL_CURRENT_LEVEL + " = 0 WHERE " + COL_ID + " = 1");
            db.execSQL("UPDATE " + TABLE_STATS + " SET " + 
                    COL_DISCIPLINA + " = 0, " +
                    COL_INTELIGENCIA + " = 0, " +
                    COL_MENTALIDAD + " = 0, " +
                    COL_VOLUNTAD + " = 0, " +
                    COL_SEGURIDAD + " = 0 WHERE " + COL_ID + " = 1");
        }
    }

    private void createStatsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATS + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_DISCIPLINA + " INTEGER DEFAULT 0, " +
                COL_INTELIGENCIA + " INTEGER DEFAULT 0, " +
                COL_MENTALIDAD + " INTEGER DEFAULT 0, " +
                COL_VOLUNTAD + " INTEGER DEFAULT 0, " +
                COL_SEGURIDAD + " INTEGER DEFAULT 0)");
    }

    private void ensureStatsRow(SQLiteDatabase db) {
        ContentValues sv = new ContentValues();
        sv.put(COL_ID, 1);
        sv.put(COL_DISCIPLINA, 0);
        sv.put(COL_INTELIGENCIA, 0);
        sv.put(COL_MENTALIDAD, 0);
        sv.put(COL_VOLUNTAD, 0);
        sv.put(COL_SEGURIDAD, 0);
        db.insertWithOnConflict(TABLE_STATS, null, sv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void createAchievementsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACHIEVEMENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_ACHIEVEMENT_NAME + " TEXT NOT NULL, " +
                COL_ACHIEVEMENT_DESCRIPTION + " TEXT NOT NULL, " +
                COL_ACHIEVEMENT_UNLOCKED + " INTEGER DEFAULT 0, " +
                COL_ACHIEVEMENT_UNLOCK_DATE + " TEXT, " +
                COL_ACHIEVEMENT_PROGRESS + " INTEGER DEFAULT 0, " +
                COL_ACHIEVEMENT_HIDDEN + " INTEGER DEFAULT 0)");
    }

    private void createAdvancedClassTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADVANCED_CLASS + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_ADVANCED_CLASS_NAME + " TEXT, " +
                COL_ADVANCED_CLASS_UNLOCK_DATE + " TEXT, " +
                COL_ADVANCED_CLASS_UNLOCKED + " INTEGER DEFAULT 0)");
    }

    private void ensureAdvancedClassRow(SQLiteDatabase db) {
        ContentValues v = new ContentValues();
        v.put(COL_ID, 1);
        v.putNull(COL_ADVANCED_CLASS_NAME);
        v.putNull(COL_ADVANCED_CLASS_UNLOCK_DATE);
        v.put(COL_ADVANCED_CLASS_UNLOCKED, 0);
        db.insertWithOnConflict(TABLE_ADVANCED_CLASS, null, v, SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void createDailyProgressTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DAILY_PROGRESS + " (" +
                COL_DATE + " TEXT PRIMARY KEY, " +
                COL_DAILY_M1 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M2 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M3 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M4 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M5 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M6 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M7 + " INTEGER DEFAULT 0, " +
                COL_DAILY_M8 + " INTEGER DEFAULT 0, " +
                COL_DAILY_ALL_COMPLETED + " INTEGER DEFAULT 0, " +
                COL_DAILY_COMPLETION_HOUR + " INTEGER DEFAULT -1, " +
                COL_DAILY_STEPS + " INTEGER DEFAULT 0, " +
                COL_DAILY_APP_OPENS + " INTEGER DEFAULT 0, " +
                COL_DAILY_DAY_STATUS + " TEXT DEFAULT 'pending', " +
                COL_DAILY_DEADLINE_REACHED + " INTEGER DEFAULT 0, " +
                COL_DAILY_LEVEL_APPLIED + " INTEGER DEFAULT 0)");
    }

    private void seedAchievements(SQLiteDatabase db) {
        ensureAchievement(db, ACH_FIRST_AWAKENING, "Primer Despertar", "Completar misiones por primera vez.");
        ensureAchievement(db, ACH_INITIAL_DISCIPLINE, "Disciplina Inicial", "Completar misiones 2 dias seguidos.");
        ensureAchievement(db, ACH_STABLE_ROUTINE, "Rutina Establecida", "Completar misiones 5 dias seguidos.");
        ensureAchievement(db, ACH_FIRST_LEVEL, "Primer Nivel", "Subir al nivel 1/100.");
        ensureAchievement(db, ACH_CONSTANT_HUNTER, "Cazador Constante", "Completar misiones 10 dias totales.");

        ensureAchievement(db, ACH_LEVEL_5, "Nivel 5", "Primer Paso.");
        ensureAchievement(db, ACH_LEVEL_10, "Nivel 10", "Cazador en Marcha.");
        ensureAchievement(db, ACH_LEVEL_15, "Nivel 15", "Ritmo Ascendente.");
        ensureAchievement(db, ACH_LEVEL_20, "Nivel 20", "Voluntad Firme.");
        ensureAchievement(db, ACH_LEVEL_25, "Nivel 25", "Espiritu de Sombra.");
        ensureAchievement(db, ACH_LEVEL_30, "Nivel 30", "Hunter Rising.");
        ensureAchievement(db, ACH_LEVEL_50, "Nivel 50", "Alcanzar nivel 50/100.");
        ensureAchievement(db, ACH_LEVEL_75, "Nivel 75", "Alcanzar nivel 75/100.");
        ensureAchievement(db, ACH_LEVEL_100, "Ascendant", "Alcanzar nivel 100/100.");

        ensureAchievement(db, ACH_LEGS_OF_STEEL, "Piernas de Acero", "Completar 100 sentadillas 3 dias seguidos.");
        ensureAchievement(db, ACH_AWAKENED_CORE, "Nucleo Despierto", "Completar 100 abdominales 5 veces.");
        ensureAchievement(db, ACH_HUNTER_FISTS, "Punos del Cazador", "Completar 50 flexiones 7 veces.");
        ensureAchievement(db, ACH_SHADOW_ARMS, "Brazos de Sombra", "Completar barra biceps 10 veces.");
        ensureAchievement(db, ACH_SYSTEM_WALKER, "Caminante del Sistema", "Completar la mision de 5000 pasos 10 veces.");

        ensureAchievement(db, ACH_WEEK_IRON, "Semana de Hierro", "350 flexiones acumuladas en una semana.");
        ensureAchievement(db, ACH_WEEK_CORE, "Semana de Nucleo", "700 abdominales acumuladas en una semana.");
        ensureAchievement(db, ACH_WEEK_LEGS, "Semana de Piernas", "700 sentadillas acumuladas en una semana.");
        ensureAchievement(db, ACH_WEEK_SHADOW, "Semana de Sombra", "350 barra biceps acumuladas en una semana.");
        ensureAchievement(db, ACH_WEEK_WALKER, "Semana del Caminante", "35.000 pasos acumulados en una semana.");
        ensureAchievement(db, ACH_PERFECT_WEEK, "Semana Perfecta", "Completar 7 dias seguidos sin fallar ninguna mision.");
        ensureAchievement(db, ACH_MONARCH_WEEK, "Semana Monarca", "Completar 7 dias seguidos y superar todas las misiones por +20%.");

        ensureAchievement(db, ACH_TOTAL_1000_PUSHUPS, "1000 Flexiones Totales", "Acumular 1000 flexiones.");
        ensureAchievement(db, ACH_TOTAL_2000_ABS, "2000 Abdominales Totales", "Acumular 2000 abdominales.");
        ensureAchievement(db, ACH_TOTAL_2000_SQUATS, "2000 Sentadillas Totales", "Acumular 2000 sentadillas.");
        ensureAchievement(db, ACH_TOTAL_1000_BAR, "1000 Barra Totales", "Acumular 1000 barra biceps.");
        ensureAchievement(db, ACH_TOTAL_100K_STEPS, "100.000 Pasos Totales", "Acumular 100.000 pasos.");
        ensureAchievement(db, ACH_HUNTER_BODY, "Cuerpo de Cazador", "Completar 500 misiones fisicas totales.");
        ensureAchievement(db, ACH_IRON_WILL, "Voluntad de Hierro", "Completar 100 dias totales.");

        ensureAchievement(db, ACH_EARLY_BIRD, "Madrugador", "Completar misiones antes de las 10:00.");
        ensureAchievement(db, ACH_NIGHT_OWL, "Nocturno", "Completar misiones despues de las 22:00.");
        ensureAchievement(db, ACH_NO_FAILURES, "Sin Fallos", "Completar misiones 3 dias seguidos sin errores.");
        ensureAchievement(db, ACH_SHADOW_DAY, "Dia de Sombra", "Completar misiones sin abrir la app mas de 1 vez.");
        ensureAchievement(db, ACH_STEADY_RHYTHM, "Ritmo Constante", "Completar misiones en el mismo rango horario durante 5 dias.");

        ensureAchievement(db, ACH_INNER_AWAKENING, "Despertar Interior", "Completar misiones en un dia emocionalmente dificil.");
        ensureAchievement(db, ACH_BREAK_LIMITS_II, "Rompe Limites II", "Superar los 10.000 pasos en un dia.");
        ensureAchievement(db, ACH_BERSERKER, "Modo Berserker", "Completar todas las misiones con +50% extra en un dia.");
        ensureAchievement(db, ACH_MONARCH_SHADOW, "Sombra del Monarca", "Completar 14 dias seguidos.");
        ensureAchievement(db, ACH_HUNTER_ASCENT, "Ascenso del Cazador", "Completar 30 dias seguidos.");
        ensureAchievement(db, ACH_SYSTEM_RECOGNIZES, "El Sistema te Reconoce", "Desbloquear 20 logros distintos.");
        ensureAchievement(db, ACH_ABSOLUTE_DOMINION, "Dominio Absoluto", "Desbloquear 30 logros distintos.");
        ensureAchievement(db, ACH_PERFECT_DAY, "Dia Perfecto", "Completar todas las misiones del dia.");
        ensureAchievement(db, ACH_ADVANCED_CLASS, "Clase Avanzada", "Has desbloqueado tu Clase Avanzada.", true);
    }

    private void ensureAchievement(SQLiteDatabase db, int id, String name, String description) {
        ensureAchievement(db, id, name, description, false);
    }

    private void ensureAchievement(SQLiteDatabase db, int id, String name, String description, boolean isHidden) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(COL_ID, id);
        insertValues.put(COL_ACHIEVEMENT_NAME, name);
        insertValues.put(COL_ACHIEVEMENT_DESCRIPTION, description);
        insertValues.put(COL_ACHIEVEMENT_UNLOCKED, 0);
        insertValues.putNull(COL_ACHIEVEMENT_UNLOCK_DATE);
        insertValues.put(COL_ACHIEVEMENT_PROGRESS, 0);
        insertValues.put(COL_ACHIEVEMENT_HIDDEN, isHidden ? 1 : 0);
        db.insertWithOnConflict(TABLE_ACHIEVEMENTS, null, insertValues, SQLiteDatabase.CONFLICT_IGNORE);

        ContentValues updateDefinition = new ContentValues();
        updateDefinition.put(COL_ACHIEVEMENT_NAME, name);
        updateDefinition.put(COL_ACHIEVEMENT_DESCRIPTION, description);
        updateDefinition.put(COL_ACHIEVEMENT_HIDDEN, isHidden ? 1 : 0);
        db.update(TABLE_ACHIEVEMENTS, updateDefinition, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Player

    public void saveNickname(String nickname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_ID, 1);
        v.put(COL_NICKNAME, nickname);
        db.insertWithOnConflict(TABLE_PLAYER, null, v, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveClass(String playerClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_CLASS, playerClass);
        db.update(TABLE_PLAYER, v, COL_ID + " = 1", null);
    }

    public String getNickname() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_NICKNAME + " FROM " + TABLE_PLAYER + " LIMIT 1", null);
        String val = null;
        if (c.moveToFirst()) val = c.getString(0);
        c.close();
        return val;
    }

    public String getPlayerClass() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_CLASS + " FROM " + TABLE_PLAYER + " LIMIT 1", null);
        String val = null;
        if (c.moveToFirst()) val = c.getString(0);
        c.close();
        return val;
    }

    // Missions

    public void saveMission(int missionId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_COMPLETED, completed ? 1 : 0);
        db.update(TABLE_MISSIONS, v, COL_MISSION_ID + " = ?", new String[]{String.valueOf(missionId)});
    }

    public void saveMissionForToday(int missionId, boolean completed, String today) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues missionValues = new ContentValues();
            missionValues.put(COL_COMPLETED, completed ? 1 : 0);
            db.update(TABLE_MISSIONS, missionValues, COL_MISSION_ID + " = ?", new String[]{String.valueOf(missionId)});

            ensureDailyRow(db, today);
            String dailyColumn = missionColumnForId(missionId);
            if (dailyColumn != null) {
                ContentValues dailyValues = new ContentValues();
                dailyValues.put(dailyColumn, completed ? 1 : 0);
                db.update(TABLE_DAILY_PROGRESS, dailyValues, COL_DATE + " = ?", new String[]{today});
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public boolean getMissionState(int missionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_COMPLETED + " FROM " + TABLE_MISSIONS +
                " WHERE " + COL_MISSION_ID + " = ?", new String[]{String.valueOf(missionId)});
        boolean val = false;
        if (c.moveToFirst()) val = c.getInt(0) == 1;
        c.close();
        return val;
    }

    public boolean areAllMissionsCompleted() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MISSIONS +
                " WHERE " + COL_COMPLETED + " = 1", null);
        boolean val = false;
        if (c.moveToFirst()) val = c.getInt(0) == 8;
        c.close();
        return val;
    }

    public void resetAllMissions() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_COMPLETED, 0);
        db.update(TABLE_MISSIONS, v, null, null);
    }

    // Steps

    public long getInitialSteps() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_INITIAL_STEPS + " FROM " + TABLE_STEPS + " WHERE " + COL_ID + " = 1", null);
        long val = 0;
        if (c.moveToFirst()) val = c.getLong(0);
        c.close();
        return val;
    }

    public String getStepDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_STEP_DATE + " FROM " + TABLE_STEPS + " WHERE " + COL_ID + " = 1", null);
        String val = "";
        if (c.moveToFirst()) val = c.getString(0);
        c.close();
        return val;
    }

    public long getStepsToday() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_STEPS_TODAY + " FROM " + TABLE_STEPS + " WHERE " + COL_ID + " = 1", null);
        long val = 0;
        if (c.moveToFirst()) val = c.getLong(0);
        c.close();
        return val;
    }

    public void saveStepData(long initialSteps, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_INITIAL_STEPS, initialSteps);
        v.put(COL_STEP_DATE, date);
        db.update(TABLE_STEPS, v, COL_ID + " = 1", null);
    }

    public void saveCurrentSteps(long currentSteps, long stepsToday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_CURRENT_STEPS, currentSteps);
        v.put(COL_STEPS_TODAY, stepsToday);
        db.update(TABLE_STEPS, v, COL_ID + " = 1", null);
    }

    // Level

    public int getCurrentLevel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_CURRENT_LEVEL + " FROM " + TABLE_LEVEL + " WHERE " + COL_ID + " = 1", null);
        int val = 0;
        if (c.moveToFirst()) val = c.getInt(0);
        c.close();
        return val;
    }

    public int incrementLevel(int maxLevel) {
        int currentLevel = getCurrentLevel();
        int newLevel = Math.min(currentLevel + 1, maxLevel);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_CURRENT_LEVEL, newLevel);
        db.update(TABLE_LEVEL, v, COL_ID + " = 1", null);
        if (newLevel > currentLevel) {
            ensureStatsRow(db);
            db.execSQL("UPDATE " + TABLE_STATS +
                    " SET " + COL_DISCIPLINA + " = " + COL_DISCIPLINA + " + 1, " +
                    COL_INTELIGENCIA + " = " + COL_INTELIGENCIA + " + 1, " +
                    COL_MENTALIDAD + " = " + COL_MENTALIDAD + " + 1, " +
                    COL_VOLUNTAD + " = " + COL_VOLUNTAD + " + 1, " +
                    COL_SEGURIDAD + " = " + COL_SEGURIDAD + " + 1" +
                    " WHERE " + COL_ID + " = 1");
        }
        return newLevel;
    }

    public String unlockAdvancedClassIfEligible(int currentLevel, String today) {
        if (currentLevel != 30) return null;

        SQLiteDatabase db = this.getWritableDatabase();
        ensureStatsRow(db);
        createAdvancedClassTable(db);
        ensureAdvancedClassRow(db);

        Cursor c = db.rawQuery("SELECT " + COL_ADVANCED_CLASS_UNLOCKED + " FROM " + TABLE_ADVANCED_CLASS + " WHERE " + COL_ID + " = 1", null);
        boolean alreadyUnlocked = false;
        if (c.moveToFirst()) {
            alreadyUnlocked = c.getInt(0) == 1;
        }
        c.close();
        if (alreadyUnlocked) return null;

        String className = determineAdvancedClassFromStats(db);
        ContentValues v = new ContentValues();
        v.put(COL_ADVANCED_CLASS_NAME, className);
        v.put(COL_ADVANCED_CLASS_UNLOCK_DATE, today);
        v.put(COL_ADVANCED_CLASS_UNLOCKED, 1);
        db.update(TABLE_ADVANCED_CLASS, v, COL_ID + " = 1", null);

        unlockIfEligible(db, ACH_ADVANCED_CLASS, true, today, new ArrayList<>());
        return className;
    }

    private String determineAdvancedClassFromStats(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT " + COL_DISCIPLINA + ", " + COL_INTELIGENCIA + ", " + COL_MENTALIDAD + ", " + COL_VOLUNTAD + ", " + COL_SEGURIDAD +
                " FROM " + TABLE_STATS + " WHERE " + COL_ID + " = 1", null);

        int disciplina = 0;
        int inteligencia = 0;
        int mentalidad = 0;
        int voluntad = 0;
        int seguridad = 0;

        if (c.moveToFirst()) {
            disciplina = c.getInt(0);
            inteligencia = c.getInt(1);
            mentalidad = c.getInt(2);
            voluntad = c.getInt(3);
            seguridad = c.getInt(4);
        }
        c.close();

        int max = Math.max(disciplina, Math.max(inteligencia, Math.max(mentalidad, Math.max(voluntad, seguridad))));
        if (disciplina == max && disciplina > mentalidad && disciplina > voluntad && disciplina > inteligencia && disciplina > seguridad) {
            return "Iron Discipline";
        }
        if (mentalidad == max && mentalidad > disciplina && mentalidad > voluntad && mentalidad > inteligencia && mentalidad > seguridad) {
            return "Mindbreaker";
        }
        if (voluntad == max && voluntad > disciplina && voluntad > mentalidad && voluntad > inteligencia && voluntad > seguridad) {
            return "Ascendant Hunter";
        }
        return "Shadow Monarch";
    }

    // Daily tracking

    public void incrementAppOpenForToday(String today) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);
        db.execSQL("UPDATE " + TABLE_DAILY_PROGRESS + " SET " + COL_DAILY_APP_OPENS + " = " + COL_DAILY_APP_OPENS + " + 1 WHERE " + COL_DATE + " = ?",
                new Object[]{today});
    }

    public void markDailyMissionCompleted(String today, int missionId) {
        setDailyMissionState(today, missionId, true);
    }

    public void setDailyMissionState(String today, int missionId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);
        String column = missionColumnForId(missionId);
        if (column == null) return;

        ContentValues v = new ContentValues();
        v.put(column, completed ? 1 : 0);
        db.update(TABLE_DAILY_PROGRESS, v, COL_DATE + " = ?", new String[]{today});
    }

    public void updateDailySteps(String today, long stepsToday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);
        ContentValues v = new ContentValues();
        v.put(COL_DAILY_STEPS, stepsToday);
        db.update(TABLE_DAILY_PROGRESS, v, COL_DATE + " = ?", new String[]{today});
    }

    public void recordDailyMissionsCompletion(String today, int completionHour, long stepsToday) {
        finalizeDailyProgress(today, completionHour, stepsToday, false);
    }

    public void markDailyFailed(String today) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);

        ContentValues v = new ContentValues();
        v.put(COL_DAILY_ALL_COMPLETED, 0);
        v.put(COL_DAILY_COMPLETION_HOUR, -1);
        v.put(COL_DAILY_DAY_STATUS, "failed");
        v.put(COL_DAILY_DEADLINE_REACHED, 1);
        v.put(COL_DAILY_LEVEL_APPLIED, 0);
        db.update(TABLE_DAILY_PROGRESS, v, COL_DATE + " = ?", new String[]{today});
    }

    public void finalizeDailyProgress(String today, int completionHour, long stepsToday, boolean deadlineReached) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);

        boolean completed = areDailyMissionsCompletedForDate(today);
        ContentValues v = new ContentValues();
        v.put(COL_DAILY_ALL_COMPLETED, completed ? 1 : 0);
        v.put(COL_DAILY_COMPLETION_HOUR, completed ? completionHour : -1);
        v.put(COL_DAILY_STEPS, stepsToday);
        v.put(COL_DAILY_DAY_STATUS, completed ? "completed" : "failed");
        v.put(COL_DAILY_DEADLINE_REACHED, deadlineReached ? 1 : 0);
        db.update(TABLE_DAILY_PROGRESS, v, COL_DATE + " = ?", new String[]{today});
    }

    public boolean isDailyLevelApplied(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_LEVEL_APPLIED + " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});
        boolean applied = false;
        if (c.moveToFirst()) applied = c.getInt(0) == 1;
        c.close();
        return applied;
    }

    public void setDailyLevelApplied(String today, boolean applied) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);
        ContentValues v = new ContentValues();
        v.put(COL_DAILY_LEVEL_APPLIED, applied ? 1 : 0);
        db.update(TABLE_DAILY_PROGRESS, v, COL_DATE + " = ?", new String[]{today});
    }

    public boolean hasAnyDailyMissionProgress(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_M1 + ", " + COL_DAILY_M2 + ", " + COL_DAILY_M3 + ", " + COL_DAILY_M4 + ", " + COL_DAILY_M5 + ", " +
                COL_DAILY_ALL_COMPLETED + ", " + COL_DAILY_STEPS +
                " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});

        boolean hasProgress = false;
        if (c.moveToFirst()) {
            hasProgress = c.getInt(0) == 1
                    || c.getInt(1) == 1
                    || c.getInt(2) == 1
                    || c.getInt(3) == 1
                    || c.getInt(4) == 1
                    || c.getInt(5) == 1
                    || c.getLong(6) > 0;
        }
        c.close();
        return hasProgress;
    }

    public void resetStepRuntimeForNewDay(String today) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_STEPS +
                " SET " + COL_INITIAL_STEPS + " = " + COL_CURRENT_STEPS + ", " +
                COL_STEPS_TODAY + " = 0, " +
                COL_STEP_DATE + " = ? WHERE " + COL_ID + " = 1", new Object[]{today});
    }

    public void prepareRuntimeForNewDay(String today) {
        SQLiteDatabase db = this.getWritableDatabase();
        ensureDailyRow(db, today);

        // Evita borrar progreso si la app ya registró avances del nuevo día.
        if (!hasAnyDailyMissionProgress(today)) {
            ContentValues missions = new ContentValues();
            missions.put(COL_COMPLETED, 0);
            db.update(TABLE_MISSIONS, missions, null, null);
            resetStepRuntimeForNewDay(today);
        }
    }

    public boolean areDailyMissionsCompletedForDate(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_M1 + ", " + COL_DAILY_M2 + ", " + COL_DAILY_M3 + ", " + COL_DAILY_M4 + ", " + COL_DAILY_M5 + ", " + 
                COL_DAILY_M6 + ", " + COL_DAILY_M7 + ", " + COL_DAILY_M8 + ", " +
                COL_DAILY_ALL_COMPLETED + ", " + COL_DAILY_DAY_STATUS +
                " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});

        boolean completed = false;
        if (c.moveToFirst()) {
            int m1 = c.getInt(0);
            int m2 = c.getInt(1);
            int m3 = c.getInt(2);
            int m4 = c.getInt(3);
            int m5 = c.getInt(4);
            int m6 = c.getInt(5);
            int m7 = c.getInt(6);
            int m8 = c.getInt(7);
            int allCompleted = c.getInt(8);
            String dayStatus = c.isNull(9) ? "pending" : c.getString(9);
            if (!"failed".equals(dayStatus)) {
                completed = "completed".equals(dayStatus) || allCompleted == 1 || (m1 == 1 && m2 == 1 && m3 == 1 && m4 == 1 && m5 == 1 && m6 == 1 && m7 == 1 && m8 == 1);
            }
        }
        c.close();
        return completed;
    }

    public String getDailyStatus(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_DAY_STATUS + " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});
        String status = "pending";
        if (c.moveToFirst() && !c.isNull(0)) status = c.getString(0);
        c.close();
        return status;
    }

    public boolean hasDailyProgressForDate(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ? LIMIT 1", new String[]{today});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean isDeadlineReached(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_DEADLINE_REACHED + " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});
        boolean reached = false;
        if (c.moveToFirst()) reached = c.getInt(0) == 1;
        c.close();
        return reached;
    }

    public int getDailyCompletionHour(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_COMPLETION_HOUR + " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});
        int hour = -1;
        if (c.moveToFirst()) hour = c.getInt(0);
        c.close();
        return hour;
    }

    public long getDailySteps(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DAILY_STEPS + " FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DATE + " = ?", new String[]{today});
        long steps = 0;
        if (c.moveToFirst()) steps = c.getLong(0);
        c.close();
        return steps;
    }

    private void ensureDailyRow(SQLiteDatabase db, String today) {
        ContentValues base = new ContentValues();
        base.put(COL_DATE, today);
        db.insertWithOnConflict(TABLE_DAILY_PROGRESS, null, base, SQLiteDatabase.CONFLICT_IGNORE);
    }

    private String missionColumnForId(int missionId) {
        switch (missionId) {
            case 1: return COL_DAILY_M1;
            case 2: return COL_DAILY_M2;
            case 3: return COL_DAILY_M3;
            case 4: return COL_DAILY_M4;
            case 5: return COL_DAILY_M5;
            case 6: return COL_DAILY_M6;
            case 7: return COL_DAILY_M7;
            case 8: return COL_DAILY_M8;
            default: return null;
        }
    }

    // Achievements

    public List<AchievementItem> getAllAchievements() {
        List<AchievementItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_ID + ", " + COL_ACHIEVEMENT_NAME + ", " + COL_ACHIEVEMENT_DESCRIPTION + ", " +
                COL_ACHIEVEMENT_UNLOCKED + ", " + COL_ACHIEVEMENT_UNLOCK_DATE + ", " + COL_ACHIEVEMENT_PROGRESS +
                " FROM " + TABLE_ACHIEVEMENTS +
                " WHERE " + COL_ACHIEVEMENT_HIDDEN + " = 0 OR " + COL_ACHIEVEMENT_UNLOCKED + " = 1" +
                " ORDER BY " + COL_ID, null);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String desc = c.getString(2);
            boolean unlocked = c.getInt(3) == 1;
            String unlockDate = c.isNull(4) ? null : c.getString(4);
            int progress = c.isNull(5) ? 0 : c.getInt(5);
            list.add(new AchievementItem(id, name, desc, unlocked, unlockDate, progress));
        }
        c.close();
        return list;
    }

    public ArrayList<String> evaluateAndUnlockAchievements(String today, int currentLevel, long stepsToday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> unlockedNow = new ArrayList<>();

        int completedDaysTotal = countRows("SELECT COUNT(*) FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1");
        int allCompletedMaxStreak = getMaxStreakForColumn(COL_DAILY_ALL_COMPLETED);
        int allCompletedCurrentStreak = getCurrentStreakForColumn(COL_DAILY_ALL_COMPLETED, today);
        int m1MaxStreak = getMaxStreakForColumn(COL_DAILY_M1);

        int mission1Count = sumColumn(COL_DAILY_M1);
        int mission2Count = sumColumn(COL_DAILY_M2);
        int mission3Count = sumColumn(COL_DAILY_M3);
        int mission4Count = sumColumn(COL_DAILY_M4);
        int mission5Count = sumColumn(COL_DAILY_M5);

        long stepsTotal = sumColumnLong(COL_DAILY_STEPS);

        int weeklyPushups = sumLastDays(COL_DAILY_M3, 7, today) * 50;
        int weeklyAbs = sumLastDays(COL_DAILY_M2, 7, today) * 100;
        int weeklySquats = sumLastDays(COL_DAILY_M1, 7, today) * 100;
        int weeklyBar = sumLastDays(COL_DAILY_M4, 7, today) * 50;
        long weeklySteps = sumLastDaysLong(COL_DAILY_STEPS, 7, today);
        int weeklyPerfectDays = countLastDaysCompleted(7, today);

        boolean hasEarlyBird = exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_COMPLETION_HOUR + " >= 0 AND " + COL_DAILY_COMPLETION_HOUR + " < 10 LIMIT 1");
        boolean hasNightOwl = exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_COMPLETION_HOUR + " >= 22 LIMIT 1");
        boolean hasShadowDay = exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_APP_OPENS + " <= 1 LIMIT 1");
        boolean hasInnerAwakening = exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_APP_OPENS + " >= 3 AND " + COL_DAILY_COMPLETION_HOUR + " >= 22 LIMIT 1");
        boolean hasBerserker = exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_STEPS + " >= 7500 LIMIT 1");
        boolean hasBreakLimits2 = stepsToday > 10000 || exists("SELECT 1 FROM " + TABLE_DAILY_PROGRESS + " WHERE " + COL_DAILY_STEPS + " > 10000 LIMIT 1");

        boolean hasSteadyRhythm = hasSameTimeRangeStreak(5);

        boolean hasSevenDayRun = allCompletedCurrentStreak >= 7;

        boolean hasMonarchWeek = hasSevenDayRun
                && weeklyPerfectDays == 7
                && weeklyPushups >= 420
                && weeklyAbs >= 840
                && weeklySquats >= 840
                && weeklyBar >= 420
                && weeklySteps >= 42000;

        int totalPushups = mission3Count * 50;
        int totalAbs = mission2Count * 100;
        int totalSquats = mission1Count * 100;
        int totalBar = mission4Count * 50;
        int totalPhysicalMissions = mission1Count + mission2Count + mission3Count + mission4Count;

        setAchievementProgress(db, ACH_INITIAL_DISCIPLINE, Math.min(allCompletedMaxStreak, 2));
        setAchievementProgress(db, ACH_STABLE_ROUTINE, Math.min(allCompletedMaxStreak, 5));
        setAchievementProgress(db, ACH_CONSTANT_HUNTER, Math.min(completedDaysTotal, 10));

        setAchievementProgress(db, ACH_LEVEL_5, Math.min(currentLevel, 5));
        setAchievementProgress(db, ACH_LEVEL_10, Math.min(currentLevel, 10));
        setAchievementProgress(db, ACH_LEVEL_15, Math.min(currentLevel, 15));
        setAchievementProgress(db, ACH_LEVEL_20, Math.min(currentLevel, 20));
        setAchievementProgress(db, ACH_LEVEL_25, Math.min(currentLevel, 25));
        setAchievementProgress(db, ACH_LEVEL_30, Math.min(currentLevel, 30));
        setAchievementProgress(db, ACH_LEVEL_50, Math.min(currentLevel, 50));
        setAchievementProgress(db, ACH_LEVEL_75, Math.min(currentLevel, 75));
        setAchievementProgress(db, ACH_LEVEL_100, Math.min(currentLevel, 100));

        setAchievementProgress(db, ACH_LEGS_OF_STEEL, Math.min(m1MaxStreak, 3));
        setAchievementProgress(db, ACH_AWAKENED_CORE, Math.min(mission2Count, 5));
        setAchievementProgress(db, ACH_HUNTER_FISTS, Math.min(mission3Count, 7));
        setAchievementProgress(db, ACH_SHADOW_ARMS, Math.min(mission4Count, 10));
        setAchievementProgress(db, ACH_SYSTEM_WALKER, Math.min(mission5Count, 10));

        setAchievementProgress(db, ACH_WEEK_IRON, Math.min(weeklyPushups, 350));
        setAchievementProgress(db, ACH_WEEK_CORE, Math.min(weeklyAbs, 700));
        setAchievementProgress(db, ACH_WEEK_LEGS, Math.min(weeklySquats, 700));
        setAchievementProgress(db, ACH_WEEK_SHADOW, Math.min(weeklyBar, 350));
        setAchievementProgress(db, ACH_WEEK_WALKER, (int) Math.min(weeklySteps, 35000));
        setAchievementProgress(db, ACH_PERFECT_WEEK, Math.min(weeklyPerfectDays, 7));
        setAchievementProgress(db, ACH_MONARCH_WEEK, hasMonarchWeek ? 1 : 0);

        setAchievementProgress(db, ACH_TOTAL_1000_PUSHUPS, Math.min(totalPushups, 1000));
        setAchievementProgress(db, ACH_TOTAL_2000_ABS, Math.min(totalAbs, 2000));
        setAchievementProgress(db, ACH_TOTAL_2000_SQUATS, Math.min(totalSquats, 2000));
        setAchievementProgress(db, ACH_TOTAL_1000_BAR, Math.min(totalBar, 1000));
        setAchievementProgress(db, ACH_TOTAL_100K_STEPS, (int) Math.min(stepsTotal, 100000));
        setAchievementProgress(db, ACH_HUNTER_BODY, Math.min(totalPhysicalMissions, 500));
        setAchievementProgress(db, ACH_IRON_WILL, Math.min(completedDaysTotal, 100));

        setAchievementProgress(db, ACH_NO_FAILURES, Math.min(allCompletedMaxStreak, 3));
        setAchievementProgress(db, ACH_SHADOW_DAY, hasShadowDay ? 1 : 0);
        setAchievementProgress(db, ACH_STEADY_RHYTHM, hasSteadyRhythm ? 1 : 0);

        setAchievementProgress(db, ACH_BREAK_LIMITS_II, hasBreakLimits2 ? 10000 : (int) Math.min(stepsToday, 10000));
        setAchievementProgress(db, ACH_BERSERKER, hasBerserker ? 1 : 0);
        setAchievementProgress(db, ACH_MONARCH_SHADOW, Math.min(allCompletedMaxStreak, 14));
        setAchievementProgress(db, ACH_HUNTER_ASCENT, Math.min(allCompletedMaxStreak, 30));
        setAchievementProgress(db, ACH_PERFECT_DAY, Math.min(completedDaysTotal, 1));

        unlockIfEligible(db, ACH_FIRST_AWAKENING, completedDaysTotal >= 1, today, unlockedNow);
        unlockIfEligible(db, ACH_INITIAL_DISCIPLINE, allCompletedMaxStreak >= 2, today, unlockedNow);
        unlockIfEligible(db, ACH_STABLE_ROUTINE, allCompletedMaxStreak >= 5, today, unlockedNow);
        unlockIfEligible(db, ACH_FIRST_LEVEL, currentLevel >= 1, today, unlockedNow);
        unlockIfEligible(db, ACH_CONSTANT_HUNTER, completedDaysTotal >= 10, today, unlockedNow);

        unlockIfEligible(db, ACH_LEVEL_5, currentLevel >= 5, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_10, currentLevel >= 10, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_15, currentLevel >= 15, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_20, currentLevel >= 20, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_25, currentLevel >= 25, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_30, currentLevel >= 30, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_50, currentLevel >= 50, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_75, currentLevel >= 75, today, unlockedNow);
        unlockIfEligible(db, ACH_LEVEL_100, currentLevel >= 100, today, unlockedNow);

        unlockIfEligible(db, ACH_LEGS_OF_STEEL, m1MaxStreak >= 3, today, unlockedNow);
        unlockIfEligible(db, ACH_AWAKENED_CORE, mission2Count >= 5, today, unlockedNow);
        unlockIfEligible(db, ACH_HUNTER_FISTS, mission3Count >= 7, today, unlockedNow);
        unlockIfEligible(db, ACH_SHADOW_ARMS, mission4Count >= 10, today, unlockedNow);
        unlockIfEligible(db, ACH_SYSTEM_WALKER, mission5Count >= 10, today, unlockedNow);

        unlockIfEligible(db, ACH_WEEK_IRON, hasSevenDayRun && weeklyPushups >= 350, today, unlockedNow);
        unlockIfEligible(db, ACH_WEEK_CORE, hasSevenDayRun && weeklyAbs >= 700, today, unlockedNow);
        unlockIfEligible(db, ACH_WEEK_LEGS, hasSevenDayRun && weeklySquats >= 700, today, unlockedNow);
        unlockIfEligible(db, ACH_WEEK_SHADOW, hasSevenDayRun && weeklyBar >= 350, today, unlockedNow);
        unlockIfEligible(db, ACH_WEEK_WALKER, hasSevenDayRun && weeklySteps >= 35000, today, unlockedNow);
        unlockIfEligible(db, ACH_PERFECT_WEEK, hasSevenDayRun && weeklyPerfectDays == 7, today, unlockedNow);
        unlockIfEligible(db, ACH_MONARCH_WEEK, hasMonarchWeek, today, unlockedNow);

        unlockIfEligible(db, ACH_TOTAL_1000_PUSHUPS, totalPushups >= 1000, today, unlockedNow);
        unlockIfEligible(db, ACH_TOTAL_2000_ABS, totalAbs >= 2000, today, unlockedNow);
        unlockIfEligible(db, ACH_TOTAL_2000_SQUATS, totalSquats >= 2000, today, unlockedNow);
        unlockIfEligible(db, ACH_TOTAL_1000_BAR, totalBar >= 1000, today, unlockedNow);
        unlockIfEligible(db, ACH_TOTAL_100K_STEPS, stepsTotal >= 100000, today, unlockedNow);
        unlockIfEligible(db, ACH_HUNTER_BODY, totalPhysicalMissions >= 500, today, unlockedNow);
        unlockIfEligible(db, ACH_IRON_WILL, completedDaysTotal >= 100, today, unlockedNow);

        unlockIfEligible(db, ACH_EARLY_BIRD, hasEarlyBird, today, unlockedNow);
        unlockIfEligible(db, ACH_NIGHT_OWL, hasNightOwl, today, unlockedNow);
        unlockIfEligible(db, ACH_NO_FAILURES, allCompletedMaxStreak >= 3, today, unlockedNow);
        unlockIfEligible(db, ACH_SHADOW_DAY, hasShadowDay, today, unlockedNow);
        unlockIfEligible(db, ACH_STEADY_RHYTHM, hasSteadyRhythm, today, unlockedNow);

        unlockIfEligible(db, ACH_INNER_AWAKENING, hasInnerAwakening, today, unlockedNow);
        unlockIfEligible(db, ACH_BREAK_LIMITS_II, hasBreakLimits2, today, unlockedNow);
        unlockIfEligible(db, ACH_BERSERKER, hasBerserker, today, unlockedNow);
        unlockIfEligible(db, ACH_MONARCH_SHADOW, allCompletedMaxStreak >= 14, today, unlockedNow);
        unlockIfEligible(db, ACH_HUNTER_ASCENT, allCompletedMaxStreak >= 30, today, unlockedNow);
        unlockIfEligible(db, ACH_PERFECT_DAY, completedDaysTotal >= 1, today, unlockedNow);

        int unlockedCount = countRows("SELECT COUNT(*) FROM " + TABLE_ACHIEVEMENTS + " WHERE " + COL_ACHIEVEMENT_UNLOCKED + " = 1");
        setAchievementProgress(db, ACH_SYSTEM_RECOGNIZES, Math.min(unlockedCount, 20));
        setAchievementProgress(db, ACH_ABSOLUTE_DOMINION, Math.min(unlockedCount, 30));

        unlockIfEligible(db, ACH_SYSTEM_RECOGNIZES, unlockedCount >= 20, today, unlockedNow);
        unlockIfEligible(db, ACH_ABSOLUTE_DOMINION, unlockedCount >= 30, today, unlockedNow);

        return unlockedNow;
    }

    private void setAchievementProgress(SQLiteDatabase db, int achievementId, int progress) {
        ContentValues v = new ContentValues();
        v.put(COL_ACHIEVEMENT_PROGRESS, Math.max(progress, 0));
        db.update(TABLE_ACHIEVEMENTS, v, COL_ID + " = ?", new String[]{String.valueOf(achievementId)});
    }

    private void unlockIfEligible(SQLiteDatabase db, int achievementId, boolean condition, String today, ArrayList<String> unlockedNow) {
        if (!condition) return;

        Cursor c = db.rawQuery("SELECT " + COL_ACHIEVEMENT_UNLOCKED + ", " + COL_ACHIEVEMENT_NAME +
                " FROM " + TABLE_ACHIEVEMENTS + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(achievementId)});
        if (!c.moveToFirst()) {
            c.close();
            return;
        }

        boolean unlocked = c.getInt(0) == 1;
        String name = c.getString(1);
        c.close();

        if (unlocked) return;

        ContentValues v = new ContentValues();
        v.put(COL_ACHIEVEMENT_UNLOCKED, 1);
        v.put(COL_ACHIEVEMENT_UNLOCK_DATE, today);
        db.update(TABLE_ACHIEVEMENTS, v, COL_ID + " = ?", new String[]{String.valueOf(achievementId)});
        unlockedNow.add(name);
    }

    private int countRows(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int val = 0;
        if (c.moveToFirst()) val = c.getInt(0);
        c.close();
        return val;
    }

    private boolean exists(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        boolean val = c.moveToFirst();
        c.close();
        return val;
    }

    private int sumColumn(String column) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COALESCE(SUM(" + column + "), 0) FROM " + TABLE_DAILY_PROGRESS, null);
        int val = 0;
        if (c.moveToFirst()) val = c.getInt(0);
        c.close();
        return val;
    }

    private long sumColumnLong(String column) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COALESCE(SUM(" + column + "), 0) FROM " + TABLE_DAILY_PROGRESS, null);
        long val = 0;
        if (c.moveToFirst()) val = c.getLong(0);
        c.close();
        return val;
    }

    private int sumLastDays(String column, int days, String today) {
        String start = getPastDate(today, days - 1);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COALESCE(SUM(" + column + "), 0) FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + COL_DATE + " >= ? AND " + COL_DATE + " <= ?", new String[]{start, today});
        int val = 0;
        if (c.moveToFirst()) val = c.getInt(0);
        c.close();
        return val;
    }

    private long sumLastDaysLong(String column, int days, String today) {
        String start = getPastDate(today, days - 1);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COALESCE(SUM(" + column + "), 0) FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + COL_DATE + " >= ? AND " + COL_DATE + " <= ?", new String[]{start, today});
        long val = 0;
        if (c.moveToFirst()) val = c.getLong(0);
        c.close();
        return val;
    }

    private int countLastDaysCompleted(int days, String today) {
        String start = getPastDate(today, days - 1);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + COL_DATE + " >= ? AND " + COL_DATE + " <= ? AND " + COL_DAILY_ALL_COMPLETED + " = 1",
                new String[]{start, today});
        int val = 0;
        if (c.moveToFirst()) val = c.getInt(0);
        c.close();
        return val;
    }

    private String getPastDate(String today, int daysBack) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        try {
            Date base = sdf.parse(today);
            if (base == null) throw new ParseException("null date", 0);
            Calendar cal = Calendar.getInstance();
            cal.setTime(base);
            cal.add(Calendar.DAY_OF_YEAR, -daysBack);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -daysBack);
            return sdf.format(cal.getTime());
        }
    }

    private int getMaxStreakForColumn(String column) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DATE + " FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + column + " = 1 ORDER BY " + COL_DATE + " ASC", null);

        int maxStreak = 0;
        int currentStreak = 0;
        String prevDate = null;

        while (c.moveToNext()) {
            String date = c.getString(0);
            if (prevDate == null) {
                currentStreak = 1;
            } else if (isNextDay(prevDate, date)) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
            }
            prevDate = date;
        }

        c.close();
        return maxStreak;
    }

    private int getCurrentStreakForColumn(String column, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DATE + " FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + column + " = 1 ORDER BY " + COL_DATE + " ASC", null);

        int currentStreak = 0;
        String prevDate = null;
        String streakEndDate = null;

        while (c.moveToNext()) {
            String date = c.getString(0);
            if (prevDate == null) {
                currentStreak = 1;
            } else if (isNextDay(prevDate, date)) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
            prevDate = date;
            streakEndDate = date;
        }

        c.close();
        if (streakEndDate == null || !streakEndDate.equals(today)) {
            return 0;
        }
        return currentStreak;
    }

    private boolean hasSameTimeRangeStreak(int targetStreak) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_DATE + ", " + COL_DAILY_COMPLETION_HOUR +
                " FROM " + TABLE_DAILY_PROGRESS +
                " WHERE " + COL_DAILY_ALL_COMPLETED + " = 1 AND " + COL_DAILY_COMPLETION_HOUR + " >= 0 ORDER BY " + COL_DATE + " ASC", null);

        int streak = 0;
        String prevDate = null;
        int prevBucket = -1;

        while (c.moveToNext()) {
            String date = c.getString(0);
            int hour = c.getInt(1);
            int bucket = mapHourToBucket(hour);

            if (prevDate == null) {
                streak = 1;
            } else if (isNextDay(prevDate, date) && bucket == prevBucket) {
                streak++;
            } else {
                streak = 1;
            }

            if (streak >= targetStreak) {
                c.close();
                return true;
            }

            prevDate = date;
            prevBucket = bucket;
        }

        c.close();
        return false;
    }

    private int mapHourToBucket(int hour) {
        if (hour < 10) return 0;
        if (hour < 14) return 1;
        if (hour < 18) return 2;
        if (hour < 22) return 3;
        return 4;
    }

    // Progress tracking

    public static class MissionProgress {
        public final int missionId;
        public final String name;
        public final String unitLabel;
        public final int weeklyCount;
        public final int totalCount;

        public MissionProgress(int missionId, String name, String unitLabel, int weeklyCount, int totalCount) {
            this.missionId = missionId;
            this.name = name;
            this.unitLabel = unitLabel;
            this.weeklyCount = weeklyCount;
            this.totalCount = totalCount;
        }
    }

    public List<MissionProgress> getMissionsProgress(Context context, String today) {
        List<MissionProgress> list = new ArrayList<>();

        String unitReps = context.getString(R.string.progress_unit_reps);
        String unitMin = context.getString(R.string.progress_unit_min);
        String unitSteps = context.getString(R.string.progress_unit_steps);

        // Cantidades fijas por misión
        int q1 = 50; // Sentadillas
        int q2 = 50; // Abdominales
        int q3 = 50; // Flexiones
        int q4 = 50; // Barra bíceps
        int q6 = 10; // Meditación (min)
        int q7 = 5;  // Ducha fría (min)
        int q8 = 15; // Lectura (min)

        // Totales semanales (7 días)
        int w1 = sumLastDays(COL_DAILY_M1, 7, today) * q1;
        int w2 = sumLastDays(COL_DAILY_M2, 7, today) * q2;
        int w3 = sumLastDays(COL_DAILY_M3, 7, today) * q3;
        int w4 = sumLastDays(COL_DAILY_M4, 7, today) * q4;
        int w5 = (int) sumLastDaysLong(COL_DAILY_STEPS, 7, today);
        int w6 = sumLastDays(COL_DAILY_M6, 7, today) * q6;
        int w7 = sumLastDays(COL_DAILY_M7, 7, today) * q7;
        int w8 = sumLastDays(COL_DAILY_M8, 7, today) * q8;

        // Totales acumulados
        int t1 = sumColumn(COL_DAILY_M1) * q1;
        int t2 = sumColumn(COL_DAILY_M2) * q2;
        int t3 = sumColumn(COL_DAILY_M3) * q3;
        int t4 = sumColumn(COL_DAILY_M4) * q4;
        int t5 = (int) sumColumnLong(COL_DAILY_STEPS);
        int t6 = sumColumn(COL_DAILY_M6) * q6;
        int t7 = sumColumn(COL_DAILY_M7) * q7;
        int t8 = sumColumn(COL_DAILY_M8) * q8;

        list.add(new MissionProgress(1, "Sentadillas", unitReps, w1, t1));
        list.add(new MissionProgress(2, "Abdominales", unitReps, w2, t2));
        list.add(new MissionProgress(3, "Flexiones", unitReps, w3, t3));
        list.add(new MissionProgress(4, "Barra Bíceps", unitReps, w4, t4));
        list.add(new MissionProgress(5, "Distancia", unitSteps, w5, t5));
        list.add(new MissionProgress(6, "Meditación", unitMin, w6, t6));
        list.add(new MissionProgress(7, "Ducha Fría", unitMin, w7, t7));
        list.add(new MissionProgress(8, "Lectura", unitMin, w8, t8));

        return list;
    }

    private boolean isNextDay(String prevDate, String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        try {
            Date prev = sdf.parse(prevDate);
            Date curr = sdf.parse(currentDate);
            if (prev == null || curr == null) return false;

            Calendar cal = Calendar.getInstance();
            cal.setTime(prev);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            return sdf.format(cal.getTime()).equals(sdf.format(curr));
        } catch (ParseException e) {
            return false;
        }
    }
}
