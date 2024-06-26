package com.arcta.events;

import java.util.List;
import java.util.regex.Matcher;

import static com.arcta.events.HyphenMatchers.M_HYPHENS_UNDERSCORES_DOTS;
import static com.arcta.events.M_Static.MONTHS_ORDER;
import static com.arcta.events.M_Static.M_YEAR_STRONG;
import static com.arcta.events.Util.list;

class SingleDateHyphenatedYMD extends DateMatcher {
    public DateMeta matchInternal(String text) {
        List<Calendar.Date> dates = list();
        String regex = M_YEAR_STRONG + M_HYPHENS_UNDERSCORES_DOTS + "([0-9]{2})" + "\\2" + "([0-9]{2})";
        Matcher matcher = Util.matcher(regex, text);
        DateMeta dateMeta = new DateMeta();
        while (matcher.find()) {
            getIndexPairs(text, dateMeta, matcher);
            Calendar.Date date = new Calendar.Date();
            Integer date_int = Integer.valueOf(matcher.group(4));
            if (date_int > 31) continue;
            date.dateDay = String.valueOf(date_int);
            Integer monthInteger = Integer.valueOf(matcher.group(3));
            if (monthInteger > 12 || monthInteger <= 0) continue;
            date.dateMonth = MONTHS_ORDER.get(monthInteger - 1);
            date.dateYear = matcher.group(1);
            Util.MultiList<Integer, Integer> indexPairsToRemove = new Util.MultiList<>();
            indexPairsToRemove.add(new Util.Multi<>(matcher.start(), matcher.end()));
            date.indexPairs = indexPairsToRemove;
            date.note = getClass().getSimpleName();
            dates.add(date);
        }
        dateMeta.dateList = dates;
        return dateMeta;
    }
}                      // 2020-04-01