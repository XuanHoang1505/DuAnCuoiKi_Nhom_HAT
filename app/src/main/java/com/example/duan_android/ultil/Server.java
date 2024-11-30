package com.example.duan_android.ultil;

public class Server {
    public static String localhost ="192.168.1.4";
    public static String path_MovieShowing=  "http://"+localhost+"/server/getMovieShowing.php";
    public static String path_MovieComingSoon=  "http://"+localhost+"/server/getMovieComingSoon.php";
    public static String path_MovieViewMoreShowing=  "http://"+localhost+"/server/getMovieViewMoreShowing.php";
    public static String path_MovieViewMoreComingSoon=  "http://"+localhost+"/server/getMovieViewMoreComingSoon.php";
    public static String rap = "http://" + localhost + "/server/getrap.php";
    public static String login = "http://" + localhost + "/server/getlogin.php";
    public static String giaodich = "http://" + localhost + "/server/getgiaodich.php?userId=";
    public static String path_Comment = "http://" + localhost + "/server/getComment.php";
    public static String path_news = "http://" + localhost + "/server/getNews.php";
    public static String path_VoucherSystem = "http://" + localhost + "/server/getVoucherSystem.php";
    public static String path_VoucherCustomer = "http://" + localhost + "/server/getVoucherCustomer.php";
    public static String path_VoucherDaDung = "http://" + localhost + "/server/getVoucherDaDung.php";
    public static String path_getMovieById = "http://" + localhost + "/server/getMovieById.php?idPhim=";
}
