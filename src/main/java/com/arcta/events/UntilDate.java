package com.arcta.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import static com.arcta.events.M_Static.M_DAY_MONTH_YEARO;
import static com.arcta.events.M_Static.SPACES;
import static com.arcta.events.M_Weekday.M_WEEKDAYO_SPACE_AFTER_ENG;
import static com.arcta.events.Util.Months.MONTHS_STANDARD_ENG;
import static com.arcta.events.Util.set;

class UntilDate extends DateMatcher {
    public DateMeta matchInternal(String text) {
        Set<List<Calendar.Date>> between_set = set();
        Matcher matcher = Util.matcher("(on now )?until" + SPACES + M_WEEKDAYO_SPACE_AFTER_ENG + M_DAY_MONTH_YEARO, text);
        DateMeta dateInternal = new DateMeta();
        while (matcher.find()) {
            getIndexPairs(text, dateInternal, matcher);
            Calendar.Date date = new Calendar.Date();
            date.dateDay = matcher.group(3) == null ? "1" : matcher.group(3);
            date.dateMonth = MONTHS_STANDARD_ENG.get(matcher.group(5));
            date.dateYear = "20" + (matcher.group(7) == null ? Calendar.defaultYearAbbrev(date.dateMonth) : matcher.group(7));
            List<Calendar.Date> dates = constructIntermediateDates(Calendar.currentDate(), date);
            if (Util.empty(dates)) continue;
            for (Calendar.Date dateIterate : dates) {
                Util.MultiList<Integer, Integer> indexPairsToRemove = new Util.MultiList<>();
                indexPairsToRemove.add(new Util.Multi<>(matcher.start(), matcher.end()));
                dateIterate.indexPairs = indexPairsToRemove;
            }
            dates.forEach(d -> d.note = getClass().getSimpleName());
            between_set.add(dates);
        }
        dateInternal.betweenList = new ArrayList<>(between_set);
        dateInternal.between = Util.get(dateInternal.betweenList, 0);
        return dateInternal;
    }
}