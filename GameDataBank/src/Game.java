
public class Game {
    private String release;
    private String gameTitle;
    private String genre;
    private String plattform;
    private String fsk;
    private String Nummer;
    private Boolean Komplett;
 
    public Game() {}
 
    public Game(final String Nummer, final String gameTitle2, final String release, final String genre2, final String plattform2,
            final String fsk2, final Boolean Komplett2) {
        this.release = release;
        this.gameTitle = gameTitle2;
        this.genre = genre2;
        this.plattform = plattform2;
        this.fsk = fsk2;
        this.Nummer = Nummer;
        this.Komplett = Komplett2;
    }
 
    public String getFsk() {
        return fsk;
    }
 
    public String getGameTitle() {
        return gameTitle;
    }
 
    public String getGenre() {
        return genre;
    }
 
    public Boolean getKomplett() {
        return Komplett;
    }
 
    public String getNummer() {
        return Nummer;
    }
 
    public String getPlattform() {
        return plattform;
    }
 
    public String getRealease() {
        return release;
    }
 
    public String getRelease() {
        return release;
    }
 
    public void setFsk(final String fsk) {
        this.fsk = fsk;
    }
 
    public void setGameTitle(final String gameTitle) {
        this.gameTitle = gameTitle;
    }
 
    public void setGenre(final String genre) {
        this.genre = genre;
    }
 
    public void setKomplett(final Boolean Komplett) {
        this.Komplett = Komplett;
    }
 
    public void setNummer(final String nummer) {
        Nummer = nummer;
    }
 
    public void setPlattform(final String plattform) {
        this.plattform = plattform;
    }
 
    public void setRelease(final String release) {
        this.release = release;
    }
 
}