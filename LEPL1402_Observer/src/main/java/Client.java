public class Client extends Observer {
    public Client(int zone){
        this.news = ""; this.zone = zone;
    }

    @Override
    public void update(Object o) {
        if ( o instanceof String){ news = (String) o; }
    }

    @Override
    public int getZone() { return zone; }

    @Override
    public String getNews() { return news; }
}
