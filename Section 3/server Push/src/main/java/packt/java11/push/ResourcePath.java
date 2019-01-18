package packt.java11.push;

public class ResourcePath {

    static String get(){
        final String path;
        var osname = System.getProperty("os.name");
        if( osname.contains("indows")){
            path = "C:\\Users\\peter_verhas\\Dropbox\\packt\\Network-Programming-in-Java\\sources\\40_SUPH2\\src\\main\\webapp\\www.flaticon.com\\packs\\miscellaneous-elements\\";
        }else{
            path = "/Users/verhasp/Dropbox/packt/Network-Programming-in-Java/sources/40_SUPH2/src/main/webapp/www.flaticon.com/packs/miscellaneous-elements/";
        }
        return path;
    }
}
