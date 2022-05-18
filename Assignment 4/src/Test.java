public class Test {
    public static void main(String[] args) {
        ArrayUnorderedList<SkiSegment> path;
        String[] hill;
        Ski ski;

        hill = new String[]{"", "slalom-L", "jump-30"};
        ski = new Ski(hill);
        System.out.println(ski.getRoot());
    }
}
