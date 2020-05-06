package uk.co.dolphin_com.sscore;

public class ChordNote {

    /**
     * The MIDI pitch of this note ie 60 = C4; 0 = rest
     */
    public final int midipitch;

    /**
     * any accidentals defined ie +1 = 1 sharp, -1 = 1 flat etc.
     */
    public final int accidentals;

    /**
     * If this is a tied note this contains information about the tie - else null
     */
    public final Tied tied;

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" pitch:").append(midipitch);
        if (accidentals > 0)
            sb.append(" ").append(accidentals).append(" sharps");
        else if (accidentals < 0)
            sb.append(" ").append(-accidentals).append(" flats");

        if (tied != null)
            sb.append(" ").append(tied);
        return sb.toString();
    }

    ChordNote(int pitch, int accidentals, Tied tied) {
        this.midipitch = pitch;
        this.accidentals = accidentals;
        this.tied = tied;
    }
}
