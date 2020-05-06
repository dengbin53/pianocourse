/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore.playdata;

import java.util.Iterator;

/**
 * Iterator type which iterates through a Bar returning each Note in sequence
 */
public class NoteIterator implements Iterator<Note>
{
	/**
	 * return true if this is not the last note in the bar
	 */
	public native boolean hasNext();
	
	/**
	 * return the next note and update this iterator
	 */
	public native Note next();

	/**
	 * unsupported for immutable list
	 */
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private NoteIterator(long nativePointer, int bidx, boolean ci, int pidx, int nidx)
	{
		this.nativePointer = nativePointer;
		this.bidx = bidx;
		this.ci = ci;
		this.pidx = pidx;
		this.nidx = nidx;
		this.loop = 0;
	}
	private NoteIterator(long nativePointer, int bidx, boolean ci, int pidx, int nidx, long loop)
	{
		this.nativePointer = nativePointer;
		this.bidx = bidx;
		this.ci = ci;
		this.pidx = pidx;
		this.nidx = nidx;
		this.loop = loop;
	}
	private final long nativePointer;
	private final int bidx;
    private final boolean ci;
	private final int pidx;
	private final long loop;
	private int nidx;
}