package com.events;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.events.date.M_Static.M_EMPTY;
import static java.lang.Integer.parseInt;
import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.String.valueOf;

public class Util {
    public static final List<String> HYPHENS = Util.list("-", "\u2010", "\u2013", "\u2014", "\u002d");

    public static String substring(int startInclusive, int endExclusive, String input) {
        int length = input.length();
        return input.substring(Math.max(0, Math.min(startInclusive, length)), Math.min(endExclusive, length));
    }

    public static String lowercase(String string) {
        if (string == null) return null;
        return string.toLowerCase();
    }

    public static <T> T getFirst(Set<T> set) {
        if (empty(set)) return null;
        return set.iterator().next();
    }

    public static <T extends Comparable> List<T> sort(Collection<T> collection) {
        ArrayList<T> sort = new ArrayList<>(collection);
        Collections.sort(sort);
        return sort;
    }

    public static List<Integer> integerList(int length) {
        return integerList(0, length - 1);
    }

    public static List<Integer> integerList(int startInclusive, int endInclusive) {
        List<Integer> toReturn = new ArrayList<>();
        for (int i = startInclusive; i <= endInclusive; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    public static Integer proximalIndex(String whole, int index, String searchTerm, int radius) {
        if (Util.empty(whole)) return null;
        if (Util.empty(searchTerm)) return null;
        int startIndex = Math.max(0, index - radius);
        int endIndex = Math.min(whole.length() - 1, index + radius);
        String substring = Util.substring(startIndex, endIndex, whole);
        int indexOfSearchTerm = substring.indexOf(searchTerm);
        if (indexOfSearchTerm == -1) return null;
        return indexOfSearchTerm + startIndex;
    }

    public static String removeBetween(int startInclusive, int endExclusive, String input) {
        if (Util.empty(input)) return input;
        String part1 = substring(0, startInclusive, input);
        String part2 = substring(endExclusive, input.length(), input);
        return part1 + part2;
    }

    public static String removeLast(String toRemove, String input) {
        if (Util.empty(input)) return input;
        if (Util.empty(toRemove)) return input;
        String quote = Pattern.quote(toRemove);
        Matcher matcher = Util.matcher(quote, input);
        Integer startIndex = null;
        Integer endIndex = null;
        while (matcher.find()) {
            startIndex = matcher.start();
            endIndex = matcher.end();
        }
        if (startIndex == null) return input;
        return removeBetween(startIndex, endIndex, input);
    }

    public static String uppercaseFirstLetter(String input) {
        return uppercaseFirstLetter(input, charmap);
    }

    public static String uppercaseFirstLetter(String input, Map<String, String> lowercaseUppercaseCharmap) {
        if (Util.empty(input)) return input;
        String firstCharacter = input.substring(0, 1);
        String foreignChar = lowercaseUppercaseCharmap.get(Util.safeNull(firstCharacter));
        if (foreignChar != null) return foreignChar + (input.length() > 1 ? input.substring(1) : "");
        if (!Util.safeNull(firstCharacter).matches("[a-z]")) return input;
        return firstCharacter.toUpperCase() + (input.length() > 1 ? input.substring(1) : "");
    }

    public static String substringRemoveLast(StringBuilder sb, Integer numToRemove) {
        if (sb.length() == 0) {
            return "";
        } else {
            if (numToRemove >= sb.length()) return "";
            return sb.substring(0, sb.length() - numToRemove);
        }
    }

    public static String substringRemoveLast(String sb) {
        if (sb.length() == 0) {
            return "";
        } else {
            return sb.substring(0, sb.length() - 1);
        }
    }

    public static boolean endsWith(String input, List<String> endings) {
        for (String string : endings) {
            if (input.endsWith(string)) return true;
        }
        return false;
    }

    public static int indexOf(String string, List<String> toFind) {
        if (string == null || Util.empty(toFind)) return -1;
        for (String s : toFind) {
            int index = string.indexOf(s);
            if (index > 0) return index;
        }
        return -1;
    }

    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        List<T> union = new ArrayList<T>();
        if (!Util.empty(a)) union.addAll(a);
        if (!Util.empty(b)) union.addAll(b);
        return union;
    }

    public static boolean intersection(Collection a, Collection b) {
        if (Util.empty(a) || Util.empty(b)) return false;
        for (Object o : a) {
            for (Object o1 : b) {
                if (o.equals(o1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> Set<T> intersect(Collection<T> a, Collection<T> b) {
        Set<T> intersect = set();
        if (Util.empty(a) || Util.empty(b)) return intersect;
        for (T t : a) {
            if (b.contains(t)) {
                intersect.add(t);
            }
        }
        return intersect;
    }

    public static boolean empty(MultiList multiList) {
        return (multiList == null || empty(multiList.underlying));
    }

    public static <A> boolean empty(A[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean empty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean empty(Multi multi) {
        if (multi == null) return true;
        if (multi.a == null && multi.b == null) return true;
        return false;
    }

    public static boolean empty(Collection collection) {
        return (collection == null || collection.size() == 0 || (collection.size() == 1 && collection.iterator().next() == null));
    }

    public static boolean empty(MapList mapList) {
        return (mapList == null || mapList.mapList == null || empty(mapList.mapList));
    }

    public static boolean empty(StringMutable stringMutable) {
        if (stringMutable == null) return true;
        return empty(stringMutable.string);
    }

    public static boolean empty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static void addAll(Collection existing, Collection newElements) {
        addAll(existing, newElements, null);
    }

    public static void addAll(MultiList existing, MultiList newElements) {
        if (existing == null || newElements == null) return;
        addAll(existing.underlying, newElements.underlying, null);
    }

    public static <T> void addAll(Collection<T> existing, Collection<T> newElements, Integer limit) {
        if (existing == null || newElements == null) return;
        if (limit != null) {
            int remaining = limit - existing.size();
            if (remaining <= 0) return;
            List<T> sublist = sublist(new ArrayList<T>(newElements), remaining);
            existing.addAll(sublist);
        } else {
            existing.addAll(newElements);
        }
    }

    public static <T> List<T> sublist(List<T> list, int num) {
        if (list == null) return null;
        return list.subList(0, Math.min(num, list.size()));
    }

    public static <S, T> MultiList<S, T> sublist(MultiList<S, T> list, int num) {
        MultiList<S, T> sublist = new MultiList<S, T>();
        if (list == null || list.underlying == null) return null;
        sublist.underlying = sublist(list.underlying, num);
        return sublist;
    }

    public static <T> List<T> sublist(List<T> list, int fromInc, int toExc) {
        if (list == null) return null;
        if (list.size() == 0 || fromInc >= list.size()) return list();
        return list.subList(fromInc, Math.min(toExc, list.size()));
    }

    public static <T> T interpolateBefore(MultiList<Integer, T> multiList, Integer key) {
        if (empty(multiList)) return null;
        Integer bestMatch = null;
        for (Integer s : multiList.getAList()) {
            if (s > key) continue;
            if (bestMatch == null) bestMatch = s;
            if (s > bestMatch) bestMatch = s;
        }
        if (bestMatch == null) return null;
        return multiList.getBOne(bestMatch);
    }

    public static List<Integer> indices(String text, List<String> toFind) {
        if (text == null) return null;
        if (empty(toFind)) return list();
        List<Integer> toReturn = list();
        for (String string : toFind) {
            int i = text.indexOf(string);
            if (i > -1) toReturn.add(i);
        }
        return toReturn;
    }

    public static boolean empty(Integer integer) {
        return (integer == null || integer.equals(0));
    }

    public static <T> T last(List<T> collection) {
        return collection.get(collection.size() - 1);
    }

    public static <E> HashSet<E> set(E... objects) {
        HashSet<E> objectsSet = new HashSet<>();
        for (E object : objects) {
            objectsSet.add(object);
        }
        return objectsSet;
    }

    public static boolean empty(NList collection) {
        return (collection == null || collection.size() == 0 || (collection.size() == 1 && collection.underlying.iterator().next() == null));
    }

    public static <T> void safeAdd(Collection<T> existing, Collection<T> newItems) {
        if (existing == null || newItems == null) return;
        existing.addAll(newItems);
    }

    public static Integer minDifferenceDirectional(List<Integer> collection, int value) {
        if (empty(collection)) return null;
        List<Integer> distancesBetween = list();
        for (Integer element : collection) {
            int difference = value - element;
            if (difference >= 0) distancesBetween.add(difference);
        }
        return min(distancesBetween);
    }

    public static Integer min(Collection<Integer> collection) {
        if (empty(collection)) return null;
        Integer min = null;
        for (Integer candidate : collection) {
            if (min == null || candidate < min) min = candidate;
        }
        return min;
    }

    public static Integer minDifference(List<Integer> collection, int value) {
        if (empty(collection)) return null;
        List<Integer> distancesBetween = list();
        collection.forEach(avoidIndex -> distancesBetween.add(Math.abs(value - avoidIndex)));
        return min(distancesBetween);
    }

    public static boolean overlap(Integer startA, Integer endA, Integer startB, Integer endB) {
        return !(startA > endB || startB > endA);
    }

    public static <A, B> void addSafe(Map<A, B> existing, Map<A, B> toAdd) {
        if (existing == null) return;
        if (empty(toAdd)) return;
        existing.putAll(toAdd);
    }

    public static String trim(String string) {
        if (string == null) return null;
        return string.trim().replaceAll("^\u00A0", "").trim();
    }

    public static <E> ArrayList<E> list(E... objects) {
        ArrayList<E> objectsList = new ArrayList<>();
        for (E object : objects) {
            objectsList.add(object);
        }
        return objectsList;
    }

    public static String safeNull(String value) {
        return value == null ? "" : value;
    }

    public static <T, S> T safeA(Multi<T, S> multi) {
        if (multi == null) return null;
        return multi.a;
    }

    public static <T, S> S safeB(Multi<T, S> multi) {
        if (multi == null) return null;
        return multi.b;
    }

    public static NList splitSafe(String string, String delimiterRegex) {
        if (string == null) {
            return split("", delimiterRegex);
        } else {
            return split(string, delimiterRegex);
        }
    }

    public static String padleft(String input, Character pad, Integer maxChars) {
        if (input == null || pad == null) return null;
        if (input.length() >= maxChars) return input;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxChars - input.length(); i++) {
            sb.append(pad);
        }
        sb.append(input);
        return sb.toString();
    }

    public static <S extends Comparable<S>, T> void sortA(MultiList<S, T> multiList) {
        if (empty(multiList)) return;
        List<Multi<S, T>> list = multiList.underlying;
        list.sort((Comparator<Multi>) (multi1, multi2) -> ((Comparable<S>) multi1.a).compareTo((S) multi2.a));
    }

    public static List<String> splitList(String string, String delimiterRegex) {
        NList split = split(string, delimiterRegex);
        if (split == null) return null;
        return split.underlying;
    }

    public static <S, T> MultiList multilist(Multi<S, T> m) {
        MultiList<S, T> multilist = new MultiList<>();
        multilist.add(m);
        return multilist;
    }

    public static boolean containsIgnoreCase(String string, List<String> list) {
        return !empty(getIgnoreCase(list, string));
    }

    public static String removeIndices(String string, Collection<Integer> indicesToRemove) {
        char[] chars = string.toCharArray();
        for (Integer integer : indicesToRemove) {
            chars[integer] = '\u30F5';
        }
        List<Character> newCharacters = list();
        for (char aChar : chars) {
            if (aChar != '\u30F5') newCharacters.add(aChar);
        }
        return stringChar(newCharacters);
    }

    public static String stringChar(List<Character> characterArray) {
        char[] array = new char[characterArray.size()];
        for (int i = 0; i < characterArray.size(); i++) {
            array[i] = characterArray.get(i);
        }
        return new String(array);
    }

    public static List<Integer> between(int startInclusive, int endInclusive) {
        List<Integer> toReturn = list();
        for (int i = startInclusive; i <= endInclusive; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    public static NList split(String string, String delimiterRegex) {
        if (string == null) return null;
        List<String> list = new ArrayList<>(Arrays.asList(string.split(delimiterRegex)));
        NList nlist = new NList();
        nlist.underlying = list;
        nlist.splitDelimiter = delimiterRegex;
        return nlist;
    }

    public static <T> List<T> safeNull(List<T> a) {
        return empty(a) ? new ArrayList<T>() : a;
    }

    public static <T> Set<T> safeNull(Set<T> a) {
        return empty(a) ? new HashSet<T>() : a;
    }

    public static <T> T get(List<T> list, int index) {
        if (empty(list)) return null;
        if (index >= list.size()) return null;
        if (index == -1) return null;
        return list.get(index);
    }

    public static String string(Collection collection, String delimiter) {
        if (collection == null) return null;
        return out(collection, delimiter);
    }

    public static String substringRemoveLast(StringBuilder sb) {
        if (sb.length() == 0) {
            return "";
        } else {
            return sb.substring(0, sb.length() - 1);
        }
    }

    public static boolean startsWith(String input, Collection<String> inputs) {
        return Util.startsWithGet(input, inputs) != null;
    }

    public static String startsWithGet(String input, Collection<String> inputs) {
        if (inputs == null || input == null || input.equals("")) return null;
        for (String s : inputs) {
            if (input.startsWith(s)) {
                return s;
            }
        }
        return null;
    }

    public static String substringRemoveFirst(String sb) {
        return sb.length() == 0 ? "" : sb.substring(1);
    }

    public static <T> String out(Collection<T> collection, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T object : collection) {
            if (object != null) {
                String str = object.toString();
                if (str != null) {
                    sb.append(str);
                }
            }
            sb.append(separator);
        }
        return Util.substringRemoveLast(sb, separator.length());
    }

    public static <T> void sout(List<T> list) {
        sout(list, null);
    }

    public static void sout(String... strings) {
        System.out.println(string(strings));
    }

    public static <T> void sout(List<T> list, String pad) {
        if (empty(list)) return;
        pad = safeNull(pad);
        for (T s : list) {
            if (s == null) continue;
            sout(pad + s);
        }
    }

    public static Set<String> keysForValues(Set<String> values, Map<String, String> map) {
        Set<String> keys = set();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (values.contains(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public static <T> String string(T[] collection) {
        if (collection == null) return null;
        return out(Arrays.asList(collection), " ");
    }

    public static boolean containsIgnoreCase(String string, String substring) {
        if (string == null || substring == null) return false;
        return Util.lowercase(string).contains(Util.lowercase(substring));
    }

    public static boolean between(int middle, int aInclusive, int bInclusive) {
        return middle >= Math.min(aInclusive, bInclusive) && middle <= Math.max(aInclusive, bInclusive);
    }

    public static List<String> lowercase(Collection<String> collection) {
        if (collection == null) return null;
        List<String> lowercase = list();
        for (String s : collection) {
            if (s == null) continue;
            lowercase.add(s.toLowerCase());
        }
        return lowercase;
    }

    public static <T> T getElement(List<T> set, T object) {
        return get(set, set.indexOf(object));
    }

    public static Matcher matcher(String regex, String text) {
        return Pattern.compile(regex).matcher(text);
    }

    public static String getIgnoreCase(Collection<String> collection, String word) {
        for (String s : collection) {
            if (s.equalsIgnoreCase(word)) {
                return s;
            }
        }
        return null;
    }

    public static <A, B, C> A safeA(Multi3<A, B, C> multi) {
        if (multi == null) return null;
        return multi.a;
    }

    public static <A, B, C> B safeB(Multi3<A, B, C> multi) {
        if (multi == null) return null;
        return multi.b;
    }

    public static boolean endswithReverse(String ending, List<String> strings) {
        for (String string : strings) {
            if (string.endsWith(ending)) return true;
        }
        return false;
    }

    public static int size(Collection collection) {
        if (collection == null) return 0;
        return collection.size();
    }

    public static <T, S> void putIfAbsent(Map<T, S> existing, Map<T, S> additionalMap) {
        if (existing == null || empty(additionalMap)) return;
        for (T t : additionalMap.keySet()) {
            existing.putIfAbsent(t, additionalMap.get(t));
        }
    }

    public static boolean complete(Multi multi) {
        if (multi == null) return false;
        if (multi.a == null || multi.b == null) return false;
        return true;
    }

    public static boolean contains(String input, Collection<String> inputs) {
        return containsGet(input, inputs) != null;
    }

    public static boolean containsRev(String input, Collection<String> inputs) {
        if (inputs == null || input == null) return false;
        for (String element : inputs) {
            if (element.contains(input)) return true;
        }
        return false;
    }

    public static boolean contains(String input, String substring) {
        if (input == null || substring == null) return false;
        if (substring.length() == 0) return false;
        return input.contains(substring);
    }

    public static Integer safeNull(Integer value) {
        return value == null ? 0 : value;
    }

    public static <T> List<T> except(Collection<T> collection, Collection<T> exceptions) {
        if (collection == null) return null;
        if (empty(exceptions)) return new ArrayList<>(collection);
        return collection.stream().filter(object -> !exceptions.contains(object)).collect(Collectors.toList());
    }

    public static <T> String string(Collection<T> collection) {
        if (collection == null) return null;
        return out(collection, " ");
    }

    public static <S, T> List<T> bList(MultiList<S, T> multiList) {
        if (empty(multiList)) return list();
        return multiList.getBList();
    }

    public static String containsGet(String input, Collection<String> inputs) {
        if (inputs == null || input == null || input.equals("")) return null;
        for (String candidate : inputs) {
            if (input.contains(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public static <T extends Collection<String>> T addIfNotEmpty(T collection, String string) {
        if (empty(string) || collection == null) return collection;
        collection.add(string);
        return collection;
    }

    public static <T> List<T> distinct(List<T> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    public static double roundedToDisplayPerDay(double numPerDay) {
        int deltaPerc = (int) round(floor((numPerDay - floor(numPerDay)) * 100));
        return new Random().nextInt(100) < deltaPerc ? Math.ceil(numPerDay) : floor(numPerDay);
    }

    public static String cleanInt(String raw) {
        return valueOf(parseInt(raw.trim()));
    }

    public static Double divide(Integer numerator, Integer denominator) {
        return Double.valueOf(numerator) / Double.valueOf(denominator);
    }

    public static Integer diff(Integer a, Integer b) {
        if (a == null || b == null) return null;
        return Math.abs(a - b);
    }

    public static String opt(String input) {
        if (Util.empty(input) || M_EMPTY.equals(input)) {
            return input;
        }
        return input + "?";
    }

    public static final List<String> FILE_LOCKS_REQUESTED_BY_READER = list();
    public static final List<String> FILE_LOCKS_REQUESTED_BY_WRITER = list();

    public static void write(String path, String valueToWrite) {
        synchronized (path) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            if (file.getParentFile() != null) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            PrintWriter out = null;
            try {
                out = new PrintWriter(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.append(valueToWrite);
            out.close();
        }
    }

    public static void appendSafe(String path, String valueToWrite) {
        synchronized (path) {
            try {
                FILE_LOCKS_REQUESTED_BY_WRITER.add(path);
                while (FILE_LOCKS_REQUESTED_BY_READER.contains(path)) {
                    Thread.sleep(200);
                }
                append(path, valueToWrite);
            } catch (Exception ignored) {
            } finally {
                FILE_LOCKS_REQUESTED_BY_WRITER.remove(path);
            }
        }
    }

    public static void append(String path, String valueToWrite, String delimiter) {
        synchronized (path) {
            if (Util.read(path).size() == 0) {
                appendInternal(path, valueToWrite);
            } else {
                appendInternal(path, delimiter + valueToWrite);
            }
        }
    }

    public static void append(String path, String valueToWrite) {
        append(path, valueToWrite, "\n");
    }

    public static void write(String path, Collection<String> valueToWrite) {
        StringBuilder sb = new StringBuilder();
        for (String s : valueToWrite) {
            sb.append(s);
            sb.append("\n");
        }
        write(path, sb.toString());
    }

    public static void appendInternal(String path, String valueToWrite) {
        PrintWriter out = null;
        try {
            File file = new File(path);
            if (file.getParentFile() != null) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            out = new PrintWriter(new FileOutputStream(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(valueToWrite);
        out.close();
    }

    public static List<String> read(String file) {
        List<String> list = list();
        Scanner scanner;
        try {
            File source = new File(file);
            if (!source.exists()) return list();
            scanner = new Scanner(source);
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
            if (list.size() == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"));
                Stream<String> lines = reader.lines();
                if (lines != null) {
                    list = lines.collect(Collectors.toList());
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String readString(String file) {
        StringBuilder sb = new StringBuilder();
        Scanner scanner;
        try {
            File source = new File(file);
            if (!source.exists()) return null;
            scanner = new Scanner(source);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return null;
        }
    }

    public static Map<String, String> readMapSafe(String file, String delimiter) {
        synchronized (file) {
            Map<String, String> map = null;
            try {
                FILE_LOCKS_REQUESTED_BY_READER.add(file);
                while (FILE_LOCKS_REQUESTED_BY_WRITER.contains(file)) {
                    Thread.sleep(200);
                }
                map = readMap(file, delimiter);
            } catch (Exception ignored) {
            } finally {
                FILE_LOCKS_REQUESTED_BY_READER.remove(file);
            }
            return map;
        }
    }

    public static Map<String, String> readMap(String file, String delimiter) {
        Map<String, String> map = new HashMap<>();
        for (String string : read(file)) {
            if (string.trim().equals("")) continue;
            String[] split = string.split(delimiter, -1);
            if (split.length > 1) {
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    public static List<List<String>> readTable(String file, String delimiter) {
        List<List<String>> toReturn = list();
        List<String> read = read(file);
        for (String s : read) {
            if (s == null || s.equals("")) continue;
            toReturn.add(Arrays.asList(s.split(delimiter)));
        }
        return toReturn;
    }

    public static Map<String, String> charmap;

    static {
        charmap = new HashMap<>();
        charmap.put("è", "È");
        charmap.put("é", "É");
        charmap.put("ê", "Ê");
        charmap.put("ç", "Ç");
        charmap.put("ö", "Ö");
        charmap.put("ë", "Ë");
        charmap.put("ï", "Ï");
        charmap.put("ü", "Ü");
        charmap.put("ä", "Ä");
        charmap.put("ż", "Ż");
        charmap.put("ł", "Ł");
        charmap.put("á", "Á");
        charmap.put("ð", "Ð");
        charmap.put("í", "Í");
        charmap.put("ó", "Ó");
        charmap.put("ú", "Ú");
        charmap.put("ý", "Ý");
        charmap.put("Þ", "þ");
        charmap.put("æ", "Æ");
        charmap.put("ô", "Ô");
    }

    public static class Url {
        String url;
        com.events.date.Calendar.Date date;

        Url(String url, com.events.date.Calendar.Date date) {
            this.url = url;
            this.date = date;
        }

        Url(String url) {
            this.url = url;
        }
    }

    public static class CountMap<T> {
        Map<T, Integer> map = new HashMap<>();

        void greaterThan(int greaterThanExclusive) {
            List<T> toRemove = new ArrayList<T>();
            for (T t : map.keySet()) {
                if (map.get(t) <= greaterThanExclusive) {
                    toRemove.add(t);
                }
            }
            for (T t : toRemove) {
                map.remove(t);
            }
        }

        int add(T key) {
            return add(key, 1);
        }

        int add(T key, Integer value) {
            Integer valueExisting = map.get(key);
            if (valueExisting == null) {
                map.put(key, value);
                return value;
            } else {
                int valueSum = map.get(key) + value;
                map.put(key, valueSum);
                return valueSum;
            }
        }

        T getLargest() {
            return getLargest(-1);
        }

        T getLargest(Integer minimum) {
            int largest = minimum;
            T largestKey = null;
            for (T key : map.keySet()) {
                Integer integer = map.get(key);
                if (integer >= largest) {
                    largest = integer;
                    largestKey = key;
                }
            }
            return largestKey;
        }
    }

    public static class StringMutable {
        public String string;

        public StringMutable(String string) {
            this.string = string;
        }

        public void set(String string) {
            this.string = string;
        }

        public String substring(int begin_index) {
            return string.substring(begin_index);
        }
    }

    public static class Table {
        public List<List<String>> underlying;

        public Table(List<List<String>> underlying) {
            this.underlying = underlying;
        }

        public MultiList<Integer, Integer> getIgnoreCase(String value) {
            if (empty(value)) return null;
            MultiList<Integer, Integer> coordinates = new MultiList<>();
            for (int i = 0; i < underlying.size(); i++) {
                List<String> row = underlying.get(i);
                for (int j = 0; j < row.size(); j++) {
                    if (value.equalsIgnoreCase(row.get(j))) {
                        coordinates.add(new Multi<>(i, j));
                    }
                }
            }
            return coordinates;
        }

        public Map<String, String> createMap(int columnA, int columnB) {
            Map<String, String> map = new HashMap<>();
            for (List<String> row : underlying) {
                if (row.size() >= Math.max(columnA, columnB) + 1) {
                    map.put(row.get(columnA), row.get(columnB));
                }
            }
            return map;
        }

        public List<String> createList(int column) {
            List<String> list = list();
            for (List<String> row : underlying) {
                if (row.size() >= column + 1) {
                    list.add(row.get(column));
                }
            }
            return list;
        }
    }

    public static class NList {
        String splitDelimiter;
        List<String> underlying;

        public String get(int index) {
            if (underlying.size() < index + 1) {
                return null;
            }
            return underlying.get(index);
        }

        public String getLast() {
            return get(size() - 1);
        }

        public List<String> getWordPairs() {
            List<String> wordPairs = list();
            if (underlying.size() == 0) return null;
            if (underlying.size() == 1) return null;
            for (int i = 0; i < underlying.size() - 1; i++) {
                wordPairs.add(underlying.get(i) + " " + underlying.get(i + 1));
            }
            return wordPairs;
        }

        public String reconstruct(List<String> list) {
            return reconstruct(list, null);
        }

        public String reconstruct(List<String> list, String joiner) {
            if (list == null) return null;
            if (joiner == null) joiner = splitDelimiter;
            if (joiner.equals("\\s+")) {
                return string(list, " ");
            } else if (joiner.equals("\\(")) {
                return string(list, "(");
            } else {
                return string(list, joiner);
            }
        }

        public String reconstruct() {
            return reconstruct(size());
        }

        public String reconstruct(int index_from_inclusive, int index_to_exclusive) {
            if (underlying == null) return null;
            List<String> sublist = sublist(index_from_inclusive, index_to_exclusive);
            return reconstruct(sublist);
        }

        public String reconstruct(int index_to_exclusive) {
            if (underlying == null) return null;
            List<String> sublist = sublist(0, index_to_exclusive);
            return reconstruct(sublist);
        }

        public String reconstruct(int index_to_exclusive, String joiner) {
            if (underlying == null) return null;
            List<String> sublist = sublist(0, index_to_exclusive);
            return reconstruct(sublist, joiner);
        }

        public void set(int index, String string) {
            underlying.set(index, string);
        }

        public String reconstructReplacing(Integer index, List<String> strings) {
            List<String> words = list();
            if (index > 0) {
                for (int j = 0; j < index; j++) {
                    words.add(underlying.get(j));
                }
            }
            words.addAll(strings);
            if (index < size() - 1) {
                for (int j = index + 1; j < size(); j++) {
                    words.add(underlying.get(j));
                }
            }
            return reconstruct(words);
        }

        public String reconstructReplacing(Map<Integer, String> index_replacement) {
            List<String> words = list();
            for (int i = 0; i < underlying.size(); i++) {
                String replacement = index_replacement.get(i);
                if (replacement == null) {
                    words.add(underlying.get(i));
                } else {
                    words.add(replacement);
                }
            }
            return reconstruct(words);
        }

        public String reconstructReplacing(Integer index, String replacement) {
            HashMap<Integer, String> map = new HashMap<>();
            map.put(index, replacement);
            return reconstructReplacing(map);
        }

        public String reconstructExcept(List<Integer> except) {
            if (underlying == null) return null;
            List<String> sublist = list();
            for (int i = 0; i < underlying.size(); i++) {
                if (!except.contains(i)) {
                    sublist.add(underlying.get(i));
                }
            }
            return reconstruct(sublist);
        }

        public String reconstructExcept(int i) {
            if (underlying == null) return null;
            if (i >= underlying.size()) return null;
            List<String> sublist = sublist(0, i);
            if (i != underlying.size() - 1) {
                sublist.addAll(sublist(i + 1, underlying.size()));
            }
            return reconstruct(sublist);
        }

        public String reconstructBeforeLast() {
            if (underlying == null) return null;
            List<String> sublist = sublist(0, underlying.size() - 1);
            return reconstruct(sublist);
        }

        public int size() {
            if (underlying == null) return 0;
            return underlying.size();
        }

        public List<String> sublist(int startIndex, int endIndexExclusive) {
            if (underlying.size() < startIndex + 1) {
                return list();
            }
            return underlying.subList(startIndex, Math.min(underlying.size(), endIndexExclusive));
        }
    }

    enum EnvType {LOCAL, SERVER}

    public static class FileContext {
        String intermediate;
        String output;
        String html;

        FileContext(String intermediate, String output, String html) {
            this.intermediate = intermediate;
            this.output = output;
            this.html = html;
        }
    }

    public static class Film {
        public static final List<String> filmRatings = list("(U)", "(PG)", "(12)", "(15)", "(18)", "(12A)", "[U]", "[PG]", "[12]", "[15]", "[18]", "[12A]", "P&B");
    }

    public static class IntegerMutable {
        Integer integer = 0;
    }

    public enum Lang {ENG, DEU}

    public static class PageData {
        List<DisplayEvent> events;
        DisplayEvent mainpageEvent;
    }

    public static class Multi<A, B> implements Serializable {
        public A a;
        public B b;

        public Multi(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public static <T, S> T safeA(Multi<T, S> multi) {
            if (multi == null) return null;
            return multi.a;
        }

        public static <T, S> S safeB(Multi<T, S> multi) {
            if (multi == null) return null;
            return multi.b;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Multi<?, ?> multi = (Multi<?, ?>) o;
            if (!Objects.equals(a, multi.a)) return false;
            return Objects.equals(b, multi.b);
        }

        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }

    public static class MapList<K extends Comparable<? super K>, T> implements Serializable {
        public Map<K, ArrayList<T>> mapList;

        public MapList() {
            this.mapList = new HashMap<>();
        }

        public Set<K> keys() {
            return this.mapList.keySet();
        }

        public K getKey(T listelement) {
            for (K key : mapList.keySet()) {
                List<T> list = mapList.get(key);
                if (empty(list)) continue;
                if (list.contains(listelement)) return key;
            }
            return null;
        }

        public int put(K key, T listElement) {
            mapList.putIfAbsent(key, list());
            mapList.get(key).add(listElement);
            return mapList.get(key).size();
        }

        public void addAll(K key, Collection<T> elements) {
            mapList.putIfAbsent(key, list());
            mapList.get(key).addAll(elements);
        }

        public List<T> get(K key) {
            return mapList.get(key);
        }

        public List<K> orderedKeys() {
            List<K> list = new ArrayList<>(this.mapList.keySet());
            Collections.sort(list);
            return list;
        }

        public List<T> listvalues() {
            List<T> aggregated = list();
            for (K key : mapList.keySet()) {
                List<T> list = mapList.get(key);
                if (empty(list)) continue;
                aggregated.addAll(list);
            }
            return aggregated;
        }
    }

    public static class Multi3<A, B, C> implements Serializable {
        A a;
        B b;
        C c;

        Multi3(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Multi3<?, ?, ?> multi_3 = (Multi3<?, ?, ?>) o;
            return Objects.equals(a, multi_3.a) && Objects.equals(b, multi_3.b) && Objects.equals(c, multi_3.c);
        }

        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }

    public static class MultiList<S, T> {
        public List<Multi<S, T>> underlying = list();

        public MultiList() {
        }

        public void add(Multi<S, T> multi) {
            underlying.add(multi);
        }

        public void add(S a, T b) {
            underlying.add(new Multi<>(a, b));
        }

        public <U extends Multi<S, T>> void addAll(Collection<U> multis) {
            underlying.addAll(multis);
        }

        public Map<S, ArrayList<T>> map() {
            MapListNC<S, T> map = new MapListNC<>();
            for (Multi<S, T> multi : underlying) {
                map.put(multi.a, multi.b);
            }
            return map.mapList;
        }

        public List<S> getAList() {
            List<S> to_return = new ArrayList<S>();
            for (Multi<S, T> multi : underlying) {
                to_return.add(multi.a);
            }
            return to_return;
        }

        public List<T> getBList() {
            List<T> to_return = list();
            for (Multi<S, T> multi : underlying) {
                to_return.add(multi.b);
            }
            return to_return;
        }

        public List<T> getB(S key) {
            List<T> toReturn = list();
            for (Multi<S, T> multi : underlying) {
                if (key.equals(multi.a)) {
                    toReturn.add(multi.b);
                }
            }
            return toReturn;
        }

        public T getBOne(S key) {
            List<T> getb = getB(key);
            if (empty(getb)) return null;
            return getb.get(0);
        }

        public S getaOne(T key) {
            List<S> geta = getA(key);
            if (empty(geta)) return null;
            return geta.get(0);
        }

        public List<S> getA(T key) {
            List<S> toReturn = list();
            for (Multi<S, T> multi : underlying) {
                if (key.equals(multi.b)) toReturn.add(multi.a);
            }
            return toReturn;
        }

        public Multi<S, T> get(Integer index) {
            return underlying.get(index);
        }

        public Integer size() {
            return underlying.size();
        }
    }
}