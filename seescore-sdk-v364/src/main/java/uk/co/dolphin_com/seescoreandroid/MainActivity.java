package uk.co.dolphin_com.seescoreandroid;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import uk.co.dolphin_com.seescoreandroid.SeeScoreView.ZoomNotification;
import uk.co.dolphin_com.sscore.BarGroup;
import uk.co.dolphin_com.sscore.Component;
import uk.co.dolphin_com.sscore.Header;
import uk.co.dolphin_com.sscore.Item;
import uk.co.dolphin_com.sscore.LoadOptions;
import uk.co.dolphin_com.sscore.RenderItem;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.Tempo;
import uk.co.dolphin_com.sscore.ex.ScoreException;
import uk.co.dolphin_com.sscore.ex.XMLValidationException;
import uk.co.dolphin_com.sscore.playdata.Note;
import uk.co.dolphin_com.sscore.playdata.PlayData;
import uk.co.dolphin_com.sscore.playdata.UserTempo;

/**
 * SeeScore For Android Sample App
 * Dolphin Computing http://www.dolphin-com.co.uk
 */
public class MainActivity extends Activity {

    private static final boolean PlayUsingMediaPlayer = true;

    private static final boolean UseNoteCursorIfPossible = true; // else bar cursor

    private static final boolean ColourPlayedNotes = false;

    private static final boolean PrintContents = false; // use SScore.getBarContents to list all items in bars

    private static final int kMinTempoBPM = 30;
    private static final int kMaxTempoBPM = 240;
    private static final int kDefaultTempoBPM = 80;
    private static final double kMinTempoScaling = 0.5;
    private static final double kMaxTempoScaling = 2.0;
    private static final double kDefaultTempoScaling = 1.0;
    private static final float kMinZoom = 0.2F;
    private static final float kMaxZoom = 2.0F;
    private static final float initialZoom = 1.0F;
    private static final int initialZoomSliderVal = (int) (100 * (initialZoom - kMinZoom) / (kMaxZoom - kMinZoom));
    private static final int kPlayLoopRepeats = 7;

    /**
     * set true to clear files in internal directory and reload from assets
     */
    private static final boolean reloadAssetsFiles = false;

    /**
     * the current file to preserve during a device rotation
     */
    private static final String CURRENT_FILE = "currentFile";

    /**
     * the index of the next file to load to preserve during device rotation
     */
    private static final String NEXT_FILE_INDEX = "nextFileIndex";

    /**
     * the magnification to preserve
     */
    private static final String MAGNIFICATION = "magnification";

    /**
     * the index of the next file to load from the internal directory
     */
    private int nextFileIndex = 0;

    /**
     * the current file which is displayed
     */
    private File currentFile;

    /**
     * the View which displays the score
     */
    private SeeScoreView ssview;

    /**
     * the current viewed score.
     * <p>Preserved to avoid reload on rotate (which causes complete destruction and recreation of this Activity)
     */
    private SScore currentScore;

    /**
     * the current magnification.
     * <p>Preserved to avoid reload on rotate (which causes complete destruction and recreation of this Activity)
     */
    private float magnification;

    /**
     * set to prevent reentry during transpose
     */
    private boolean isTransposing;

    /**
     * the player plays the music using MediaPlayer and supports handlers for synchronised events on bar start, beat and note start
     */
    private Player player;

    /**
     * the current bar preserved on player stop so it can be restarted in the same place
     */
    private int currentBar;

    /**
     * we can mute upper (right) or lower (left) staff
     */
    private boolean playingLeft = true;
    private boolean playingRight = true;

    /**
     * setup looping
     */
    private int loopStart;
    private int loopEnd;

    /**
     * set to show a single part
     */
    private boolean isShowingSinglePart = false;
    private int singlePart = 0;

