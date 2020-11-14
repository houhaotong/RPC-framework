package enums;

/**
 * @author hht
 * @date 2020/11/6 20:49
 */
public enum RegistryURL {

    ZKADDRESS("127.0.0.1:2181"),
    NACOSADDR("127.0.0.1:8848");

    RegistryURL(String url){
        this.url=url;
    }
    private String url;

    public String getUrl() {
        return url;
    }
}
