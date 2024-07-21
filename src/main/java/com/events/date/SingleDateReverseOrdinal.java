package com.events.date;

import com.events.Util;
import com.events.date.Calendar;
import com.events.date.DateMatcher;
import com.events.date.DateMeta;

import java.util.List;
import java.util.regex.Matcher;

import static com.events.date.Calendar.defaultYearFull;
import static com.events.date.M_Month.M_MONTH_ENG;
import static com.events.date.M_Month.M_MONTH_NEG_LOOKAHEAD;
import static com.events.date.M_Static.*;
import static com.events.date.M_Weekday.M_WEEKDAYO_ENG;
import static com.events.date.Months.MONTHS_STANDARD_ENG;
import static com.events.Util.list;

class SingleDateReverseOrdinal extends DateMatcher {
    public DateMeta matchInternal(String text) {
        List<Calendar.Date> dates = list();
        text = cleanText(text);
        String day_month_year = M_START_INDICATORSO_ENG + M_WEEKDAYO_ENG + "\\s*" + M_MONTH_ENG + "\\s+" + M_DAY_ORDINAL + "(:)?" + MW + M_MONTH_NEG_LOOKAHEAD;
        DateMeta date_internal = new DateMeta();
        Matcher matcher = Util.matcher(day_month_year, text);
        while (matcher.find()) {
            getIndexPairs(text, date_internal, matcher);
            Calendar.Date date = new Calendar.Date();
            date.dateDay = String.valueOf(Integer.valueOf(matcher.group(4)));
            date.dateMonth = MONTHS_STANDARD_ENG.get(matcher.group(3));
            date.dateYear = defaultYearFull(date.dateMonth);
            date.indexPairs.add(new Util.Multi<>(matcher.start(), matcher.end()));
            date.note = this.getClass().getSimpleName();
            dates.add(date);
        }
        date_internal.dateList = dates;
        return date_internal;
    }
}