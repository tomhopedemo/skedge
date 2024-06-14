package com.arcta.events;
import java.util.Map;
import java.util.regex.Matcher;
import static com.arcta.events.Util.map;
import static com.arcta.events.Weekdays.WEEKDAYS_STANDARD_ENG;
import static com.arcta.events.M_Static.MWO;
import static com.arcta.events.M_Static.SPACES;
import static com.arcta.events.M_Weekday.M_WEEKDAY_ENG;
class SingleTimeWeekdayBracketed extends DateMatcher.DayTimeMatcher {
    Map<String, Time> match(String text) {         Map<String, Time> weekdayTimes = map();
        String time_date = "\\(" + MWO +  "([0-9]{1,2})" + "([:|\\.][0-5][0|5])?" + MWO + "(am|pm)" + SPACES + M_WEEKDAY_ENG + MWO + "\\)";
        Matcher matcher = Util.matcher(time_date, text);
        while (matcher.find()) { Time time = new Time();
            time.setHour(String.valueOf(Integer.valueOf(matcher.group(1).trim())));
            if (Integer.parseInt(time.getHour()) > 24) continue;
            time.timeMinute = matcher.group(2) == null ? "00" : matcher.group(2).substring(1);
            time.amPm = matcher.group(3);
            time.provenance = this.getClass().getSimpleName();
            time.convertTo24H();
            weekdayTimes.put(WEEKDAYS_STANDARD_ENG.getKey(matcher.group(4)), time);} return weekdayTimes;}} //(7.30pm saturday)
