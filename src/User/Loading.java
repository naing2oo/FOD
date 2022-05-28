package User;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;

/**
 * @author NaingNaingOo 
		 This class is for showing a loading screen with 5 seconds before showing user login screen.
 *
 */
public class Loading {
	String imgpath = "C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image
	JWindow w = new JWindow();// creating a JWindow just for showing a loading gif

	Loading() {//Constructor for loading class
		Icon bug = new ImageIcon(imgpath+"Loading.GIF");//a image icon is created to be shown in screen
		JLabel l = new JLabel("", bug, JLabel.CENTER);//add the image icon to a label in center

		w.add(l);//add label to window
		w.setSize(400, 300);//set size of window
		w.setVisible(true);//set window visible true
		w.setLocationRelativeTo(null);//set window's location to center of screen
		new timer().execute();// call execute function to run an internal class's object to wait for 5 seconds 
	}

	class timer extends SwingWorker<Void, Void> {// an internal class to put into thread for the purpose of timer 5 seconds

		@Override
		protected Void doInBackground() throws Exception {// a function named doInBackground with throw Exception
			Thread.sleep(5000);//put into thread for 5 seconds
			return null;// return null from the function
		}

		protected void done() {// a function to perform to move to another state when it come back from thread
				new UserLogin();//go to User Login Screen
			w.dispose();// disappear the window
		}
	}
	public static void main(String[] args) {// the main function of class
				new Loading();// call loading object to run
	}
}