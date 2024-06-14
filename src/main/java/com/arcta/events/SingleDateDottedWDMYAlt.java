package com.arcta.events;
import java.util.List;
import java.util.regex.Matcher;
import static com.arcta.events.Util.Months.MONTHS_STANDARD_ENG;
import static com.arcta.events.M_Month.M_MONTH_ENG;
import static com.arcta.events.M_Static.M_DAY_ORDINALO;
import static com.arcta.events.M_Static.M_YEAR;
import static com.arcta.events.M_Weekday.M_WEEKDAY_ENG;
import static com.arcta.events.Util.list;
class SingleDateDottedWDMYAlt extends DateMatcher {
    public DateMeta matchInternal(String text) { List<Calendar.Date> dates = list();
        text = cleanText(text);
        String regex = "\\b" + M_WEEKDAY_ENG + "\\." + M_DAY_ORDINALO + "\\." + M_MONTH_ENG + "\\." + M_YEAR + "\\b";
        DateMeta date_internal = new DateMeta();
        Matcher matcher = Util.matcher(regex, text);
        while (matcher.find()) { getIndexPairs(text, date_internal, matcher);
            Calendar.Date date = new Calendar.Date();
            date.dateDay = String.valueOf(Integer.valueOf(matcher.group(2)));
            date.dateMonth = MONTHS_STANDARD_ENG.get(matcher.group(4));
            String year_suffix = matcher.group(6);
            if (Integer.parseInt(year_suffix) > 25) continue;
            date.dateYear = "20" + year_suffix;
            date.indexPairs.add(matcher.start(), matcher.end());
            date.note = this.getClass().getSimpleName();
            dates.add(date);}
        date_internal.dateList = dates; return date_internal;}}