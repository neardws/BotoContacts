package com.qq.singleangelvip.contact.contacts;
import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Created by singl on 2017/3/31.
 */
public final class Contact {
    private Long _ID;
    private String nickName;
    private String company;
    private String im;
    private String displayName;
    private String givenName;
    private String familyName;
    private final Set<PhoneNumber> phoneNumbers = new HashSet<>();
    private String photoUri;
    private final Set<Email> emails = new HashSet<>();
    private final Set<Event> events = new HashSet<>();

    interface AbstractField {
        String getMimeType();
        String getColumn();
    }

    public enum Field implements AbstractField {
        NickName(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Nickname.NAME),
        Company(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Organization.COMPANY),
        Im(ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL),
        DisplayName(null, ContactsContract.Data.DISPLAY_NAME),
        GivenName(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME),
        FamilyName(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME),
        PhoneNumber(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.NUMBER),
        PhoneType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE),
        PhoneLabel(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.LABEL),
        @SuppressLint("InlinedApi")
        PhoneNormalizedNumber(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER),
        Email(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Email.ADDRESS),
        EmailType(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE),
        EmailLabel(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Email.LABEL),
        PhotoUri(null, ContactsContract.Data.PHOTO_URI),
        EventStartDate(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Event.START_DATE),
        EventType(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Event.TYPE),
        EventLabel(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Event.LABEL);

        private final String column;
        private final String mimeType;

        Field(String mimeType, String column) {
            this.mimeType = mimeType;
            this.column = column;
        }

        @Override
        public String getColumn() {
            return column;
        }

        @Override
        public String getMimeType() {
            return mimeType;
        }
    }

    enum InternalField implements AbstractField {
        ContactId(null, ContactsContract.RawContacts.CONTACT_ID),
        MimeType(null, ContactsContract.Data.MIMETYPE);

        private final String column;
        private final String mimeType;

        InternalField(String mimeType, String column) {
            this.mimeType = mimeType;
            this.column = column;
        }

        @Override
        public String getColumn() {
            return column;
        }

        @Override
        public String getMimeType() {
            return mimeType;
        }
    }

    Contact() {}

    Contact add_ID(Long _ID){
        this._ID = _ID;
        return this;
    }

    Contact addNickName(String nickName){
        this.nickName = nickName;
        return this;
    }

    Contact addCompany(String company){
        this.company = company;
        return this;
    }

    Contact addIm(String im){
        this.im = im;
        return this;
    }

    Contact addDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    Contact addGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    Contact addFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    Contact addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
        return this;
    }

    Contact addPhotoUri(String photoUri) {
        this.photoUri = photoUri;
        return this;
    }

    Contact addEmail(Email email) {
        emails.add(email);
        return this;
    }

    Contact addEvent(Event event) {
        events.add(event);
        return this;
    }

    public Long get_ID(){
        return _ID;
    }

    public String getNickName(){
        return nickName;
    }

    public String getCompany(){
        return company;
    }

    public String getIm(){
        return im;
    }
    /**
     * Gets a the display name the contact.
     *
     * @return Display Name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets a the given name the contact.
     *
     * @return Given Name.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Gets a the Family name the contact.
     *
     * @return Family Name.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Gets a list of all phone numbers the contact has.
     *
     * @return A List of phone numbers.
     */
    public List<PhoneNumber> getPhoneNumbers() {
        return Arrays.asList(phoneNumbers.toArray(new PhoneNumber[phoneNumbers.size()]));
    }

    /**
     * Gets a contacts photo uri.
     *
     * @return Photo URI.
     */
    public String getPhotoUri() {
        return photoUri;
    }

    /**
     * Gets a list of all emails the contact has.
     *
     * @return A List of emails.
     */
    public List<Email> getEmails() {
        return Arrays.asList(emails.toArray(new Email[emails.size()]));
    }

    /**
     * Gets a list of all events the contact has.
     *
     * @return A List of emails.
     */
    public List<Event> getEvents() {
        return Arrays.asList(events.toArray(new Event[events.size()]));
    }

    /**
     * Gets the birthday event if exists.
     *
     * @return Birthday event or null.
     */
    public Event getBirthday() {
        return getEvent(Event.Type.BIRTHDAY);
    }

    /**
     * Gets the anniversary event if exists.
     *
     * @return Anniversary event or null.
     */
    public Event getAnniversary() {
        return getEvent(Event.Type.ANNIVERSARY);

    }

    private Event getEvent(Event.Type type) {
        for (Event event: events) {
            if (type.equals(event.getType())) {
                return event;
            }
        }

        return null;
    }
}
