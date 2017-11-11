package com.example.hi.rps;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

//import static com.example.hi.rps.TCPConnectionService;


class Entry {
    /*public final String userName;
    public final String ipAddress;
    public String status;
    public String n;
   private static final String ns=null;

    Entry("<OnlinePlayers/>") {
        this.status = status;
        this.userName = TCPConnectionService.userName;
        this.ipAddress = TCPConnectionService.ipAddress;

    }



    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "ReadyToPlay");
        String userName = null;
        String ipAddress= null;
        //String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("userName")) {
                userName = readTitle(parser);
            } else if (name.equals("status")) {
                status = readSummary(parser);
            }  else {
                skip(parser);
            }
        }
        return new Entry();
    }

    private void skip(XmlPullParser parser) {
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "userName");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "status");
        return userName;
    }



    // Processes summary tags in the feed.
    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "status");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "status");
        return status;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }*/
}