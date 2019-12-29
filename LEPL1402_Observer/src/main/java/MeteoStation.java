public class MeteoStation extends Observable {
    @Override
    public Observer[] getSubscribers() {
        // return subscribers.toArray(Observer[]::new);
        return subscribers.toArray(new Observer[]{});
    }

    @Override
    public void broadcast(Pair<String, Integer> pair) {
        zones.add(pair);
        for (Observer subscriber : subscribers){
            if ( pair.getZone() == subscriber.getZone() ){
                subscriber.update(pair.getAlert());
            }
        }
    }

    @Override
    public void addObserver(Observer sub) {
        subscribers.add(sub);
    }

    @Override
    public boolean removeObserver(Observer sub) {
        return subscribers.removeIf(subscribers -> subscribers.equals(sub));
    }

    @Override
    public void setAlert(String alert, int zone) {
        broadcast(new Pair<>(alert, zone));
    }
}
