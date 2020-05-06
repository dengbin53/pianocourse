package uk.co.dolphin_com.sscore;

import org.w3c.dom.Notation;

/**
 * a chord
 */
public class ChordItem extends TimedItem {

    /**
     * The value of the note 2 = minim, 4 = crochet etc.
     */
    public final int noteType;

    /**
     * number of dots - 1 if dotted, 2 if double-dotted
     */
    public final int numdots;

    /**
     * Array of notations - NotationsType_tied,NotationsType_slur.. etc
     */
    public final int[] notations;

    /**
     * true if this is a grace note
     */
    public final boolean grace;

    /**
     * The first note of the chord (as defined in MusicXML)
     */
    public final ChordNote base_note;

    /**
     * The subsequent chord notes
     */
    public final ChordNote chordNotes[];

    public boolean hasBaseNote() { return true; }

    public NoteItem baseNoteItem()
    {
        return new NoteItem(staff, item_h, start, duration,
            ItemType_note, base_note.midipitch, noteType, numdots, base_note.accidentals,
            false, notations, base_note.tied, grace);
    }


    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" value:").append(noteType);
        if (numdots == 1)
            sb.append(" dot");
        else if (numdots > 1)
            sb.append(" ").append(numdots).append(" dots");
        if (notations.length > 0)
        {
            sb.append(" {");
            int i = 0;
            for (int n : notations)
            {
                if (i > 0)
                    sb.append(", ");
                switch(n)
                {
                    case NoteItem.NotationsType_unset:sb.append("unset");break;
                    case NoteItem.NotationsType_tied:sb.append("tied");break;
                    case NoteItem.NotationsType_slur:sb.append("slur");break;
                    case NoteItem.NotationsType_tuplet:sb.append("tuplet");break;
                    case NoteItem.NotationsType_glissando:sb.append("glissando");break;
                    case NoteItem.NotationsType_slide:sb.append("slide");break;
                    case NoteItem.NotationsType_ornaments:sb.append("ornaments");break;
                    case NoteItem.NotationsType_technical:sb.append("technical");break;
                    case NoteItem.NotationsType_articulations:sb.append("articulations");break;
                    case NoteItem.NotationsType_dynamics:sb.append("dynamics");break;
                    case NoteItem.NotationsType_fermata:sb.append("fermata");break;
                    case NoteItem.NotationsType_arpeggiate:sb.append("arpeggiate");break;
                    case NoteItem.NotationsType_non_arpeggiate:sb.append("non_arpeggiate");break;
                    case NoteItem.NotationsType_accidental_mark:sb.append("accidental_mark");break;
                    case NoteItem.NotationsType_other:sb.append("other");break;
                    default:
                    case NoteItem.NotationsType_unknown:sb.append("unknown");break;
                }
                ++i;
            }
            sb.append("}");
        }
        if (grace)
            sb.append(" grace");
        sb.append(" base note:").append(base_note);
        sb.append("  notes: ");
        int index = 0;
        for (ChordNote cn : chordNotes)
        {
            if (index > 0)
                sb.append(" +");
            sb.append(cn);
            ++index;
        }
        return sb.toString();
    }

    private ChordItem(int staff, int item_h, int start, int duration,
                     int noteType, int numdots,
                     int[] notations, boolean grace, ChordNote base_note, ChordNote chordNotes[]) {
        super(ItemType_chord, staff, item_h, start, duration);
        this.noteType = noteType;
        this.numdots = numdots;
        this.notations = notations;
        this.grace = grace;
        this.base_note = base_note;
        this.chordNotes = chordNotes;
    }
}