    /**
     * called on creating this Activity AND ALSO on device rotation (ie portrait/landscape switch)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating the cursor view
        final CursorView cursorView = new CursorView(this, new CursorView.OffsetCalculator() {
            public float getScrollY() {
                final ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
                return sv.getScrollY();
            }
        });

        currentScore = null;
        currentBar = 0;
        loopStart = loopEnd = -1;
        magnification = 1.0F;
        isTransposing = false;
        if (reloadAssetsFiles)
            clearInternalDir();
        ssview = new SeeScoreView(this, cursorView, getAssets(), new ZoomNotification() {

            public void zoom(float scale) {
                showZoom(scale);
                magnification = scale;
            }

        }, new SeeScoreView.TapNotification() {
            public void tap(int systemIndex, int partIndex, int barIndex, Component[] components) {
                System.out.println("tap system:" + systemIndex + " bar:" + barIndex);

                boolean isPlaying = (player != null && player.state() == Player.State.Started);

                if (player != null) {
                    boolean isPaused = (player.state() == Player.State.Paused);
                    if (isPlaying || isPaused)
                        player.stop();
                }
                if (barIndex < loopStart)
                    barIndex = loopStart;
                else if (loopEnd > 0 && loopEnd > loopStart && barIndex > loopEnd)
                    barIndex = loopEnd;

                ssview.setCursorAtBar(barIndex, (player != null) ? SeeScoreView.CursorType.line : SeeScoreView.CursorType.box, 200);

                if (isPlaying) {
                    player.startAt(barIndex, false/*no countIn*/);
                }
                currentBar = barIndex;
                updatePlayPauseButtonImage();
                for (Component comp : components)
                    System.out.println(comp);
            }

            public void longTap(int systemIndex, int partIndex, int barIndex, Component[] components) {

                isShowingSinglePart = !isShowingSinglePart;
                playingLeft = playingRight = true;
                clearLoop();
                invalidateOptionsMenu();
                if (isShowingSinglePart)
                    singlePart = partIndex;
                if (currentScore != null)
                    showScore(currentScore, null); // toggle single part or all parts on long tap
            }
        });
        setContentView(R.layout.activity_main);
        TextView versionText = (TextView) findViewById(R.id.versionLabel);
        versionText.setText("SeeScoreLib Version:" + SScore.getVersion().toString());
        hideBeat();
        final ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
        sv.addView(ssview);

        //Add the overlaying cursor view
        final ScrollView cursorScrollView = (ScrollView) findViewById(R.id.scrollViewCursor);
        cursorScrollView.addView(cursorView);
        cursorScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return sv.dispatchTouchEvent(event); // pass through the touch events to sv
            }
        });

        //Sets the cursorScrollView's height and width
        cursorScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cursorView.measure(cursorScrollView.getWidth(), cursorScrollView.getHeight());
            }
        });

        sv.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                return ssview.onTouchEvent(event);
            }

        });
        setTempo(kDefaultTempoBPM);
        SeekBar tempoSlider = (SeekBar) findViewById(R.id.tempoSlider);
        tempoSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int p, boolean u) {
            }

            @Override
            /**
             * called on moving the tempo slider. Updates the tempo text and the player tempo if playing
             */
            public void onStopTrackingTouch(SeekBar seekBar) {
                int sliderValCents = seekBar.getProgress();
                if (currentScore != null) {
                    if (currentScore.hasDefinedTempo()) {
                        double scaling = tempoSliderPercentToScaling(sliderValCents);
                        try {
                            Tempo tempo = currentScore.tempoAtStart();
                            setTempoText((int) (scaling * tempo.bpm + 0.5));
                        } catch (ScoreException ex) {
                        }
                    } else {
                        int bpm = tempoSliderPercentToBPM(sliderValCents);
                        setTempoText(bpm);
                    }
                    if (player != null) {
                        try {
                            player.updateTempo();
                        } catch (Player.PlayerException ex) {
                            System.out.println("Failed to set player tempo " + ex);
                        }
                    }
                } else {
                    setTempoText(tempoSliderPercentToBPM(sliderValCents));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }
        });
        SeekBar zoomSlider = (SeekBar) findViewById(R.id.zoomSlider);
        zoomSlider.setProgress(initialZoomSliderVal);
        zoomSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int sliderValCents, boolean b) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                int percent = seekBar.getProgress();
                float zoom = sliderPercentToZoom(percent);
                ssview.zoom(zoom);
            }
        });

        if (savedInstanceState != null) // restore state on device rotation avoiding file reload
        {
            String filePath = savedInstanceState.getString(CURRENT_FILE);
            if (filePath != null && filePath.length() > 0)
                currentFile = new File(filePath);
            nextFileIndex = savedInstanceState.getInt(NEXT_FILE_INDEX);
            magnification = savedInstanceState.getFloat(MAGNIFICATION);
            playingLeft = savedInstanceState.getBoolean("playingLeft");
            playingRight = savedInstanceState.getBoolean("playingRight");

            Object o = getLastNonConfigurationInstance();
            if (o instanceof SScore) {
                currentScore = (SScore) o; // onResume updates the ui with this score
            }
        }
        showZoom(magnification);
    }

    void clearLoop() {
        loopStart = loopEnd = -1;
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            public void run() {
                ssview.hideLoopGraphics();
            }
        });
        player = null; // we need to recreate the player with new playdata
    }

    /**
     * called on app quit and device rotation.
     * <p>We save the state and the score so we can restore without reloading the file on device rotation
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (player != null) {
            player.reset();
        }
        if (currentFile != null)
            savedInstanceState.putString(CURRENT_FILE, currentFile.getAbsolutePath());
        savedInstanceState.putInt(NEXT_FILE_INDEX, nextFileIndex);
        savedInstanceState.putFloat(MAGNIFICATION, ssview.getMagnification());
        savedInstanceState.putBoolean("playingLeft", playingLeft);
        savedInstanceState.putBoolean("playingRight", playingRight);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * restore the score after device rotation
     */
    public Object onRetainNonConfigurationInstance() {
        return currentScore;
    }

    private enum PlayPause {play, pause}

    /**
     * set the correct image in the Play/Pause button
     *
     * @param playPause play or pause image to use
     */
    private void setPlayButtonImage(PlayPause playPause) {
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        if (playPause == PlayPause.pause)
            playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_media_pause));
        else
            playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_media_play));
    }

    /**
     * update the play-pause button image according to the player state
     */
    private void updatePlayPauseButtonImage() {
        if (player != null && player.state() == Player.State.Started)
            setPlayButtonImage(PlayPause.pause);
        else
            setPlayButtonImage(PlayPause.play);
    }

    /**
     * Get all the .xml or .mxl filenames in the assets folder
     */
    private List<String> getXMLAssetsFilenames() {
        ArrayList<String> rval = new ArrayList<String>();
        // copy files from assets to internal directory where they can be opened as files (assets can only be opened as InputStreams)
        AssetManager am = getAssets();
        try {
            String[] files = am.list("");
            for (String filename : files)
                if (filename.endsWith(".mxl") || filename.endsWith(".xml"))
                    rval.add(filename);
        } catch (IOException e) {
        }
        return rval;
    }

    /**
     * Get all the .xml/.mxl files in the internal dir
     */
    private List<File> getXMLFiles() {
        File internalDir = getFilesDir();
        String[] files = internalDir.list(new FilenameFilter() {

            @Override
            public boolean accept(File arg0, String filename) {
                return filename.endsWith(".xml") || filename.endsWith(".mxl");
            }
        });
        ArrayList<File> rval = new ArrayList<File>();
        for (String fname : files) {
            rval.add(new File(internalDir, fname));
        }
        return rval;
    }

    /**
     * delete all xml/mxl files in internal directory so they are reloaded from assets
     */
    private void clearInternalDir() {
        File internalDir = getFilesDir();
        File[] files = internalDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File file, String filename) {
                return filename.endsWith(".xml") || filename.endsWith(".mxl");
            }

        });
        for (File file : files) {
            file.delete();
        }
    }

    /**
     * copy all .xml/.mxl files from assets to the internal directory where they can be opened as files
     * (assets can only be opened as InputStreams).
     */
    private List<File> moveFilesToInternalStorage() {
        ArrayList<File> rval = new ArrayList<File>();
        AssetManager am = getAssets();
        try {
            String[] files = am.list("");
            File internalDir = getFilesDir();
            for (String filename : files) {
                if (filename.endsWith(".xml") || filename.endsWith(".mxl")) {
                    File outfile = new File(internalDir, filename);
                    InputStream is = am.open(filename);
                    OutputStream os = new FileOutputStream(outfile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) != -1) {
                        os.write(buffer, 0, read);
                    }
                    is.close();
                    os.close();
                    rval.add(outfile);
                }
            }
        } catch (IOException e) {
            Log.w("FileStorage", "Error copying asset files ", e);
        }
        return rval;
    }

    /**
     * get the list of .xml & .mxl files in the internal directory.
     *
     * @return the List of {@link File}.
     */
    private List<File> sourceXMLFiles() {
        List<File> files = getXMLFiles();
        List<String> assetsFiles = getXMLAssetsFilenames();
        if (files.size() >= assetsFiles.size()) {
            return files;
        } else
            return moveFilesToInternalStorage();
    }

    /**
     * get a suitable String to use as a title for the score
     *
     * @param score the {@link SScore}
     * @return the title {@link String}
     */
    private String titleText(SScore score) {
        Header header = score.getHeader();
        return currentFile.getName() + " - " + header.work_title + " " + header.composer;
    }

    /**
     * update the titleLabel with the titleText
     *
     * @param score the displayed {@link SScore}
     */
    private void showTitle(SScore score) {
        TextView titleLabel = (TextView) findViewById(R.id.titleLabel);
        titleLabel.setText(titleText(score));
    }

    private void showBeat(int beat) {
        TextView beatText = (TextView) findViewById(R.id.beatText);
        beatText.setText("" + beat);
        beatText.setVisibility(TextView.VISIBLE);
    }

    private void hideBeat() {
        TextView beatText = (TextView) findViewById(R.id.beatText);
        beatText.setVisibility(TextView.INVISIBLE);
    }

    private void setTempoText(int tempoVal) {
        TextView tempoText = (TextView) findViewById(R.id.tempoText);
        tempoText.setText("" + tempoVal);
    }

    private void setTempoSliderValPercent(int percent) {
        SeekBar tempoSlider = (SeekBar) findViewById(R.id.tempoSlider);
        tempoSlider.setProgress(percent);
    }

    private int getTempoSliderValPercent() {
        SeekBar tempoSlider = findViewById(R.id.tempoSlider);
        return tempoSlider.getProgress();
    }

    private int scalingToBPM(double scaling, int nominalBPM) {
        return (int) (nominalBPM * scaling);
    }

    private int scalingToTempoSliderPercent(double scaling) {
        return (int) (0.5 + (100 * ((scaling - kMinTempoScaling) / (kMaxTempoScaling - kMinTempoScaling))));
    }

    private double tempoSliderPercentToScaling(int percent) {
        return kMinTempoScaling + (percent / 100.0) * (kMaxTempoScaling - kMinTempoScaling);
    }

    private int tempoSliderPercentToBPM(int percent) {
        return kMinTempoBPM + (int) ((percent / 100.0) * (kMaxTempoBPM - kMinTempoBPM));
    }

    private int bpmToTempoSliderPercent(int bpm) {
        return (int) (100.0 * (bpm - kMinTempoBPM) / (double) (kMaxTempoBPM - kMinTempoBPM));
    }

    private void setTempoScaling(double tempoScaling, int nominalBPM) {
        setTempoSliderValPercent(scalingToTempoSliderPercent(tempoScaling));
        setTempoText(scalingToBPM(tempoScaling, nominalBPM));
    }

    private void setTempo(int bpm) {
        setTempoSliderValPercent(bpmToTempoSliderPercent(bpm));
        setTempoText(bpm);
    }

    private int zoomToSliderPercent(float zoom) {
        return (int) (100.0 * (zoom - kMinZoom) / (double) (kMaxZoom - kMinZoom));
    }

    private float sliderPercentToZoom(int percent) {
        return (float) (kMinZoom + (percent / 100.0) * (kMaxZoom - kMinZoom));
    }

    /**
     * an implementation of the UserTempo interface used by the {@link PlayData}
     * to get a current user-defined tempo, or scaling for the score embedded tempo values
     * These read the position of the tempo slider and convert that to a suitable tempo value
     */
    private class UserTempoImpl implements UserTempo {
        /**
         * @return the user-defined tempo BPM (if not defined by the score)
         */
        public int getUserTempo() {
            int percent = getTempoSliderValPercent();
            int bpm = tempoSliderPercentToBPM(percent);
            return bpm;
        }

        /**
         * @return the user-defined tempo scaling for score embedded tempo values (ie 1.0 => use standard tempo)
         */
        public float getUserTempoScaling() {
            int percent = getTempoSliderValPercent();
            double scaling = tempoSliderPercentToScaling(percent);
            if (Math.abs(scaling - 1.0) < 0.05)
                scaling = 1.0; // ensure we can get an exact scaling of 1 (despite rounding error with integer percent)
            return (float) scaling;
        }
    }

    /**
     * load a .mxl file and return a {@link SScore}
     * We use a ZipInputStream to decompress the .mxl data into a UTF-8 XML byte buffer
     *
     * @param file a file which can be opened with FileInputStream
     * @return a {@link SScore}
     */
    private SScore loadMXLFile(File file) {
        if (!file.getName().endsWith(".mxl"))
            return null;

        InputStream is;
        try {
            is = new FileInputStream(file);
            ZipInputStream zis = null;
            try {
                zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                while ((ze = zis.getNextEntry()) != null) {
                    if (!ze.getName().startsWith("META-INF") // ignore META-INF/ and container.xml
                            && ze.getName() != "container.xml") {
                        // read from Zip into buffer and copy into ByteArrayOutputStream which is converted to byte array of whole file
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int count;
                        while ((count = zis.read(buffer)) != -1) { // load in 1K chunks
                            os.write(buffer, 0, count);
                        }
                        try {
                            LoadOptions loadOptions = new LoadOptions(LicenceKeyInstance.SeeScoreLibKey, true);
                            return SScore.loadXMLData(os.toByteArray(), loadOptions);
                        } catch (XMLValidationException e) {
                            Log.w("sscore", "loadfile <" + file + "> xml validation error: " + e.getMessage());
                        } catch (ScoreException e) {
                            Log.w("sscore", "loadfile <" + file + "> error:" + e);
                        }
                    }
                }
            } catch (IOException e) {
                Log.w("Open", "file open error " + file, e);
                e.printStackTrace();
            } finally {
                if (zis != null)
                    zis.close();
            }
        } catch (FileNotFoundException e1) {
            Log.w("Open", "file not found error " + file, e1);
            e1.printStackTrace();
        } catch (IOException e) {
            Log.w("Open", "io exception " + file, e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load the given xml file and return a SScore.
     *
     * @param file the file
     * @return the score
     */
    private SScore loadXMLFile(File file) {
        if (!file.getName().endsWith(".xml"))
            return null;
        try {
            LoadOptions loadOptions = new LoadOptions(LicenceKeyInstance.SeeScoreLibKey, true);
            return SScore.loadXMLFile(file, loadOptions);
        } catch (XMLValidationException e) {
            Log.w("sscore", "loadfile <" + file + "> xml validation error: " + e.getMessage());
        } catch (ScoreException e) {
            Log.w("sscore", "loadfile <" + file + "> error:" + e);
        }
        return null;
    }

    /**
     * Load the file of type .xml or .mxl
     *
     * @param file
     * @return the score
     */
    private SScore loadFile(File file) {
        if (player != null) {
            player.reset();
            player = null; // force a reload with the new score
        }
        currentBar = 0;
        clearLoop();
        isTransposing = false;
        isShowingSinglePart = false;
        SeekBar zoomSlider = (SeekBar) findViewById(R.id.zoomSlider);
        zoomSlider.setProgress(zoomToSliderPercent(ssview.getMagnification()));
        if (file.getName().endsWith(".mxl")) {
            return loadMXLFile(file);
        } else if (file.getName().endsWith(".xml")) {
            return loadXMLFile(file);
        } else
            return null;
    }

    /**
     * Load the next .xml/.mxl file from the assets (copied via the internal dir)
     *
     * @return the score
     */
    private SScore loadNextFile() {
        List<File> files = sourceXMLFiles();
        int index = 0;
        for (File file : files) {
            if (index == nextFileIndex) {
                SScore sc = loadFile(file);
                nextFileIndex = (index + 1) % files.size();
                if (sc != null) {
                    currentFile = file;
                    currentScore = sc;

                    if (PrintContents) {
                        try {
                            for (int partIndex = 0; partIndex < sc.numParts(); ++partIndex) {
                                System.out.println(" part: " + partIndex);
                                for (int barIndex = 0; barIndex < sc.numBars(); ++barIndex) {
                                    BarGroup bg = sc.getBarContents(partIndex, barIndex);
                                    System.out.println(" bar: " + barIndex + " has " + bg.items.length + " items:");
                                    for (Item item : bg.items) {
                                        System.out.println("    item: " + item.toString());
                                    }
                                }
                            }
                        } catch (ScoreException ex) {
                            System.out.println(ex);
                        }
                    }

                    return sc;
                }
            }
            ++index;
        }
        return null;
    }

    /**
     * show the text in the transpose TextView
     */
    private void setTransposeText(String text) {
        TextView transposeTextView = (TextView) findViewById(R.id.transposeLabel);
        transposeTextView.setText(text);
    }

    /**
     * update the transpose TextView with the current transpose setting for the score
     *
     * @param score the score
     */
    private void showTranspose(SScore score) {
        int semi = score.getTranspose();
        if (semi == 0)
            setTransposeText("no transposition");
        else if (semi > 0)
            setTransposeText("transposed +" + Integer.toString(semi) + " semitones");
        else if (semi < 0)
            setTransposeText("transposed " + Integer.toString(semi) + " semitones");
    }

    private void setupTempoUI(SScore score) {
        // set tempo slider to default tempo
        if (score.hasDefinedTempo()) {
            try {
                Tempo tempo = score.tempoAtStart();
                setTempoScaling(kDefaultTempoScaling, tempo.bpm);
            } catch (ScoreException ex) {
            }
        } else {
            setTempo(kDefaultTempoBPM);
        }
    }

    /**
     * update the UI to show the score
     *
     * @param score the score
     */
    private void showScore(SScore score, Runnable completionHandler) {
        if (completionHandler != null) {
            ssview.setLayoutCompletionHandler(completionHandler);
        } else {
            ssview.setLayoutCompletionHandler(new Runnable() {
                public void run() {
                    // we could do something here when the score has finished loading
                }
            });
        }
        hideBeat();
        setPlayButtonImage(PlayPause.play); // show play in menu
        showTranspose(score);
        ArrayList parts = new ArrayList<Boolean>();
        if (isShowingSinglePart) {
            for (int i = 0; i < score.numParts(); ++i)
                parts.add(new Boolean(i == singlePart ? true : false));
        }
        ssview.setScore(score, parts, magnification);
        showTitle(score);
        setupTempoUI(score);
    }

    /**
     * display the current zoom value in the TextView label
     */
    private void showZoom(float scale) {
        TextView zoomText = (TextView) findViewById(R.id.zoomText);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        zoomText.setText(nf.format(scale));
    }

    /**
     * load the next file in the directory (transferred at startup from assets) in a background thread
     */
    void backgroundLoadNext() {
        magnification = ssview.getMagnification(); // preserve the magnification
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            public void run() { // must call ssview.clear on the foreground thread

                ssview.clear(new Runnable() {
                    @Override
                    public void run() {

                        new Thread(new Runnable() { // load file on background thread

                            public void run() {
                                final SScore score = loadNextFile();

                                new Handler(Looper.getMainLooper()).post(new Runnable() {

                                    public void run() {
                                        if (score != null) {
                                            showScore(score, null); // update score in SeeScoreView on foreground thread
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        });
    }

    /**
     * called on resuming the activity, including after device rotation
     */
    protected void onResume() {
        super.onResume();
        if (currentScore != null) // we can use the saved score if only rotating the display - we don't want the whole reload
        {
            showScore(currentScore, null);
        } else {
            new Thread(new Runnable() { // load file on background thread

                public void run() {

                    final SScore score = (currentFile != null) ? loadFile(currentFile) : loadNextFile();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        public void run() {
                            if (score != null) {
                                showScore(score, null); // update score in SeeScoreView on foreground thread
                            }
                        }
                    });
                }

            }).start();
        }
    }

    /**
     * called by the system on opening the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * enable or disable all menu items
     */
    private void enableMenuItems(boolean enable) {
        boolean canEnableSingleStaff = isSelectedSinglePartWithMultiStaff();
        final View LItem = findViewById(R.id.LButton);
        final View RItem = findViewById(R.id.RButton);
        if (LItem != null)
            LItem.setEnabled(enable && canEnableSingleStaff);
        if (RItem != null)
            RItem.setEnabled(enable && canEnableSingleStaff);
        final View nextfileItem = findViewById(R.id.nextfile);
        if (nextfileItem != null)
            nextfileItem.setEnabled(enable);
        final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        if (playButton != null)
            playButton.setEnabled(enable);
        final ImageButton stopButton = (ImageButton) findViewById(R.id.stopButton);
        if (stopButton != null)
            stopButton.setEnabled(enable);
    }

    private void enableTransposeButtons(boolean enable) {
        final View plusItem = findViewById(R.id.transposePlus);
        final View minusItem = findViewById(R.id.transposeMinus);
        plusItem.setEnabled(enable);
        minusItem.setEnabled(enable);
    }

    /**
     * transpose in a background thread
     *
     * @param transpose +1/-1 to transpose up/down one semitone from the current transpose setting
     */
    private void backgroundTranspose(final int transpose) {
        if (!isTransposing) {
            isTransposing = true;
            enableTransposeButtons(false);
            enableMenuItems(false); // disable menu until transpose is complete
            new Thread(new Runnable() { // load file on background thread
                public void run() {
                    if (player != null) {
                        player.reset();
                        player = null;
                    }
                    currentBar = 0;
                    clearLoop();
                    if (currentScore != null) {
                        try {
                            currentScore.setTranspose(currentScore.getTranspose() + transpose);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {// relayout after transpose
                                    showScore(currentScore, new Runnable() {
                                        public void run() {
                                            isTransposing = false;
                                            enableMenuItems(true); // reenable menu only when layout is complete (there is a problem with transpose during layout)
                                            enableTransposeButtons(true);
                                        }
                                    });
                                }
                            });
                        } catch (ScoreException e) {
                            System.out.println(" exception from setTranspose:" + e.toString());
                            isTransposing = false;
                            enableMenuItems(true); // reenable menu
                            enableTransposeButtons(true);
                        }
                    } else {
                        isTransposing = false;
                        enableMenuItems(true); // reenable menu
                        enableTransposeButtons(true);
                    }

                }
            }).start();
        }
    }

    private static RenderItem.Colour kOrange = new RenderItem.Colour(1, 0.5F, 0, 1);
    private static RenderItem.Colour kBlue = new RenderItem.Colour(0, 0, 1, 1);

    /**
     * create and setup the Player with dispatch handlers
     *
     * @return the new player
     */
    private Player setupPlayer() {
        try {
            final Player pl = new Player(currentScore, new UserTempoImpl(), this, PlayUsingMediaPlayer, new PlayData.PlayControls() {
                public boolean getPartEnabled(int partIndex) {
                    if (isShowingSinglePart)
                        return partIndex == singlePart; // play single part if showing single part
                    else
                        return true;
                }

                public boolean getPartStaffEnabled(int partIndex, int staffIndex) {
                    return staffIndex == 0 ? playingRight : playingLeft;
                }

                public int getPartMIDIInstrument(int partIndex) {
                    return 0; // 0 = use default. Return eg 41 for violin (see MIDI spec for standard program change values)
                }

                public boolean getMetronomeEnabled() {
                    final CheckBox metronomeCheck = (CheckBox) findViewById(R.id.metronomeCheck);
                    return metronomeCheck.isChecked();
                }

                public int getMidiKeyForMetronome() {
                    // defines voice of metronome - see MIDI spec "Appendix 1.5 - General MIDI Percussion Key Map"
                    return 0; // use default voice
                }

                public float getPartVolume(int partIndex) {
                    return  0.5F;
                }

                public float getMetronomeVolume() {
                    return 1.F;
                }
            }, loopStart, loopEnd, (loopStart >= 0 && loopEnd >= 0) ? kPlayLoopRepeats : 0);
            final int autoScrollAnimationTime = pl.bestScrollAnimationTime();
            pl.setBarStartHandler(new Dispatcher.EventHandler() {
                private int lastIndex = -1;

                public void event(final int index, final boolean ci) {

                    // use bar cursor if bar time is short
                    final boolean useNoteCursor = UseNoteCursorIfPossible && !pl.needsFastCursor();
                    if (!useNoteCursor || ColourPlayedNotes) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {

                            public void run() {
                                if (!useNoteCursor) // use bar cursor
                                    ssview.setCursorAtBar(index, SeeScoreView.CursorType.box, autoScrollAnimationTime);

                                if (ColourPlayedNotes) { // if this is a repeat section we clear the colouring from the previous repeat
                                    boolean startRepeat = index < lastIndex;
                                    if (startRepeat) {
                                        ssview.clearColouringForBarRange(index, currentScore.numBars() - index);
                                    }
                                }
                                lastIndex = index;
                            }
                        });
                    }
                }
            }, -50); // anticipate so cursor arrives on time
            pl.setBeatHandler(new Dispatcher.EventHandler() {
                public void event(final int index, final boolean ci) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        final int beatNumber = index + 1;
                        final boolean countIn = ci;

                        public void run() {
                            if (countIn)
                                showBeat(beatNumber);
                            else
                                hideBeat();
                        }
                    });
                }
            }, 0);
            if (UseNoteCursorIfPossible || ColourPlayedNotes) {
                pl.setNoteHandler(new Dispatcher.NoteEventHandler() {
                    public void startNotes(final List<Note> notes) {

                        // disable note cursor if bar time is short
                        final boolean useNoteCursor = !pl.needsFastCursor();
                        if (useNoteCursor) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                final List<Note> localNotes = notes;

                                public void run() {

                                    ssview.moveNoteCursor(localNotes, autoScrollAnimationTime);
                                }
                            });
                        }
                        if (ColourPlayedNotes) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    for (Note note : notes) {
                                        ssview.colourItem(note.partIndex, note.startBarIndex, note.item_h, (note.staffindex > 0) ? kOrange : kBlue, true); // different colours in different staves
                                    }
                                }
                            });
                        }
                    }
                }, -50);
            }
            pl.setEndHandler(new Dispatcher.EventHandler() {
                @Override
                public void event(int index, boolean countIn) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            setPlayButtonImage(PlayPause.play);
                            currentBar = Math.max(0, loopStart); // next play will be from start
                            if (ColourPlayedNotes) {
                                ssview.clearAllColouring();
                            }
                        }
                    });
                }
            }, 0);

            return pl;

        } catch (Player.PlayerException ex) {
            System.out.println("Player error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * called on tapping play-pause button
     *
     * @param button the button
     */
    public void play_pause(View button) {
        if (currentScore == null)
            return;

        final CheckBox countInCheck = (CheckBox) findViewById(R.id.countInSwitch);
        boolean countIn = countInCheck.isChecked();

        hideBeat();

        if (player != null) {
            switch (player.state()) {
                case NotStarted:
                    // scroll to current bar ready for start
                    ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
                    player.startAt(currentBar, countIn);
                    break;

                case Started:
                    player.pause();
                    currentBar = player.currentBar();
                    break;

                case Paused:
                    currentBar = player.currentBar();
                    player.resume();
                    break;

                case Stopped:
                case Completed:
                    player.reset();
                    currentBar = Math.max(0, loopStart);
                    ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
                    player.startAt(currentBar, countIn);
                    break;
            }

        } else { // player == null

            player = setupPlayer();
            ssview.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
            player.startAt(currentBar, countIn);
        }
        updatePlayPauseButtonImage();
    }

    /**
     * called on tapping the stop button
     *
     * @param button the tapped button
     */
    public void stop_play(View button) {
        hideBeat();
        if (player != null) {
            switch (player.state()) {
                case Started:
                case Paused:
                case Stopped:
                case Completed:
                    player.reset();
                    currentBar = Math.max(0, loopStart);
                    break;
            }
        }
        player = null;
        updatePlayPauseButtonImage();
    }

    public void plusTapped(View button) {
        backgroundTranspose(+1);
    }

    public void minusTapped(View button) {
        backgroundTranspose(-1);

    }

    private boolean isSelectedSinglePartWithMultiStaff() {

        if (currentScore != null) {
            boolean isSelectedSinglePart = isShowingSinglePart || currentScore.numParts() == 1;
            if (isSelectedSinglePart) {
                int partIndex = isShowingSinglePart ? singlePart : 0;
                return currentScore.numStavesForPart(partIndex) > 1;
            }
        }
        return false;
    }

    void setLoopL() {
        if (player != null && (player.state() == Player.State.Started || player.state() == Player.State.Paused))
            player.reset();
        if (currentBar == loopStart) // 2nd tap on button clears loop
            clearLoop();
        else {
            loopStart = currentBar;
            if (loopEnd < loopStart)
                loopEnd = currentScore.numBars() - 1;
            ssview.displayLoopGraphics(loopStart, loopEnd);
        }
        currentBar = Math.max(0, loopStart); // if looping we need to start at the first bar of the loop
        player = null; // we need to recreate the player with new playdata
    }

    void setLoopR() {
        if (player != null && (player.state() == Player.State.Started || player.state() == Player.State.Paused))
            player.reset();
        if (currentBar == loopEnd) // 2nd tap on button clears loop
            clearLoop();
        else {
            loopEnd = currentBar;
            if (loopStart > loopEnd)
                loopStart = 0;
            ssview.displayLoopGraphics(loopStart, loopEnd);
        }
        currentBar = Math.max(0, loopStart); // if looping we need to start at the first bar of the loop
        player = null; // we need to recreate the player with new playdata
    }

    public boolean onPrepareOptionsMenu(Menu menu) {

        if (super.onPrepareOptionsMenu(menu)) {
            boolean canEnableSingleStaff = isSelectedSinglePartWithMultiStaff();
            MenuItem rItem = menu.findItem(R.id.RButton);
            rItem.setTitle(playingRight ? "Ⓡ" : "r");
            rItem.setEnabled(canEnableSingleStaff);
            MenuItem lItem = menu.findItem(R.id.LButton);
            lItem.setTitle(playingLeft ? "Ⓛ" : "l");
            lItem.setEnabled(canEnableSingleStaff);
            return true;
        } else
            return false;
    }

    /**
     * called from the system to handle menu selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int itemId = item.getItemId();
        if (itemId == R.id.nextfile) {
            backgroundLoadNext();
            return true;
        } else if (itemId == R.id.RButton) {
            playingRight = !playingRight;
            item.setTitle(playingRight ? "Ⓡ" : "r");
            if (player != null) {
                player.stop();
                player.restart(false);
            }

            return true;
        } else if (itemId == R.id.LButton) {
            playingLeft = !playingLeft;
            item.setTitle(playingLeft ? "Ⓛ" : "l");
            if (player != null) {
                player.stop();
                player.restart(false);
            }
            return true;
        } else if (itemId == R.id.loopLButton) {
            setLoopL();
            return true;
        } else if (itemId == R.id.loopRButton) {
            setLoopR();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * load the SeeScoreLib.so library
     */
    static {
        System.loadLibrary("SeeScoreLib");
    }
}
