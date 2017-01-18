package database.CrimeDbSchema;

/**
 * Created by fanshuchao on 2017-1-18.
 */

public class CrimeDbSchema {
    //定义数据表元素的String常量
    public static final class CrimeTable{
        public static final String NAME = "crimes";
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
