package handlers;

import com.github.kennedyoliveira.pastebin4j.*;
import config.ApplicationConstants;

public class PasteBinHandler {
    private PasteBin pasteBin;

    public PasteBinHandler() {
        this.pasteBin = new PasteBin(new AccountCredentials(ApplicationConstants.PASTE_BIN_DEV_KEY, "", ""));
    }

    public String postContentAsGuest(String title, String content){
        GuestPaste guestPaste = new GuestPaste();

        guestPaste.setContent(content.toString());
        guestPaste.setTitle(title);
        guestPaste.setExpiration(PasteExpiration.NEVER);
        guestPaste.setVisibility(PasteVisibility.PUBLIC);

        return pasteBin.createPaste(guestPaste);
    }
}
