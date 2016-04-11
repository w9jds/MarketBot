package core.eve.crest;

public final class CrestContact extends CrestEntity {

    private double standing;

    private CrestCharacter character;

    private CrestItem contact;

    private String contactType;

    private boolean watched;

    private boolean blocked;

    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public double getStanding() {
        return standing;
    }

    public void setStanding(double standing) {
        this.standing = standing;
    }

    public CrestCharacter getCharacter() {
        return character;
    }

    public void setCharacter(CrestCharacter character) {
        this.character = character;
    }

    public CrestItem getContact() {
        return contact;
    }

    public void setContact(CrestItem contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
