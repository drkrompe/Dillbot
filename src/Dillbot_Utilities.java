import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import com.sun.glass.events.KeyEvent;

/**
 * Set of Utilities to facilitate rapid production of java Robot agents. This
 * Class when extended by your application or used directly can make scripting
 * of complex human interactions with browsers and other applications easier to
 * achieve. I have tried to make each method as concise and easy to understand
 * as possible. there is only one method with 8 lines of code. Used together
 * these methods are a powerful human action scripting tool.
 * 
 * Another useful utility to have is a MousePositionHUD that will show you what
 * pixel your mouse is pointing and other useful info such as pixel color
 * properties.
 * 
 * NOTE : There was a lot of issue with trying to find a way to close the
 *        application remotely or through a key input. The problem was that
 *        any Mouse or keyboard listeners require that a java application be
 *        in focus for them to be registered. Therefore a solution to the 
 *        problem in all situations would require a daemon or other 
 *        application written in C/C++ to get the low level readings of 
 *        keyboard input and to send a termination signal to the java 
 *        application that is running this robot utility. Or if you are up
 *        to the challenge of messing with the JNI you can create your own
 *        interface between the application and a low level language.
 * 
 * Applications: 
 * - Script class registration to get fast filling spots. 
 * - Script games to run while you are away 
 * - Script online voting polls with simple or non existent captcha 
 * - drive your friends wild with a robot that moves the mouse a random direction
 *   at a random interval between (40 - 180) seconds.
 * 
 * Created this initially as a Junior at Michigan Technological University to
 * "compete" with other CS students in an online poll for top song choice Winter
 * Carnival 2016. Competition was fierce but I was able to produce scripts for
 * different computer configurations to win.
 * 
 * Winning song: "Kill the Police" by G.G. Allin
 * 
 * @author drkrompe
 * @date 3/21/2017
 */
public class Dillbot_Utilities {

	/**
	 * ========================================================
	 * 
	 * Program is split into the following sections by order:
	 * 
	 * ========================================================
	 * 
	 * - Constructor
	 * 
	 * - Timing Utilities
	 * 
	 * - ClipBoard Utilities
	 * 
	 * - Windows Specific Utilities
	 * 
	 * - Mouse Utilities
	 * 
	 * - Keyboard Utilities
	 * 
	 * =======================================================
	 */

	/**
	 * internal Robot Object.
	 */
	private Robot dbot;

	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @throws AWTException
	 */
	public Dillbot_Utilities() throws AWTException {
		dbot = new Robot();
	}

	// ----------------------------------------------------
	// Timing Utilities
	// ----------------------------------------------------

	/**
	 * sleep X seconds
	 * 
	 * @param x
	 *            - int number of seconds.
	 */
	protected void sxs(int x) {
		for (int i = 1; i <= x; i++) {
			dbot.delay(1000);
		}
	}

	// ----------------------------------------------------
	// Clipboard Utilities
	// ----------------------------------------------------

	/**
	 * ClipBoard Util
	 * 
	 * String from memory to clipboard.
	 * 
	 * @param msg
	 *            - String message to be put into ClipBoard buffer.
	 */
	protected void cb_stocb(String msg) {
		StringSelection sel = new StringSelection(msg);
		Clipboard clipb = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipb.setContents(sel, sel);
	}

	/**
	 * ClipBoard Util
	 * 
	 * Clipboard to String in memory.
	 */
	protected String cb_cbtos() throws Exception {
		Clipboard clipb = Toolkit.getDefaultToolkit().getSystemClipboard();
		return (String) clipb.getData(DataFlavor.stringFlavor);
	}

	// ----------------------------------------------------
	// Windows Specific Utilities - combo of mouse and key
	// ----------------------------------------------------

	/**
	 * Throw current selected window right side of screen.
	 */
	protected void Win_thrRight() {
		kwin(true);
		kar();
		kwin(false);
	}

	/**
	 * Throw current selected window left side of screen.
	 */
	protected void Win_thrLeft() {
		kwin(true);
		kal();
		kwin(false);
	}

	// ----------------------------------------------------
	// Mouse utilities
	// ----------------------------------------------------
	/**
	 * Mouse Move.
	 * 
	 * @param x
	 * @param y
	 */
	protected void mmv(int x, int y) {
		dbot.mouseMove(x, y);
	}

