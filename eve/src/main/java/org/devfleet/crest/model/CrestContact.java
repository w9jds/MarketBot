package org.devfleet.crest.model;

public final class CrestContact extends CrestEntity {

    private double standing;

    private CrestCharacter character;

    private CrestItem contact;

    private String contactType;

    private Boolean watched;

    private Boolean blocked;

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

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
