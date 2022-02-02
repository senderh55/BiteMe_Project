package screenManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import user.UserViewController;

/**
 * This class is the manager for all the screens in the system.
 */
public class ScreenManager extends StackPane {
	
	/** The screens (screen enum - node). */
	private Map<IScreenEnum, Node> screens = new HashMap<IScreenEnum, Node>();
	
	/** The current screen. */
	private MyScreenEnum currentScreen;
	
	

	/** 
	 * Add screen to be managed.
	 * 
	 * @param screenId Screen identification.
	 * @param node - Node to put inside pane.
	 */
	public void addScreen(IScreenEnum screenId, Node node) {
		screens.put(screenId, node);
	}
	
	/**
	 * Define actual screen with fade transition.
	 * 
	 * @param screenId Screen identification.
	 * @return true to success defined, or false otherwise.
	 */
	public boolean setScreen(final IScreenEnum screenId) {
		if (screens.get(screenId) != null) {
			if (!getChildren().isEmpty()) {
//				FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2));
//				fadeOut.setFromValue(1.0);
//				fadeOut.setToValue(0.0);
//				fadeOut.setNode(getChildren().get(0));
				getChildren().remove(0);
				getChildren().add(0, screens.get(screenId));
//				fadeOut.setOnFinished(
//						new EventHandler<ActionEvent>() {
//							@Override
//							public void handle(ActionEvent event) {
//								FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2));
//								fadeIn.setFromValue(0.0);
//								fadeIn.setToValue(1.0);
//								fadeIn.setNode(getChildren().get(0));
//								getChildren().remove(0);
//								getChildren().add(0, screens.get(screenId));
//								fadeIn.play();
//							}
//						});
//				fadeOut.play();
			} else {
//				FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2));
//				fadeIn.setFromValue(0.0);
//				fadeIn.setToValue(1.0);
//				fadeIn.setNode(screens.get(screenId));
				getChildren().add(screens.get(screenId));
//				fadeIn.play();
			}
			currentScreen = screenId.getScreenEnum();
			return true;
		} else {
			System.out.println("Screen hasn't been loaded! - " + screenId.getFXMLName());
			return false;
		}
	}
	
	/**
	 * Load screen with your template FXML.
	 *
	 * @param screenId Screen identification.
	 * @param clazz Class to get resource.
	 * @param resource Template name of FXML.
	 * @param UVC the uvc
	 * @return true to success load, or false otherwise.
	 */
	public boolean loadScreen(IScreenEnum screenId, Class<? extends Application> clazz, String resource, UserViewController UVC) {
		try {
			FXMLLoader loader = new FXMLLoader(clazz.getResource(resource));
			Parent screen = (Parent) loader.load();
			IScreenController controller = (IScreenController) loader.getController();
			controller.setUserViewController(UVC);
			controller.setParameters();
			addScreen(screenId, screen);
			return true;
		} catch (IOException ioe) {
			Logger.getLogger(ScreenManager.class.getName()).log(Level.ALL, ioe.getMessage());
			return false;
		}
	}

	/**
	 * Remove screen of the stack.
	 * 
	 * @param screenId Screen identification.
	 * @return true to success remove, or false otherwise.
	 */
	public boolean unloadScreen(MyScreenEnum screenId) {
		if (screens.remove(screenId) == null) {
			System.out.println("Screen didn't exist!");
			return false;
		} else {
			return true;
		}
	}
	
	public IScreenEnum getCurrentScreen() {
		return currentScreen;
	}
}
