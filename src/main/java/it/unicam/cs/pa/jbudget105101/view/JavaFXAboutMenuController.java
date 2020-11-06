/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @author Damiano
 *
 */
public class JavaFXAboutMenuController extends JavaFXStageController {

	@FXML
	private Text LicenseText;
	
	private Path path = Paths.get(System.getProperty("user.dir"), "LICENSE");
	
	@FXML
	private void initialize() {
		String content;
		try {
			content = new String(Files.readAllBytes(path));
			LicenseText.setText(content);
		} catch (IOException e) {
			LicenseText.setText("License : ");
		}
		
	}

	@Override
	protected void handleItemAdd() {
		super.handleItemCancel();
	}

}
