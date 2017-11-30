package com.SAM2018.model;

/**
 * A Value Object that represents a Message sent to a User for feedback
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class Message {
    //Attributes
    private String text;
    private String type;

    /**
     * Parameterized Constructor for Message Class
     * @param _text The message's text
     * @param _type The message's type
     */
    public Message(String _text, String _type) {
        this.text = _text;
        this.type = _type;
    }

    /**
     * Accessor for text property
     * @return text The Message's text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Accessor for type property
     * @return type The Message's type
     */
    public String getType() {
        return this.type;
    }

}
