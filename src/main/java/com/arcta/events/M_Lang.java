package com.arcta.events;

import java.util.List;
import java.util.Map;

import static com.arcta.events.M_Month.M_MONTH_DEU;
import static com.arcta.events.M_Month.M_MONTH_ENG;
import static com.arcta.events.M_Static.*;
import static com.arcta.events.M_Weekday.*;
import static com.arcta.events.Util.Lang.DEU;
import static com.arcta.events.Util.Lang.ENG;
import static com.arcta.events.Util.Months.MONTHS_STANDARD_DEU;
import static com.arcta.events.Util.Months.MONTHS_STANDARD_ENG;
import static com.arcta.events.Util.map;
import static com.arcta.events.Weekdays.WEEKDAYS_STANDARD_DEU;
import static com.arcta.events.Weekdays.WEEKDAYS_STANDARD_ENG;

class M_Lang {
    static final Map<Util.Lang, Map<String, String>> LANG_MONTHS_STANDARD;
    static final Map<Util.Lang, Util.MapList<String, String>> LANG_WEEKDAYS_STANDARD;
    static final Map<Util.Lang, String> LANG_M_MONTH;
    static final Map<Util.Lang, String> LANG_M_WEEKDAY;
    static final Map<Util.Lang, String> LANG_M_START_INDICATORS;
    static final Map<Util.Lang, String> LANG_M_WEEKDAYO_SPACE_AFTER;
    static final Map<Util.Lang, String> LANG_M_ORDINAL;
    static final Map<Util.Lang, List<String>> LANG_PHRASES_TO_CLEAN;
    static final Map<Util.Lang, List<String>> LANG_LOWERCASE;
    static List<String> PHRASES_TO_CLEAN_ENG = Util.list("special event", "new exhibition", "limited availability", "available", "order tickets", "buy tickets", "book tickets", "book ticket", "book online", "book a table", "booking required", "get tickets", "additional tickets", "tickets coming soon", "tickets", "booking fee", "booking", "members go free", "a free", "free entry", "free event", "foyer", "add to wishlist", "bank holiday sunday", "bank holiday", "lecture series", "seminar series", "on the gate", "cash on the door", "on the door", "ticket holder", "ticket price", "advance day ticket", "day pass", "google calendar ics", "last entry", "google maps", "google map", "visually impaired only", "visually impaired", "public", "for visit", "register", "on-sale", "on sale now", "on sale", "dates and times vary", "dates & times", "dates and times", "various dates", "various times", "find out more", "more details to follow", "more details", "find out", "read more", "doors open at", "book now", "category:", "more information", "more info", "view availability", "find tickets", "tickets now", "this week", "this month", "short courses", "every week", "now playing", "buy now", "buy");

    static {
        LANG_LOWERCASE = map();
        LANG_LOWERCASE.put(ENG, Context.LanguageContext.LOWERCASE_ENG);
        LANG_PHRASES_TO_CLEAN = map();
        LANG_PHRASES_TO_CLEAN.put(ENG, PHRASES_TO_CLEAN_ENG);
        LANG_M_ORDINAL = map();
        LANG_M_ORDINAL.put(ENG, M_ORDINAL_ENG);
        LANG_M_ORDINAL.put(DEU, M_EMPTY);
        LANG_M_WEEKDAYO_SPACE_AFTER = map();
        LANG_M_WEEKDAYO_SPACE_AFTER.put(ENG, M_WEEKDAYO_SPACE_AFTER_ENG);
        LANG_M_WEEKDAYO_SPACE_AFTER.put(DEU, M_WEEKDAYO_SPACE_AFTER_DEU);
        LANG_M_WEEKDAY = map();
        LANG_M_WEEKDAY.put(ENG, M_WEEKDAY_ENG);
        LANG_M_WEEKDAY.put(DEU, M_WEEKDAY_DEU);
        LANG_M_START_INDICATORS = map();
        LANG_M_START_INDICATORS.put(ENG, M_START_INDICATORS_ENG);
        LANG_M_START_INDICATORS.put(DEU, M_EMPTY);
        LANG_M_MONTH = map();
        LANG_M_MONTH.put(ENG, M_MONTH_ENG);
        LANG_M_MONTH.put(DEU, M_MONTH_DEU);
        LANG_MONTHS_STANDARD = map();
        LANG_MONTHS_STANDARD.put(ENG, MONTHS_STANDARD_ENG);
        LANG_MONTHS_STANDARD.put(DEU, MONTHS_STANDARD_DEU);
        LANG_WEEKDAYS_STANDARD = map();
        LANG_WEEKDAYS_STANDARD.put(ENG, WEEKDAYS_STANDARD_ENG);
        LANG_WEEKDAYS_STANDARD.put(DEU, WEEKDAYS_STANDARD_DEU);
    }
}