	/**
	 * Mouse click - press and release.
	 */
	protected void mc() {
		dbot.mousePress(InputEvent.BUTTON1_MASK);
		dbot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * Mouse left click hold.
	 */
	protected void mc_h() {
		dbot.mousePress(InputEvent.BUTTON1_MASK);
	}

	/**
	 * Mouse left click release.
	 */
	protected void mc_r() {
		dbot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * Highlight text from x,y to xf,yf.
	 */
	protected void mdrag(int x, int y, int xf, int yf) {
		mmv(x, y);
		mc_h();
		mmv(xf, yf);
		mc_r();
	}

	/**
	 * Mouse get pixel red value at pos(x,y)
	 */
	protected int mgp_r(int x, int y) {
		return dbot.getPixelColor(x, y).getRed();
	}

	/**
	 * Mouse get pixel green value at pos(x,y)
	 */
	protected int mgp_g(int x, int y) {
		return dbot.getPixelColor(x, y).getGreen();
	}

	/**
	 * Mouse get pixel blue value at pos(x,y)
	 */
	protected int mgp_b(int x, int y) {
		return dbot.getPixelColor(x, y).getBlue();
	}

	// ----------------------------------------------------
	// Keyboard utilities
	// ----------------------------------------------------

	/**
	 * Keyboard letter Can choose letter either upper case or lower. Will always
	 * write lower case. Use Shift modifier to get upper.
	 * 
	 * @param press
	 *            - boolean press or release
	 * @param letter
	 *            - from (a-z)(A-Z)
	 */
	protected void kletter(boolean press, char letter) {
		if (!Character.isUpperCase(letter)) {
			letter = Character.toUpperCase(letter);
		}
		if (press) {
			dbot.keyPress((int) letter);
		} else {
			dbot.keyRelease((int) letter);
		}
	}

	/**
	 * Keyboard Control-C.
	 */
	protected void kcc() {
		kctrl(true);
		kletter(true, 'c');
		kletter(false, 'c');
		kctrl(false);
	}

	/**
	 * Keyboard Control-V
	 */
	protected void kcv() {
		kctrl(true);
		kletter(true, 'v');
		kletter(false, 'v');
		kctrl(false);
	}

	/**
	 * Keyboard Control-Shift-N
	 */
	protected void kcsn() throws Exception {
		kctrl(true);
		kshift(true);
		kletter(true, 'n');
		kletter(false, 'n');
		kshift(false);
		kctrl(false);
	}

	/**
	 * Keyboard Ctrl key
	 * 
	 * @param press
	 *            - boolean press or release
	 */
	protected void kctrl(boolean press) {
		if (press) {
			dbot.keyPress(KeyEvent.VK_CONTROL);
		} else {
			dbot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	/**
	 * Keyboard shift key
	 * 
	 * @param press
	 *            - boolean press or release
	 */
	protected void kshift(boolean press) {
		if (press) {
			dbot.keyPress(KeyEvent.VK_SHIFT);
		} else {
			dbot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	/**
	 * Keyboard Enter
	 */
	protected void ke() {
		dbot.keyPress(KeyEvent.VK_ENTER);
		dbot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * Keyboard right arrow
	 */
	protected void kar() {
		dbot.keyPress(KeyEvent.VK_RIGHT);
		dbot.keyRelease(KeyEvent.VK_RIGHT);
	}

	/**
	 * Keyboard left arrow
	 */
	protected void kal() {
		dbot.keyPress(KeyEvent.VK_LEFT);
		dbot.keyRelease(KeyEvent.VK_LEFT);
	}

	/**
	 * Keyboard up arrow
	 */
	protected void kau() {
		dbot.keyPress(KeyEvent.VK_UP);
		dbot.keyRelease(KeyEvent.VK_UP);
	}

	/**
	 * Keyboard down arrow
	 */
	protected void kad() {
		dbot.keyPress(KeyEvent.VK_DOWN);
		dbot.keyRelease(KeyEvent.VK_DOWN);
	}

	/**
	 * Keyboard Windows key modifier
	 * 
	 * @param press
	 *            - boolean press or release
	 */
	protected void kwin(boolean press) {
		if (press) {
			dbot.keyPress(KeyEvent.VK_WINDOWS);
		} else {
			dbot.keyRelease(KeyEvent.VK_WINDOWS);
		}
	}
}
