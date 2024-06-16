package com.arcta.events;

import java.util.List;
import java.util.regex.Matcher;

import static com.arcta.events.Calendar.defaultYearFull;
import static com.arcta.events.M_Month.M_MONTH_ENG;
import static com.arcta.events.M_Static.M_DAY;
import static com.arcta.events.M_Static.SPACES;
import static com.arcta.events.M_Weekday.M_WEEKDAY_ENG;
import static com.arcta.events.Util.Months.MONTHS_STANDARD_ENG;
import static com.arcta.events.Util.list;

class SingleDateMWDAlt extends DateMatcher {
    public DateMeta matchInternal(String text) {
        List<Calendar.Date> dates = list();
        text = cleanText(text);
        String month_year_weekday_day = M_MONTH_ENG + SPACES + M_WEEKDAY_ENG + SPACES + M_DAY;
        DateMeta date_internal = new DateMeta();
        Matcher matcher = Util.matcher(month_year_weekday_day, text);
        while (matcher.find()) {
            getIndexPairs(text, date_internal, matcher);
            Calendar.Date date = new Calendar.Date();
            date.dateDay = String.valueOf(Integer.valueOf(matcher.group(3)));
            date.dateMonth = MONTHS_STANDARD_ENG.get(matcher.group(1));
            date.dateYear = defaultYearFull(date.dateMonth);
            date.indexPairs.add(new Util.Multi<>(matcher.start(), matcher.end()));
            date.note = this.getClass().getSimpleName();
            dates.add(date);
        }
        date_internal.dateList = dates;
        return date_internal;
    }
}