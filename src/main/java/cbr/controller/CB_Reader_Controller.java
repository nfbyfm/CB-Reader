package cbr.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cbr.view.CB_Reader_View;


public class CB_Reader_Controller {

	private CB_Reader_View mainview;
	
	public CB_Reader_Controller() {
		mainview = new CB_Reader_View(this);
	}

	public void showview()
	{
		mainview.setVisible(true);
	}

	public void gui_close_program() {
		// TODO Auto-generated method stub
		System.exit(0);
		/*int dialogResult = JOptionPane.showConfirmDialog (null, "Really close program?","Close program",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			System.exit(0);
		}*/	
	}
	
	public void gui_open() {
		// open cbr/cbz-file
		//show FileChooser
		JFileChooser ficho = new JFileChooser();
		ficho.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter imageFilter =new FileNameExtensionFilter("cbz-file", "cbz");
		ficho.setFileFilter(imageFilter);
		ficho.setAcceptAllFileFilterUsed(false);
		ficho.setDialogTitle("open cbz-file");
		ficho.setCurrentDirectory(new File(System.getProperty("user.home") ));

		int returnVal = ficho.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = ficho.getSelectedFile().getAbsolutePath();
			System.out.println(filename);
			if(filename.toLowerCase().contains(".cbz")==false){
				filename += ".cbz";
			}
			File fs = new File(filename);
			if(fs.exists()){
				
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No matching file with the selected name could be found.","open cbr-file",JOptionPane.ERROR_MESSAGE);
			}					
		}
	}
	
	
	
	
}
